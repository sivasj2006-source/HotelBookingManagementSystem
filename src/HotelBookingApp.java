import java.util.*;

/**
 * Use Case 10: Booking Cancellation & Inventory Rollback
 * Demonstrates safe cancellation and rollback using Stack
 *
 * @version 10.0
 */

// Reservation class
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory Service
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public void decreaseRoom(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public void increaseRoom(String type) {
        inventory.put(type, inventory.get(type) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " : " + inventory.get(key));
        }
    }
}

// Booking Service
class BookingService {

    private Map<String, Reservation> confirmedBookings = new HashMap<>();
    private Stack<String> rollbackStack = new Stack<>();
    private InventoryService inventory;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void confirmBooking(Reservation reservation) {

        confirmedBookings.put(reservation.getReservationId(), reservation);

        String roomId = reservation.getRoomType() + "-" + new Random().nextInt(100);

        rollbackStack.push(roomId);

        inventory.decreaseRoom(reservation.getRoomType());

        System.out.println("Booking Confirmed");
        System.out.println("Reservation ID: " + reservation.getReservationId());
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Allocated: " + roomId);
        System.out.println("--------------------------");
    }

    public void cancelBooking(String reservationId) {

        if (!confirmedBookings.containsKey(reservationId)) {
            System.out.println("Cancellation Failed ❌");
            System.out.println("Reason: Reservation not found.");
            return;
        }

        Reservation reservation = confirmedBookings.get(reservationId);

        if (rollbackStack.isEmpty()) {
            System.out.println("Rollback not possible.");
            return;
        }

        String releasedRoom = rollbackStack.pop();

        inventory.increaseRoom(reservation.getRoomType());

        confirmedBookings.remove(reservationId);

        System.out.println("Booking Cancelled ✅");
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Released Room ID: " + releasedRoom);
        System.out.println("--------------------------");
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System");
        System.out.println("Version: 10.0");
        System.out.println("----------------------");

        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        Reservation r1 = new Reservation("RES101", "Arun", "Single Room");
        Reservation r2 = new Reservation("RES102", "Priya", "Double Room");

        bookingService.confirmBooking(r1);
        bookingService.confirmBooking(r2);

        inventory.displayInventory();

        // Cancel booking
        bookingService.cancelBooking("RES101");

        inventory.displayInventory();
    }
}