import java.io.*;
import java.util.*;

/**
 * Use Case 12: Data Persistence & System Recovery
 * Demonstrates saving and restoring booking and inventory state
 *
 * @version 12.0
 */

// Reservation class
class Reservation implements Serializable {

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

// System state class (inventory + booking history)
class SystemState implements Serializable {

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "hotel_state.ser";

    public void saveState(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    public SystemState loadState() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state restored successfully.");
            return (SystemState) in.readObject();

        } catch (Exception e) {

            System.out.println("No previous state found. Starting fresh.");
            return null;
        }
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System");
        System.out.println("Version: 12.0");
        System.out.println("---------------------");

        PersistenceService persistence = new PersistenceService();

        SystemState state = persistence.loadState();

        Map<String, Integer> inventory;
        List<Reservation> bookings;

        if (state == null) {

            // Fresh system start
            inventory = new HashMap<>();
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 2);
            inventory.put("Suite Room", 1);

            bookings = new ArrayList<>();

        } else {

            inventory = state.inventory;
            bookings = state.bookings;
        }

        // Simulate booking
        Reservation r1 = new Reservation("RES201", "Arun", "Single Room");

        bookings.add(r1);
        inventory.put("Single Room", inventory.get("Single Room") - 1);

        System.out.println("Booking confirmed for " + r1.getGuestName());

        // Save state before shutdown
        SystemState newState = new SystemState(inventory, bookings);
        persistence.saveState(newState);

        System.out.println("\nCurrent Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " : " + inventory.get(key));
        }
    }
}