import java.util.*;

/**
 * Use Case 11: Concurrent Booking Simulation
 * Demonstrates thread-safe booking using synchronized methods
 *
 * @version 11.0
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

// Shared Inventory Service
class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    // Critical section
    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {

            inventory.put(roomType, available - 1);

            return true;
        }

        return false;
    }

    public void displayInventory() {
        System.out.println("\nRemaining Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " : " + inventory.get(key));
        }
    }
}

// Booking Processor Thread
class BookingProcessor extends Thread {

    private Queue<Reservation> bookingQueue;
    private InventoryService inventory;

    public BookingProcessor(Queue<Reservation> bookingQueue, InventoryService inventory) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
    }

    public void run() {

        while (true) {

            Reservation reservation;

            synchronized (bookingQueue) {

                if (bookingQueue.isEmpty()) {
                    return;
                }

                reservation = bookingQueue.poll();
            }

            boolean success = inventory.allocateRoom(reservation.getRoomType());

            if (success) {
                System.out.println(Thread.currentThread().getName()
                        + " confirmed booking for "
                        + reservation.getGuestName()
                        + " (" + reservation.getRoomType() + ")");
            } else {
                System.out.println(Thread.currentThread().getName()
                        + " failed booking for "
                        + reservation.getGuestName()
                        + " - No rooms available");
            }
        }
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System");
        System.out.println("Version: 11.0");
        System.out.println("--------------------");

        Queue<Reservation> bookingQueue = new LinkedList<>();

        // Simulate multiple guests
        bookingQueue.add(new Reservation("Arun", "Single Room"));
        bookingQueue.add(new Reservation("Priya", "Single Room"));
        bookingQueue.add(new Reservation("Rahul", "Double Room"));
        bookingQueue.add(new Reservation("Anita", "Suite Room"));
        bookingQueue.add(new Reservation("Kiran", "Suite Room"));

        InventoryService inventory = new InventoryService();

        // Multiple threads (concurrent guests)
        BookingProcessor t1 = new BookingProcessor(bookingQueue, inventory);
        BookingProcessor t2 = new BookingProcessor(bookingQueue, inventory);
        BookingProcessor t3 = new BookingProcessor(bookingQueue, inventory);

        t1.setName("Thread-1");
        t2.setName("Thread-2");
        t3.setName("Thread-3");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.displayInventory();
    }
}