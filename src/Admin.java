
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Admin extends User {
static final String DIVIDER = "----------------------------------------------------------------------------------------------------------------------------------------------";
    private static final String DELIMITER = ",";

    Admin() {
    }

    Admin(String username, String password) {
        super(username, password);
    }

    public static int listUsers(int page, String fileName) {
        //Page must start from 1
        int startLine = (page - 1) * 20 + 4;
        int endLine = startLine + 19;
        int lineCount = 0;

        System.out.println(DIVIDER);
        System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s | %-23s |%n",
                "Username", "Password", "Name", "Phone Number", "Email", "Available Points");
        System.out.println(DIVIDER);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                if (lineCount >= startLine && lineCount <= endLine) {
                    String[] userData = line.split(DELIMITER);
                    // Calculate available points
                    int totalEarned = Integer.parseInt(userData[6]);
                    int totalRedeemed = Integer.parseInt(userData[7]);
                    int totalExpired = Integer.parseInt(userData[8]);
                    int availablePoints = totalEarned - totalRedeemed - totalExpired;
                    System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s |  %-22d |%n",
                            userData[0], userData[1], userData[2], userData[3], userData[4], availablePoints);
                }

            }
        } catch (IOException e) {
            System.err.println("Error reading user data from file: " + e.getMessage());
            return -1;
        }

        // Print the footer
        System.out.println(DIVIDER);
        return lineCount;
    }

}
