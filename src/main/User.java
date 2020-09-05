package main;

import java.io.FileWriter;
import java.util.UUID;

// Data model for storing user information
public class User {

    private UUID userId;
    private String username;
    private String password;

    public User(String username, String password) {
        this.userId = UUID.randomUUID();
        this.username = username;
        this.password = password;
    }

    public User(UUID userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Static method to save a user information in the database
    public static void save(User user, String path){
        try{
            FileWriter csvWriter = new FileWriter(path, true);
            csvWriter.append(user.getUserId().toString());
            csvWriter.append(",");
            csvWriter.append(user.getUsername());
            csvWriter.append(",");
            csvWriter.append(user.getPassword());
            csvWriter.append("\n");

            csvWriter.flush();
            csvWriter.close();
        }catch(Exception e){
            Display.error("Couldn't Register User [Database Error]");
            e.printStackTrace();
        }
    }
}
