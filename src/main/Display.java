package main;

import java.util.ArrayList;

// This is a utility class that colorizes console output
public class Display {

    public static final String ANSI_RESET  = "\u001B[0m";

    public static final String ANSI_BLACK  = "\u001B[30m";
    public static final String ANSI_RED    = "\u001B[31m";
    public static final String ANSI_GREEN  = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE   = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN   = "\u001B[36m";
    public static final String ANSI_WHITE  = "\u001B[37m";

    public static final String ANSI_BRIGHT_BLACK  = "\u001B[90m";
    public static final String ANSI_BRIGHT_RED    = "\u001B[91m";
    public static final String ANSI_BRIGHT_GREEN  = "\u001B[92m";
    public static final String ANSI_BRIGHT_YELLOW = "\u001B[93m";
    public static final String ANSI_BRIGHT_BLUE   = "\u001B[94m";
    public static final String ANSI_BRIGHT_PURPLE = "\u001B[95m";
    public static final String ANSI_BRIGHT_CYAN   = "\u001B[96m";
    public static final String ANSI_BRIGHT_WHITE  = "\u001B[97m";

    public static final String ANSI_BG_BLACK  = "\u001B[40m";
    public static final String ANSI_BG_RED    = "\u001B[41m";
    public static final String ANSI_BG_GREEN  = "\u001B[42m";
    public static final String ANSI_BG_YELLOW = "\u001B[43m";
    public static final String ANSI_BG_BLUE   = "\u001B[44m";
    public static final String ANSI_BG_PURPLE = "\u001B[45m";
    public static final String ANSI_BG_CYAN   = "\u001B[46m";
    public static final String ANSI_BG_WHITE  = "\u001B[47m";

    public static final String ANSI_BRIGHT_BG_BLACK  = "\u001B[100m";
    public static final String ANSI_BRIGHT_BG_RED    = "\u001B[101m";
    public static final String ANSI_BRIGHT_BG_GREEN  = "\u001B[102m";
    public static final String ANSI_BRIGHT_BG_YELLOW = "\u001B[103m";
    public static final String ANSI_BRIGHT_BG_BLUE   = "\u001B[104m";
    public static final String ANSI_BRIGHT_BG_PURPLE = "\u001B[105m";
    public static final String ANSI_BRIGHT_BG_CYAN   = "\u001B[106m";
    public static final String ANSI_BRIGHT_BG_WHITE  = "\u001B[107m";

    // Displays menus
    public static void menu(String s){
        System.out.print(ANSI_BRIGHT_BG_BLACK + ANSI_BLUE + " " + s + " ");
        System.out.println(ANSI_RESET);
    }

    // Displays error messages
    public static void error(String s){
        System.out.print(ANSI_BG_RED + ANSI_BRIGHT_WHITE + " " + s + " ");
        System.out.println(ANSI_RESET);
    }

    // Displays success messages
    public static void success(String s){
        System.out.print(ANSI_BG_GREEN + ANSI_BRIGHT_WHITE + " " + s + " ");
        System.out.println(ANSI_RESET);
    }

    // Displays all ride information in a table
    public static void ride(ArrayList<Ride> list){
        System.out.format("%s%s %5s %15s %15s %15s %15s %15s %15s ",ANSI_BRIGHT_BG_BLACK, ANSI_YELLOW, "Id", "Car Model", "Location", "Destination", "Departure Time", "Available Seats", "Price ($)");
        System.out.println(ANSI_RESET);
        for(int i=0; i<list.size(); i++){
            Ride r = list.get(i);
            System.out.format("%s%s %5s %15s %15s %15s %15s %15s %15s ",ANSI_BRIGHT_BG_BLACK, ANSI_BLUE, i+1, r.getCarModel(), r.getLocation(), r.getDestination(), r.getDepartureTime(), r.getAvailableSeats(), "$"+r.getPrice());
            System.out.println(ANSI_RESET);
        }
    }

}