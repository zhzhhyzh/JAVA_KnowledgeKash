
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Date;

//Point Management Module
public class PointManagement {

    private String username;
    private int totalEarnedPoints;
    private int totalRedeemedPoints;
    private int availablePoints;
    private int totalExpiredPoints;
    private LocalDate lastEarningDate;
    private static final String DELIMITER = ",";
   

    public PointManagement() {
    }

    public PointManagement(String username, int totalEarnedPoints, int totalRedeemedPoints, int totalExpiredPoints) {
        this.username = username;
        this.totalEarnedPoints = totalEarnedPoints;
        this.totalRedeemedPoints = totalRedeemedPoints;
        calAvailablePoint(totalEarnedPoints, totalRedeemedPoints, totalExpiredPoints);
        this.totalExpiredPoints = totalExpiredPoints;
        this.lastEarningDate = LocalDate.now();
    }

    private int calAvailablePoint(int totalEarnedPoints, int totalRedeemedPoints, int totalExpiredPoints) {
        int availablePointss = totalEarnedPoints - totalRedeemedPoints - totalExpiredPoints;
        this.availablePoints = availablePointss;
        return availablePoints;
    }

    public String getUsername() {
        return username;
    }

    public int getTotalEarnedPoints() {
        return totalEarnedPoints;
    }

    public int getTotalRedeemedPoints() {
        return totalRedeemedPoints;
    }

    public int getTotalExpiredPoints() {
        return totalExpiredPoints;
    }

    public int getAvailablePoints() {
        return availablePoints;
    }

    public void updatePoints(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            File tempFile = new File("tempfile.txt");
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.contains(username)) {
                    String[] parts = line.split(",");
                    int earnPoints = Integer.parseInt(parts[6]);
                    int redeemedPoints = Integer.parseInt(parts[7]);
                    int expiredPoints = Integer.parseInt(parts[8]);
                    // update points
                    parts[6] = String.valueOf(totalEarnedPoints);
                    parts[7] = String.valueOf(totalRedeemedPoints);
                    parts[8] = String.valueOf(totalExpiredPoints);
                    parts[9] = lastEarningDate.toString();
                    //Rewrite the line
                    pw.println(String.join(",", parts));
                    calAvailablePoint(earnPoints, redeemedPoints, expiredPoints);
                } else {
                    // If the line does not contain the username, just copy it to the temporary file
                    pw.println(line);
                }
            }
            br.close();
            pw.close();
            // Delete the original file
            File originalFile = new File(filename);
            if (!originalFile.delete()) {
                System.err.println("Error: Could not delete original file");
                return;
            }
            // Rename the temporary file to the original filename
            if (!tempFile.renameTo(originalFile)) {
                System.err.println("Error: Could not rename temporary file");
            }

        } catch (IOException e) {
            System.err.println("Error reading user data from file: " + e.getMessage());
        }
    }

    public void increasePoints(int points, String fileName) {
        totalEarnedPoints += points;
        lastEarningDate = LocalDate.now();
        updatePoints(fileName);
//        return availablePoints;
    }

    public void decreasePoints(int points, String fileName) {
        totalRedeemedPoints += points;
        lastEarningDate = LocalDate.now();
        updatePoints(fileName);
//        return availablePoints;
    }

    @Override
    public String toString() {
        return "Total Earned Points : " + totalEarnedPoints + "\n"
                + "Total Redeemed Points : " + totalRedeemedPoints + "\n"
                + "Total Expired Points : " + totalExpiredPoints + "\n"
                + "Available Points : " + availablePoints;
    }

    public String[] getClient(String username, String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(DELIMITER);
                if (userData.length >= 9 && userData[0].equals(username)) {
                    // Extract the required information
                    String[] clientInfo = new String[4];

                    clientInfo[0] = this.username = userData[0]; // Username
                    this.totalEarnedPoints = Integer.parseInt(userData[6]);
                    this.totalRedeemedPoints = Integer.parseInt(userData[7]);
                    this.totalExpiredPoints = Integer.parseInt(userData[8]);
                    calAvailablePoint(this.totalEarnedPoints, this.totalRedeemedPoints, this.totalExpiredPoints);
                    clientInfo[1] = userData[6]; // total earn
                    clientInfo[2] = userData[7]; // total redeem
                    clientInfo[3] = userData[8]; // total expired
                    return clientInfo;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user data from file: " + e.getMessage());
        }
        return null;
    }

}
