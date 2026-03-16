import java.util.*;

/**
 * Use Case 8: Booking History & Reporting
 * Demonstrates storing confirmed bookings and generating reports
 *
 * @version 8.0
 */

// Reservation class
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private double totalCost;

    public Reservation(String reservationId, String guestName, String roomType, double totalCost) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.totalCost = totalCost;
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

    public double getTotalCost() {
        return totalCost;
    }
}

// Booking history service
class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    public void addReservation(Reservation r) {
        history.add(r);
        System.out.println("Reservation " + r.getReservationId() + " added to history.");
    }

    public List<Reservation> getHistory() {
        return history;
    }
}

// Booking report service
class BookingReportService {

    public void generateReport(List<Reservation> history) {
        System.out.println("\nBooking History Report");
        System.out.println("----------------------");

        if (history.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : history) {
            System.out.println("Reservation ID: " + r.getReservationId() +
                    ", Guest: " + r.getGuestName() +
                    ", Room Type: " + r.getRoomType() +
                    ", Total Cost: ₹" + r.getTotalCost());
        }
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System");
        System.out.println("Version: 8.0");
        System.out.println("--------------------");

        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulate confirmed reservations
        Reservation r1 = new Reservation("RES101", "Arun", "Single Room", 5000);
        Reservation r2 = new Reservation("RES102", "Priya", "Double Room", 7500);
        Reservation r3 = new Reservation("RES103", "Rahul", "Suite Room", 12000);

        bookingHistory.addReservation(r1);
        bookingHistory.addReservation(r2);
        bookingHistory.addReservation(r3);

        // Generate report
        reportService.generateReport(bookingHistory.getHistory());
    }
}