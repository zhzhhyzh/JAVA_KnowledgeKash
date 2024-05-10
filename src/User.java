
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class User {

    protected String username;
    protected String password;
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
    
      public void setUsername(String username) {
        this.username = username;
    }

    public static String login(String username, String password, String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(DELIMITER);
                if (userData.length >= 2) {
                    String storedUsername = userData[0];
                    String storedPassword = userData[1];
                    if (username.equals(storedUsername) && password.equals(storedPassword)) {
                        return userData[0];
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user data from file: " + e.getMessage());
        }
        return null;
    }

  

}
