import java.util.HashMap;
import java.util.Map;

/**
 * Use Case 4: Room Search
 * Displays available rooms without modifying inventory
 *
 * @version 4.0
 */

// Inventory class (state holder)
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 0); // Example unavailable
        inventory.put("Suite Room", 3);
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }
}

// Room domain model
class Room {

    private String type;
    private int price;

    public Room(String type, int price) {
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }
}

// Search service
class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory, Room[] rooms) {

        System.out.println("Available Rooms");
        System.out.println("----------------");

        Map<String, Integer> data = inventory.getInventory();

        for (Room room : rooms) {

            int available = data.getOrDefault(room.getType(), 0);

            if (available > 0) {
                System.out.println(
                        room.getType() +
                                " | Price: ₹" + room.getPrice() +
                                " | Available: " + available
                );
            }
        }
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System");
        System.out.println("Version: 4.0");
        System.out.println("--------------------");

        // Inventory
        RoomInventory inventory = new RoomInventory();

        // Room details
        Room[] rooms = {
                new Room("Single Room", 2000),
                new Room("Double Room", 3500),
                new Room("Suite Room", 6000)
        };

        // Search service
        RoomSearchService searchService = new RoomSearchService();

        // Display available rooms
        searchService.searchAvailableRooms(inventory, rooms);
    }
}