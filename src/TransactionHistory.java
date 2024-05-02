
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionHistory {

    private static final String TRANSACTION_FILE_PATH = "transactionHistory.txt";
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

    public void writeTransactionToFile() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String transactionDetails = transactionId + "," + username + "," + transactionType + "," + points + "," + dateFormat.format(transactionDate) + "\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactionHistory.txt", true))) {
            writer.write(transactionDetails);
            System.out.println("Transaction details written to transactionHistory.txt successfully.");
        } catch (IOException e) {
            System.out.println("Error writing transaction details to file: " + e.getMessage());
        }
    }

    public static List<TransactionHistory> listTransactions(char filterTransactionType, Date startDate, Date endDate) {
        List<TransactionHistory> transactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("transactionHistory.txt"))) {
            String line;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int transactionId = Integer.parseInt(parts[0]);
                String username = parts[1];
                char transactionType = parts[2].charAt(0);
                int points = Integer.parseInt(parts[3]);
                Date transactionDate = dateFormat.parse(parts[4]);

                // Apply filters
                if ((filterTransactionType == '\u0000' || transactionType == filterTransactionType)
                        && (startDate == null || transactionDate.after(startDate))
                        && (endDate == null || transactionDate.before(endDate))) {
                    TransactionHistory transaction = new TransactionHistory();
                    transaction.setTransactionId(transactionId);
                    transaction.setUserId(username);
                    transaction.setTransactionType(transactionType);
                    transaction.setPoints(points);
                    transaction.setTransactionDate(transactionDate);
                    transactions.add(transaction);
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error reading transaction history: " + e.getMessage());
        }

        return transactions;
    }

    //Admin using to find list
    public static List<TransactionHistory> listTransactionsByUsername(String username) {
        List<TransactionHistory> userTransactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("transactionHistory.txt"))) {
            String line;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int transactionId = Integer.parseInt(parts[0]);
                String user = parts[1];
                char transactionType = parts[2].charAt(0);
                int points = Integer.parseInt(parts[3]);
                Date transactionDate = dateFormat.parse(parts[4]);

                if (user.equals(username)) {
                    TransactionHistory transaction = new TransactionHistory();
                    transaction.setTransactionId(transactionId);
                    transaction.setUserId(user);
                    transaction.setTransactionType(transactionType);
                    transaction.setPoints(points);
                    transaction.setTransactionDate(transactionDate);
                    userTransactions.add(transaction);
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error reading transaction history: " + e.getMessage());
        }

        return userTransactions;
    }

}
