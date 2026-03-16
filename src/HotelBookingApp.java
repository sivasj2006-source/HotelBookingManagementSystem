import java.util.LinkedList;
import java.util.Queue;

/**
 * Use Case 5: Booking Request Queue
 * Demonstrates handling booking requests using FIFO Queue
 *
 * @version 5.0
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

// Booking Request Queue
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add request to queue
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Display queued requests
    public void displayRequests() {

        System.out.println("\nCurrent Booking Request Queue");
        System.out.println("-----------------------------");

        for (Reservation r : requestQueue) {
            System.out.println("Guest: " + r.getGuestName() + " | Room Type: " + r.getRoomType());
        }
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System");
        System.out.println("Version: 5.0");
        System.out.println("--------------------");

        BookingRequestQueue queue = new BookingRequestQueue();

        // Guest booking requests
        Reservation r1 = new Reservation("Arun", "Single Room");
        Reservation r2 = new Reservation("Priya", "Double Room");
        Reservation r3 = new Reservation("Rahul", "Suite Room");

        // Add requests to queue
        queue.addRequest(r1);
        queue.addRequest(r2);
        queue.addRequest(r3);

        // Display queue
        queue.displayRequests();
    }
}