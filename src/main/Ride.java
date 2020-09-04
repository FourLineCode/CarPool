package main;

import java.io.FileWriter;
import java.util.UUID;

public class Ride {

    private UUID rideId;
    private String carModel;
    private String location;
    private String destination;
    private String departureTime;
    private int availableSeats;
    private double price;

    public Ride(String carModel, String location, String destination, String departureTime, int availableSeats, double price) {
        this.rideId = UUID.randomUUID();
        this.carModel = carModel;
        this.location = location;
        this.destination = destination;
        this.departureTime = departureTime;
        this.availableSeats = availableSeats;
        this.price = price;
    }

    public Ride(UUID rideId, String carModel, String location, String destination, String departureTime, int availableSeats, double price) {
        this.rideId = rideId;
        this.carModel = carModel;
        this.location = location;
        this.destination = destination;
        this.departureTime = departureTime;
        this.availableSeats = availableSeats;
        this.price = price;
    }

    public UUID getRideId() {
        return rideId;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static void save(Ride r, String path){
        try{
            FileWriter csvWriter = new FileWriter(path, true);
            csvWriter.append(r.getRideId().toString());
            csvWriter.append(",");
            csvWriter.append(r.getCarModel());
            csvWriter.append(",");
            csvWriter.append(r.getLocation());
            csvWriter.append(",");
            csvWriter.append(r.getDestination());
            csvWriter.append(",");
            csvWriter.append(r.getDepartureTime());
            csvWriter.append(",");
            csvWriter.append(Integer.toString(r.getAvailableSeats()));
            csvWriter.append(",");
            csvWriter.append(Double.toString(r.getPrice()));
            csvWriter.append("\n");

            csvWriter.flush();
            csvWriter.close();
        }catch(Exception e){
            Display.error("Couldn't Schedule Ride [Database Error]");
            e.printStackTrace();
        }
    }
}
