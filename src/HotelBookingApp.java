import java.util.*;

/**
 * Use Case 6: Reservation Confirmation & Room Allocation
 * Demonstrates safe room allocation and prevention of double booking
 *
 * @version 6.0
 */

// Reservation class
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory service
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void decreaseRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
}

// Booking service
class BookingService {

    private Queue<Reservation> requestQueue = new LinkedList<>();
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    public void addReservation(Reservation reservation) {
        requestQueue.add(reservation);
    }

    public void processBookings(InventoryService inventory) {

        while (!requestQueue.isEmpty()) {

            Reservation r = requestQueue.poll();
            String type = r.getRoomType();

            if (inventory.isAvailable(type)) {

                String roomId = type.substring(0,2).toUpperCase() + new Random().nextInt(100);

                allocatedRooms.putIfAbsent(type, new HashSet<>());

                if (!allocatedRooms.get(type).contains(roomId)) {

                    allocatedRooms.get(type).add(roomId);
                    inventory.decreaseRoom(type);

                    System.out.println("Reservation Confirmed");
                    System.out.println("Guest: " + r.getGuestName());
                    System.out.println("Room Type: " + type);
                    System.out.println("Room ID: " + roomId);
                    System.out.println("----------------------");
                }

            } else {
                System.out.println("No rooms available for " + r.getGuestName());
            }
        }
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System");
        System.out.println("Version: 6.0");
        System.out.println("--------------------");

        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService();

        // Booking requests
        bookingService.addReservation(new Reservation("Arun", "Single Room"));
        bookingService.addReservation(new Reservation("Priya", "Double Room"));
        bookingService.addReservation(new Reservation("Rahul", "Suite Room"));
        bookingService.addReservation(new Reservation("Anita", "Single Room"));

        // Process bookings
        bookingService.processBookings(inventory);
    }
}