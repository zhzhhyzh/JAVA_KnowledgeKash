
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionHistory {

    private static final int TRANSACTIONS_PER_PAGE = 20;
    private static final String TRANSACTION_FILE_PATH = "transactionHistory.txt";
    private static final String DIVIDER = "--------------------------------------------------------------------------------------";

    private static final String DELIMITER = ",";
    private int transactionId;
    private String username;
    private char transactionType;
    private int points;
    private Date transactionDate;

    TransactionHistory() {
    }

    public TransactionHistory(String username, char transactionType, int points) {
        this.username = username;
        this.transactionType = transactionType;
        this.points = points;
        this.transactionDate = new Date();
        this.transactionId = getLastTransactionId() + 1;
    }

    private int getLastTransactionId() {
        int maxId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTION_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] transactionData = line.split(DELIMITER);
                int id = Integer.parseInt(transactionData[0]);
                if (id > maxId) {
                    maxId = id;
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading transaction history file: " + e.getMessage());
        }
        return maxId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public void setUserId(String username) {
        this.username = username;
    }

    public void setTransactionType(char transactionType) {
        this.transactionType = transactionType;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public String getUserId() {
        return username;
    }

    public int getPoints() {
        return points;
    }

    public char getTransactionType() {
        return transactionType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void writeTransactionToFile(String fileName) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String transactionDetails = transactionId + "," + username + "," + transactionType + "," + points + "," + dateFormat.format(transactionDate) + "\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(transactionDetails);
            System.out.println("Transaction successfully.");
        } catch (IOException e) {
            System.out.println("Error writing transaction details to file: " + e.getMessage());
        }
    }

    public static String[] listTransaction(int page, String fileName, int pageLimit) {
        int lineCount = 0;
        int arrayCount = 0;
        String[] arrayListForTransaction = new String[101];
        int startIndex = (page - 1) * pageLimit + 1;
        int endIndex = startIndex + pageLimit - 1;
//        System.out.println(DIVIDER);
//        System.out.printf("| %-15s | %-15s | %-10s | %-11s | %-19s |%n", "Transaction ID", "Username", "Type", "Points", "Date");
//        System.out.println(DIVIDER);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                if (lineCount >= startIndex && lineCount <= endIndex) {
                    String[] userData = line.split(",");
                    // Print transaction data in table format
                    arrayListForTransaction[arrayCount] = userData[0];
                    arrayCount++;
                    arrayListForTransaction[arrayCount] = userData[1];
                    arrayCount++;
                    arrayListForTransaction[arrayCount] = userData[2];
                    arrayCount++;
                    arrayListForTransaction[arrayCount] = userData[3];
                    arrayCount++;
                    arrayListForTransaction[arrayCount] = userData[4];
                    arrayCount++;
                } else if (lineCount > endIndex) {
                    break; // Exit loop if reached end index
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading transaction file: " + e.getMessage());

        }
        //        System.out.println(DIVIDER);
        arrayListForTransaction[100] = String.valueOf(lineCount);
        return arrayListForTransaction;
    }

    public static String[] listTransactionByType(char transactionType, int page, String fileName, int pageLimit) {
        int lineCount = 0;
        int arrayCount = 0;
        String[] arrayListForTransaction = new String[101];
        int startIndex = (page - 1) * pageLimit + 1;
        int endIndex = startIndex + pageLimit - 1;
//        System.out.println(DIVIDER);
//        System.out.printf("| %-15s | %-15s | %-10s | %-11s | %-19s |%n", "Transaction ID", "Username", "Type", "Points", "Date");
//        System.out.println(DIVIDER);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                char type = userData[2].charAt(0); // Get the transaction type
                if (type == transactionType) {
                    lineCount++;
                    if (lineCount >= startIndex && lineCount <= endIndex) {
                        // Print transaction data in table format
//                        System.out.printf("| %-15s | %-15s | %-10s | %-11s | %-19s |%n",
//                                userData[0], userData[1], userData[2], userData[3], userData[4]);

                        arrayListForTransaction[arrayCount] = userData[0];
                        arrayCount++;
                        arrayListForTransaction[arrayCount] = userData[1];
                        arrayCount++;
                        arrayListForTransaction[arrayCount] = userData[2];
                        arrayCount++;
                        arrayListForTransaction[arrayCount] = userData[3];
                        arrayCount++;
                        arrayListForTransaction[arrayCount] = userData[4];
                        arrayCount++;

                    } else if (lineCount > endIndex) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading transaction file: " + e.getMessage());
        }
//        System.out.println(DIVIDER);
        arrayListForTransaction[100] = String.valueOf(lineCount);
        return arrayListForTransaction;
    }

    public static String[] listTransactionByDate(LocalDate startDate, LocalDate endDate, int page, String fileName, int pageLimit) {
        int lineCount = 0;
        int arrayCount = 0;
        String[] arrayListForTransaction = new String[104];
        int startIndex = (page - 1) * pageLimit + 1;
        int endIndex = startIndex + pageLimit - 1;
        int totalEarnedPoint = 0;
        int totalRedemptedPoint = 0;
        int totalExpiredPoint = 0;
//        System.out.println(DIVIDER);
//        System.out.printf("| %-15s | %-15s | %-10s | %-11s | %-19s |%n", "Transaction ID", "Username", "Type", "Points", "Date");
//        System.out.println(DIVIDER);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");

                LocalDateTime transactionDate = LocalDateTime.parse(userData[4], formatter);

                LocalDate parsedTransactionDate = transactionDate.toLocalDate();

                if ((parsedTransactionDate.isEqual(startDate) || parsedTransactionDate.isAfter(startDate))
                        && (parsedTransactionDate.isEqual(endDate) || parsedTransactionDate.isBefore(endDate))) {

                    lineCount++;
                    if (userData[2].equals("E")) {
                        totalEarnedPoint += Integer.parseInt(userData[3].trim());
                    } else if (userData[2].equals("R")) {
                        totalRedemptedPoint += Integer.parseInt(userData[3].trim());
                    } else if (userData[2].equals("P")) {
                        totalExpiredPoint += Integer.parseInt(userData[3].trim());
                    }
                    if (lineCount >= startIndex && lineCount <= endIndex) {
                        arrayListForTransaction[arrayCount] = userData[0];
                        arrayCount++;
                        arrayListForTransaction[arrayCount] = userData[1];
                        arrayCount++;
                        arrayListForTransaction[arrayCount] = userData[2];
                        arrayCount++;
                        arrayListForTransaction[arrayCount] = userData[3];
                        arrayCount++;
                        arrayListForTransaction[arrayCount] = userData[4];
                        arrayCount++;
                    } else if (lineCount > endIndex) {
                        break; // Exit loop if reached end index
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading transaction file: " + e.getMessage());
        }
        System.out.println(DIVIDER);
        arrayListForTransaction[101] = String.valueOf(totalEarnedPoint);
        arrayListForTransaction[102] = String.valueOf(totalRedemptedPoint);
        arrayListForTransaction[103] = String.valueOf(totalExpiredPoint);

        //        System.out.println(DIVIDER);
        arrayListForTransaction[100] = String.valueOf(lineCount);
        return arrayListForTransaction;
    }

    public static String[] listTransactionByTypeAndDate(char transactionType, LocalDate startDate, LocalDate endDate, int page, String fileName, int pageLimit) {
        int lineCount = 0;
        int arrayCount = 0;
        String[] arrayListForTransaction = new String[101];
        int startIndex = (page - 1) * pageLimit + 1;
        int endIndex = startIndex + pageLimit - 1;

//        System.out.println(DIVIDER);
//        System.out.printf("| %-15s | %-15s | %-10s | %-11s | %-19s |%n", "Transaction ID", "Username", "Type", "Points", "Date");
//        System.out.println(DIVIDER);
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                char type = userData[2].charAt(0);
                LocalDateTime transactionDate = LocalDateTime.parse(userData[4], formatter);

                LocalDate parsedTransactionDate = transactionDate.toLocalDate();

                if ((parsedTransactionDate.isEqual(startDate) || parsedTransactionDate.isAfter(startDate))
                        && (parsedTransactionDate.isEqual(endDate) || parsedTransactionDate.isBefore(endDate))) {

                    lineCount++;
                    if (lineCount >= startIndex && lineCount <= endIndex) {
//                        System.out.printf("|%-15s | %-15s | %-10s| %11s | %-19s | %n", userData[0], userData[1], userData[2], userData[3], userData[4]);
                        arrayListForTransaction[arrayCount] = userData[0];
                        arrayCount++;
                        arrayListForTransaction[arrayCount] = userData[1];
                        arrayCount++;
                        arrayListForTransaction[arrayCount] = userData[2];
                        arrayCount++;
                        arrayListForTransaction[arrayCount] = userData[3];
                        arrayCount++;
                        arrayListForTransaction[arrayCount] = userData[4];
                        arrayCount++;
                    } else if (lineCount > endIndex) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading transaction file: " + e.getMessage());
        }
//        System.out.println(DIVIDER);
        arrayListForTransaction[100] = String.valueOf(lineCount);
        return arrayListForTransaction;
    }

    public static String[] findTransactionByUsername(String username, int page , String fileName, int pageLimit) {
        int lineCount = 0;
        int arrayCount = 0;
        String[] arrayListForTransaction = new String[101];
        int startIndex = (page - 1) * pageLimit + 1;
        int endIndex = startIndex + pageLimit - 1;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean found = false;

//            System.out.println(DIVIDER);
//            System.out.printf("| %-15s | %-15s | %-10s | %-11s | %-19s |%n", "Transaction ID", "Username", "Type", "Points", "Date");
//            System.out.println(DIVIDER);
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length >= 2 && username.equals(userData[1].trim())) {
                    lineCount++;
                    if (lineCount >= startIndex && lineCount <= endIndex) {
                        found = true;
                        arrayListForTransaction[arrayCount] = userData[0];
                        arrayCount++;
                        arrayListForTransaction[arrayCount] = userData[1];
                        arrayCount++;
                        arrayListForTransaction[arrayCount] = userData[2];
                        arrayCount++;
                        arrayListForTransaction[arrayCount] = userData[3];
                        arrayCount++;
                        arrayListForTransaction[arrayCount] = userData[4];
                        arrayCount++;
                    } else if (lineCount > endIndex) {
                        break; // Exit loop if reached end index
                    }
                }
            }

            if (!found) {
                System.out.println("No transactions found for username: " + username);
            }

            System.out.println(DIVIDER);
        } catch (IOException e) {
            System.err.println("Error reading transaction file: " + e.getMessage());
        }
        arrayListForTransaction[100] = String.valueOf(lineCount);
        return arrayListForTransaction;
    }

}
