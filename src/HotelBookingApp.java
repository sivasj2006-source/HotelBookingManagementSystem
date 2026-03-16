import java.util.*;

/**
 * Use Case 9: Error Handling & Validation
 * Demonstrates validation and custom exception handling
 *
 * @version 9.0
 */

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

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

// Inventory Service
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void validateRoom(String roomType) throws InvalidBookingException {

        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid Room Type: " + roomType);
        }

        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for " + roomType);
        }
    }

    public void allocateRoom(String roomType) throws InvalidBookingException {

        validateRoom(roomType);

        int available = inventory.get(roomType);

        if (available - 1 < 0) {
            throw new InvalidBookingException("Inventory cannot become negative!");
        }

        inventory.put(roomType, available - 1);
    }
}

// Booking Service
class BookingService {

    private InventoryService inventory;

    public BookingService(InventoryService inventory) {
        this.inventory = inventory;
    }

    public void processReservation(Reservation reservation) {

        try {

            inventory.allocateRoom(reservation.getRoomType());

            System.out.println("Reservation Confirmed");
            System.out.println("Guest: " + reservation.getGuestName());
            System.out.println("Room Type: " + reservation.getRoomType());
            System.out.println("---------------------");

        } catch (InvalidBookingException e) {

            System.out.println("Booking Failed ❌");
            System.out.println("Reason: " + e.getMessage());
            System.out.println("---------------------");

        }
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System");
        System.out.println("Version: 9.0");
        System.out.println("--------------------");

        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        // Test bookings
        Reservation r1 = new Reservation("Arun", "Single Room");
        Reservation r2 = new Reservation("Priya", "Suite Room"); // unavailable
        Reservation r3 = new Reservation("Rahul", "Luxury Room"); // invalid

        bookingService.processReservation(r1);
        bookingService.processReservation(r2);
        bookingService.processReservation(r3);
    }
}