import java.util.*;

/**
 * Use Case 7: Add-On Service Selection
 * Demonstrates attaching optional services to a reservation
 *
 * @version 7.0
 */

// Service class
class AddOnService {

    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }
}

// Manager class
class AddOnServiceManager {

    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);

        System.out.println(service.getServiceName() + " added to Reservation " + reservationId);
    }

    // Calculate additional cost
    public double calculateTotalCost(String reservationId) {

        double total = 0;

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getPrice();
            }
        }

        return total;
    }

    // Display services
    public void displayServices(String reservationId) {

        System.out.println("\nServices for Reservation: " + reservationId);

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (AddOnService s : services) {
            System.out.println(s.getServiceName() + " - ₹" + s.getPrice());
        }

        System.out.println("Total Add-On Cost: ₹" + calculateTotalCost(reservationId));
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("Hotel Booking System");
        System.out.println("Version: 7.0");
        System.out.println("--------------------");

        String reservationId = "RES101";

        AddOnServiceManager manager = new AddOnServiceManager();

        // Available services
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 1200);
        AddOnService spa = new AddOnService("Spa Access", 2000);

        // Guest selects services
        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, airportPickup);
        manager.addService(reservationId, spa);

        // Display selected services
        manager.displayServices(reservationId);
    }
}