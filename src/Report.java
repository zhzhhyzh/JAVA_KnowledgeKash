
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class Report {

    private static final String DIVIDER = "--------------------------------------------------------------------------------------";
    private static final String TRANSACTION_FILE_PATH = "transactionHistory.txt";

    public static void generateReport(int totalEarnedPoints, int totalRedemptionPoints, int totalExpiredPoints, LocalDate fromDate, LocalDate toDate) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String fileName = "summary_report_" + dateFormatter.format(LocalDate.now()) + ".txt";

        try {
            // Get the downloads folder path
            Path downloadsFolder = Paths.get(System.getProperty("user.home"), "Downloads");
            Path filePath = downloadsFolder.resolve(fileName);

            // Create the file if it doesn't exist
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

            // Write the report to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                writer.write("Title: Summary Report from " + fromDate + " to " + toDate + "\n");
                writer.write("Date Generated: " + LocalDate.now().format(dateFormatter) + "\n");

                writer.write("Summary Report:\n");
                writer.write("From Date: " + fromDate.format(dateFormatter) + "\n");
                writer.write("To Date: " + toDate.format(dateFormatter) + "\n");
                writer.write("Total Earned Points: " + totalEarnedPoints + "\n");
                writer.write("Total Redemption Points: " + totalRedemptionPoints + "\n");
                writer.write("Total Expired Points: " + totalExpiredPoints + "\n");

                System.out.println("Summary report is downloaded to: " + filePath);
            } catch (IOException e) {
                System.out.println("Error writing summary report to file: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error creating summary report file: " + e.getMessage());
        }
    }

    public static void findTransactionByUsername(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTION_FILE_PATH))) {
            String line;
            boolean found = false;
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            int totalEarnedPoint = 0;
            int totalRedemptedPoint = 0;
            int totalExpiredPoint = 0;
            // Create a path for the downloads folder
            Path downloadsFolder = Paths.get(System.getProperty("user.home"), "Downloads");
            Path filePath = downloadsFolder.resolve("transactions_" + username + "_" + dateFormatter.format(LocalDate.now()) + ".txt");

            // Create the file if it doesn't exist
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

            // Create a BufferedWriter to write to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                writer.write(DIVIDER + System.lineSeparator());
                writer.write(String.format("| %-15s | %-15s | %-15s | %-7s | %-19s |%n", "Transaction ID", "Username", "Type", "Points", "Date"));
                writer.write(DIVIDER + System.lineSeparator());

                while ((line = reader.readLine()) != null) {
                    String[] userData = line.split(",");

                    if (userData.length >= 2 && username.equals(userData[1].trim())) {
                        found = true;
                        if (userData[2].equals("E")) {
                            totalEarnedPoint += Integer.parseInt(userData[3].trim());
                        } else if (userData[2].equals("R")) {
                            totalRedemptedPoint += Integer.parseInt(userData[3].trim());
                        } else if (userData[2].equals("P")) {
                            totalExpiredPoint += Integer.parseInt(userData[3].trim());
                        }
                        // Write transaction data to the file
                        writer.write(String.format("| %-15s | %-15s | %-15s | %-7s | %-19s |%n",
                                userData[0], userData[1], userData[2], userData[3], userData[4]));
                    }
                }

                if (!found) {
                    writer.write("No transactions found for username: " + username);
                }

                writer.write(DIVIDER);
                writer.write("\nTotal Earned Points: " + totalEarnedPoint + "\nTotal Redeemed Points: " + totalRedemptedPoint
                        + "\nTotal Expired Points: " + totalExpiredPoint + "\nCurrently Available Points: " + (totalEarnedPoint - totalRedemptedPoint - totalExpiredPoint));
            }

            System.out.println("Report is downloaded to: " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading transaction file or writing to output file: " + e.getMessage());
        }
    }

}
