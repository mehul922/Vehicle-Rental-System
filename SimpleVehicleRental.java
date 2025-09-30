package assignment2;

import java.util.*;

class Vehicle {
    private int id;
    private String brand;
    private String model;
    private double ratePerDay;
    private boolean available;

    public Vehicle(int id, String brand, String model, double ratePerDay) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.ratePerDay = ratePerDay;
        this.available = true;
    }

    public int getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public double getRatePerDay() { return ratePerDay; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean a) { this.available = a; }

    public String toString() {
        return id + " - " + brand + " " + model + " | Rate:" + ratePerDay + " | Available:" + available;
    }
}

class Car extends Vehicle {
    private int doors;

    public Car(int id, String brand, String model, double ratePerDay, int doors) {
        super(id, brand, model, ratePerDay);
        this.doors = doors;
    }

    public String toString() {
        return "Car " + super.toString() + " | Doors:" + doors;
    }
}

class Bike extends Vehicle {
    private int engineCC;

    public Bike(int id, String brand, String model, double ratePerDay, int engineCC) {
        super(id, brand, model, ratePerDay);
        this.engineCC = engineCC;
    }

    public String toString() {
        return "Bike " + super.toString() + " | Engine:" + engineCC + "cc";
    }
}

class Customer {
    private int id;
    private String name;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public String toString() {
        return id + " - " + name;
    }
}

class PremiumCustomer extends Customer {
    private double discountRate;

    public PremiumCustomer(int id, String name, double discountRate) {
        super(id, name);
        this.discountRate = discountRate;
    }

    public double getDiscountRate() { return discountRate; }

    public String toString() {
        return super.toString() + " | Premium (Discount " + (discountRate * 100) + "%)";
    }
}

public class SimpleVehicleRental {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Vehicle> vehicles = new ArrayList<>();
    static ArrayList<Customer> customers = new ArrayList<>();

    static int vehicleId = 1;
    static int customerId = 1;

    public static void main(String[] args) {
        boolean run = true;
        while (run) {
            System.out.println("\n--- Vehicle Rental System ---");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Add Customer");
            System.out.println("3. Rent Vehicle");
            System.out.println("4. Return Vehicle");
            System.out.println("5. Show Vehicles");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addVehicle();
                case 2 -> addCustomer();
                case 3 -> rentVehicle();
                case 4 -> returnVehicle();
                case 5 -> showVehicles();
                case 0 -> run = false;
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void addVehicle() {
        System.out.print("Enter type (car/bike): ");
        String type = sc.nextLine();
        System.out.print("Brand: ");
        String brand = sc.nextLine();
        System.out.print("Model: ");
        String model = sc.nextLine();
        System.out.print("Rate per day: ");
        double rate = sc.nextDouble();

        if (type.equalsIgnoreCase("car")) {
            System.out.print("Doors: ");
            int doors = sc.nextInt();
            vehicles.add(new Car(vehicleId++, brand, model, rate, doors));
        } else {
            System.out.print("Engine CC: ");
            int cc = sc.nextInt();
            vehicles.add(new Bike(vehicleId++, brand, model, rate, cc));
        }
    }

    static void addCustomer() {
        System.out.print("Enter type (normal/premium): ");
        String type = sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        if (type.equalsIgnoreCase("premium")) {
            System.out.print("Discount (like 0.1 for 10%): ");
            double disc = sc.nextDouble();
            customers.add(new PremiumCustomer(customerId++, name, disc));
        } else {
            customers.add(new Customer(customerId++, name));
        }
    }

    static void rentVehicle() {
        showVehicles();
        System.out.print("Enter vehicle id: ");
        int vid = sc.nextInt();
        Vehicle v = null;
        for (Vehicle ve : vehicles) if (ve.getId() == vid) v = ve;
        if (v == null || !v.isAvailable()) {
            System.out.println("Not available!");
            return;
        }

        System.out.print("Enter customer id: ");
        int cid = sc.nextInt();
        Customer c = null;
        for (Customer cu : customers) if (cu.getId() == cid) c = cu;
        if (c == null) {
            System.out.println("Customer not found!");
            return;
        }

        System.out.print("Days: ");
        int days = sc.nextInt();
        double cost = v.getRatePerDay() * days;
        if (c instanceof PremiumCustomer pc) {
            cost = cost - (cost * pc.getDiscountRate());
        }
        System.out.println("Bill: " + cost);
        v.setAvailable(false);
    }

    static void returnVehicle() {
        System.out.print("Enter vehicle id to return: ");
        int vid = sc.nextInt();
        for (Vehicle v : vehicles) {
            if (v.getId() == vid && !v.isAvailable()) {
                v.setAvailable(true);
                System.out.println("Returned successfully!");
                return;
            }
        }
        System.out.println("Invalid vehicle id or not rented.");
    }

    static void showVehicles() {
        for (Vehicle v : vehicles) {
            System.out.println(v);
        }
    }
}
