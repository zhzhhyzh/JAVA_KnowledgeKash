
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Policy {

    public static int dayCount;

    public static void applyPolicy(String username) {
        LocalDate currentDate = LocalDate.now();

        LocalDate periodTerm = currentDate.minusDays(dayCount);

        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            StringBuilder fileContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[0].equals(username)) {
                    String transactionDate = userData[9];
                    if (transactionDate.equals("null")) {
                        userData[9] = currentDate.toString();
                    } else if (LocalDate.parse(transactionDate).isBefore(periodTerm) && Integer.parseInt(userData[8]) >= 50) {
                        int currentPoints = Integer.parseInt(userData[8]);
                        currentPoints -= 10;
                        TransactionHistory th = new TransactionHistory(username, 'P', 10);
                        th.writeTransactionToFile();
                        userData[8] = String.valueOf(currentPoints);
                        userData[9] = currentDate.toString();
                    }
                }
                fileContent.append(String.join(",", userData)).append("\n");
            }

            // Write the updated content back to the file
            try (FileWriter writer = new FileWriter("user.txt")) {
                writer.write(fileContent.toString());
            } catch (IOException e) {
                System.err.println("Error writing to user file: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error reading user file: " + e.getMessage());
        }
    }

    public static void setDayCount(int passInDayCount) {
        dayCount = passInDayCount;
        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            StringBuilder fileContent = new StringBuilder();
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (lineCount < 3) {
                    userData[8] = String.valueOf(passInDayCount);
                }
                fileContent.append(String.join(",", userData)).append("\n");
                lineCount++;
            }

            // Write the updated content back to the file
            try (FileWriter writer = new FileWriter("user.txt")) {
                writer.write(fileContent.toString());
            } catch (IOException e) {
                System.err.println("Error writing to user file: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error reading user file: " + e.getMessage());
        }

    }

    public static int getDayCount() {
        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            String firstLine = reader.readLine();
            if (firstLine != null) {
                String[] userData = firstLine.split(",");
                if (userData.length > 8) {
                    dayCount = Integer.parseInt(userData[8]);
//                    System.err.println("Daycount hereee>>>>>" + dayCount);
//                    System.err.println("Daycount hereee>>>>>" + userData[0]);
//                    System.err.println("Daycount hereee>>>>>" + userData[1]);
//                    System.err.println("Daycount hereee>>>>>" + userData[2]);
//                    System.err.println("Daycount hereee>>>>>" + userData[3]);
//
//                    System.err.println("Daycount hereee>>>>>" + userData[8]);
//                    System.err.println("Daycount hereee>>>>>" + userData[9]); testing purpose

                    return Integer.parseInt(userData[8]);
                } else {
                    System.err.println("Invalid format in user file: Missing field at index 8");
                }
            } else {
                System.err.println("Empty user file: No data found");
            }
        } catch (IOException e) {
            System.err.println("Error reading user file: " + e.getMessage());
        }
        return 0;
    }

    public static void showExpired(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length > 9 && username.equals(userData[0].trim())) {
                    String lastTransactionDateStr = userData[9].trim();
                    LocalDate lastTransactionDate = LocalDate.parse(lastTransactionDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate currentDate = LocalDate.now();
                    getDayCount();
                    long daysLeft = ChronoUnit.DAYS.between(currentDate, lastTransactionDate.plusDays(dayCount));
                    System.out.println("Days left until 10 points deduction: " + daysLeft);
                    break; // Exit the loop after processing the user data
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user file: " + e.getMessage());
        }
    }

}
