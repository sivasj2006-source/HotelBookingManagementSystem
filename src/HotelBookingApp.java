import java.util.HashMap;
import java.util.Map;

/**
 * Use Case 3: Centralized Inventory Setup
 * Demonstrates how room availability is managed using a HashMap.
 *
 * @author
 * @version 3.1
 */

class RoomInventory {

    private Map<String, Integer> inventory;

    // Constructor initializes the inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 6);
        inventory.put("Suite Room", 3);
    }

    // Method to get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Method to update availability
    public void updateAvailability(String roomType, int count) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, count);
        }
    }

    // Method to display inventory
    public void displayInventory() {
        System.out.println("Current Room Inventory");
        System.out.println("----------------------");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System - Room Inventory");
        System.out.println("Version: 3.1");
        System.out.println("-------------------------------------");

        RoomInventory inventory = new RoomInventory();

        inventory.displayInventory();

        System.out.println("\nChecking Availability for Single Room:");
        System.out.println("Available Rooms: " + inventory.getAvailability("Single Room"));
    }
}