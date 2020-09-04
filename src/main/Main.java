package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    public static Scanner in = new Scanner(System.in);

    public static String classPath = new File("").getAbsolutePath();
    public static String userPath = classPath.concat("/src/db/users.csv");
    public static String ridePath = classPath.concat("/src/db/rides.csv");
    public static String bookingPath = classPath.concat("/src/db/bookings.csv");

    public static boolean running = true;
    public static boolean loggedIn = false;
    public static UUID currentUserId = null;

    public static ArrayList<User> userList = new ArrayList<User>();
    public static ArrayList<Ride> rideList = new ArrayList<Ride>();
    public static ArrayList<UUID> bookedRidesList = new ArrayList<UUID>();

    public static void main(String[] args) {

        loadDatabase();
        init();

        while(running){
            int authResponse = authMenu();
            switch(authResponse){
                case 1: {
                    loggedIn = login();
                    while(loggedIn){
                        int mainResponse = mainMenu();

                        switch(mainResponse){
                            case 1:{
                                showAvailableRides();
                                break;
                            }
                            case 2:{
                                createRide();
                                break;
                            }
                            case 3:{
                                showBookedRides();
                                break;
                            }
                            case 4:{
                                logout();
                                break;
                            }
                            case 5:{
                                logout();
                                running = false;
                                break;
                            }
                        }
                    }
                    break;
                }
                case 2: {
                    register();
                    break;
                }
                case 3: {
                    running = false;
                    break;
                }
            }
        }

        System.out.println();
        Display.success("Successfully Exited The Program");

    }

    public static void loadDatabase(){
        try{
            String row;
            BufferedReader userReader = new BufferedReader(new FileReader(userPath));
            while ((row = userReader.readLine()) != null) {
                String[] data = row.split(",");
                User u = new User(UUID.fromString(data[0]), data[1], data[2]);

                userList.add(u);
            }
            userReader.close();

            BufferedReader rideReader = new BufferedReader(new FileReader(ridePath));
            while ((row = rideReader.readLine()) != null) {
                String[] data = row.split(",");
                Ride r = new Ride(UUID.fromString(data[0]), data[1], data[2], data[3], data[4], Integer.parseInt(data[5]), Double.parseDouble(data[6]));

                rideList.add(r);
            }
            rideReader.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void loadBookedList(){
        try{
            String row;
            BufferedReader bookingReader = new BufferedReader(new FileReader(bookingPath));
            while ((row = bookingReader.readLine()) != null) {
                String[] data = row.split(",");
                UUID userId = UUID.fromString(data[0]);
                UUID rideID = UUID.fromString(data[1]);
                if(currentUserId.compareTo(userId) == 0){
                    for(Ride r : rideList){
                        if(r.getRideId().compareTo(rideID) == 0){
                            bookedRidesList.add(r.getRideId());
                            break;
                        }
                    }
                }
            }
            bookingReader.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void init(){
        Display.menu("Welcome To CarPool");
        Display.menu("An App To Schedule And Share Rides With Others");
    }

    public static int prompt(){
        try{
            System.out.print("Your Choice> ");
            String res = in.next();

            return Integer.parseInt(res);
        } catch(Exception e){
            Display.error("Invalid Response!");
            return prompt();
        }
    }

    public static int authMenu(){
        int res;
        do {
            System.out.println();
            Display.menu("1. Log In");
            Display.menu("2. Register A New Account");
            Display.menu("3. Exit");
            System.out.println();
            res = prompt();
            if(res < 1 || res > 3){
                Display.error("Invalid Response!");
            }
        } while(res < 1 || res > 3);

        return res;
    }

    public static void register(){
        System.out.println();
        Display.menu("Register A New Account");
        System.out.print("Enter Username: ");
        String name = in.next();

        boolean foundUser = false;
        for(User u : userList){
            if(u.getUsername().equals(name)){
                foundUser = true;
                break;
            }
        }
        if(foundUser){
            Display.error("Username Already Exists");
            return;
        }

        System.out.print("Enter Password: ");
        String password = in.next();

        User newUser = new User(name, password);

        User.save(newUser, userPath);
        userList.add(newUser);

        Display.success("Successfully Registered");
    }

    public static boolean login(){
        System.out.println();
        Display.menu("Log In As Existing User");
        System.out.print("Enter Username: ");
        String name = in.next();

        boolean foundUser = false;
        User user = null;
        for(User u : userList){
            if(u.getUsername().equals(name)){
                foundUser = true;
                user = u;
                break;
            }
        }
        if(!foundUser){
            Display.error("No User Found With Given Username");
            return false;
        }

        String password = "something random that shouldn't match";
        while(!password.equals(user.getPassword())){
            System.out.print("Enter Password: ");
            password = in.next();

            if(!password.equals(user.getPassword())){
                Display.error("Incorrect Password");
            }
        }

        currentUserId = user.getUserId();
        loadBookedList();

        Display.success("Successfully Logged In");
        return true;
    }

    public static void logout(){
        currentUserId = null;
        loggedIn = false;
        bookedRidesList.clear();
        Display.success("Successfully Logged Out");
    }

    public static int mainMenu(){
        int res;
        do {
            System.out.println();
            Display.menu("1. Find A Ride");
            Display.menu("2. Schedule A Ride");
            Display.menu("3. Show Booked Rides");
            Display.menu("4. Log Out");
            Display.menu("5. Exit");
            System.out.println();

            res = prompt();
            if(res < 1 || res > 5){
                Display.error("Invalid Response!");
            }
        } while(res < 1 || res > 5);

        return res;
    }

    public static void showAvailableRides(){
        System.out.println();
        if(rideList.size() == 0){
            Display.error("No Available Rides Found");
            System.out.println();
        } else {
            Display.ride(rideList);
            System.out.println();
            Display.menu("Enter '-1' To Go Back");

            System.out.print("Choose A Ride Id To Book> ");
            int res = in.nextInt();
            if(res == -1) return;
            while(res < 1 || res > rideList.size()){
                Display.error("Invalid Choice");
                System.out.print("Choose A Ride Id To Book> ");
                res = in.nextInt();
            }

            bookRide(res-1);
        }
    }

    public static void createRide(){
        System.out.println();
        Display.menu("Schedule A Ride");
        System.out.print("Enter Location: ");
        String location = in.next();
        System.out.print("Enter Destination: ");
        String destination = in.next();
        System.out.print("Enter Car Model: ");
        String carModel = in.next();
        System.out.print("Enter Departure Time: ");
        String time = in.next();
        System.out.print("Enter Available Seats: ");
        int seats = in.nextInt();
        while(seats <= 0){
            Display.error("Cannot Create Ride With No Seats");
            System.out.print("Enter Available Seats: ");
            seats = in.nextInt();
        }

        System.out.print("Enter Price: ");
        double price = in.nextDouble();
        while(price <= 0){
            Display.error("Price Is Too Low");
            System.out.print("Enter Price: ");
            price = in.nextDouble();
        }
        System.out.println();

        Ride newRide = new Ride(carModel, location, destination, time, seats, price);

        Ride.save(newRide, ridePath);
        rideList.add(newRide);

        Display.success("Successfully Scheduled A Ride");
    }

    public static void showBookedRides(){
        System.out.println();
        if(bookedRidesList.size() == 0){
            Display.error("No Booked Rides Found");
            System.out.println();
        } else {
            ArrayList<Ride> bookedList = new ArrayList<Ride>();
            for(UUID id : bookedRidesList){
                for(Ride r : rideList){
                    if(r.getRideId().compareTo(id) == 0){
                        bookedList.add(r);
                        break;
                    }
                }
            }
            Display.ride(bookedList);
        }
    }

    public static void bookRide(int index){
        Ride ride = rideList.get(index);

        for(UUID id : bookedRidesList){
            if(id.compareTo(ride.getRideId()) == 0){
                Display.error("This Ride Is Already Booked For This User");
                return;
            }
        }

        try{
            FileWriter csvWriter = new FileWriter(bookingPath, true);
            csvWriter.append(currentUserId.toString());
            csvWriter.append(",");
            csvWriter.append(ride.getRideId().toString());
            csvWriter.append("\n");

            csvWriter.flush();
            csvWriter.close();

            bookedRidesList.add(ride.getRideId());
        }catch(Exception e){
            Display.error("Couldn't Book Ride [Database Error]");
            e.printStackTrace();
            return;
        }

        Display.success("Successfully Booked A Ride");
        System.out.println();
    }
}
