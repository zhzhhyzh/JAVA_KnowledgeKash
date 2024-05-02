
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class User {

    protected String username;
    protected String password;

    private static final String USER_FILE_PATH = "user.txt";
    private static final String DELIMITER = ",";

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public static String login(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(DELIMITER);
                // Ensure line contains at least 2 elements
                if (userData.length >= 2) {
                    String storedUsername = userData[0];
                    String storedPassword = userData[1];
                    // Check if the provided username and password match stored credentials
                    if (username.equals(storedUsername) && password.equals(storedPassword)) {
                        return userData[0];
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user data from file: " + e.getMessage());
        }
        return null;

        /* Sample Usage:  User loggedInUser = User.login(username, password);

        // Check if login was successful
        if (loggedInUser != null) {
            System.out.println("Login successful! Welcome, " + loggedInUser.getUsername() + "!");
        } else {
            System.out.println("Login failed. Invalid username or password.");
        } */
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
