
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Report {

    private String title;
    private Date dateGenerated;

    public Report(String title) {
        this.title = title;
        this.dateGenerated = new Date();
    }

    public void generateReport(List<TransactionHistory> transactions) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String fileName = "report_" + dateFormat.format(dateGenerated) + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Title: " + title + "\n");
            writer.write("Date Generated: " + dateFormat.format(dateGenerated) + "\n");

            writer.write("Transaction Details:\n");
            for (TransactionHistory transaction : transactions) {
                writer.write(transaction.getTransactionId() + ", " + transaction.getUserId() + ", "
                        + transaction.getTransactionType() + ", " + transaction.getPoints() + ", "
                        + transaction.getTransactionDate() + "\n");
            }

            System.out.println("Report generated successfully. File name: " + fileName);
        } catch (IOException e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }

    public void generateReport(List<TransactionHistory> transactions, String filterType, Date startDate, Date endDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String fileName = "report_" + dateFormat.format(dateGenerated) + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Title: " + title + "\n");
            writer.write("Date Generated: " + dateFormat.format(dateGenerated) + "\n");

            writer.write("Transaction Details (Filtered):\n");
            for (TransactionHistory transaction : transactions) {
                // Check if transaction meets filtering criteria
                if (Character.toString(transaction.getTransactionType()).equals(filterType)
                        && transaction.getTransactionDate().after(startDate)
                        && transaction.getTransactionDate().before(endDate)) {
                    writer.write(transaction.getTransactionId() + ", " + transaction.getUserId() + ", "
                            + transaction.getTransactionType() + ", " + transaction.getPoints() + ", "
                            + transaction.getTransactionDate() + "\n");
                }
            }

            System.out.println("Filtered report generated successfully. File name: " + fileName);
        } catch (IOException e) {
            System.out.println("Error generating filtered report: " + e.getMessage());
        }
    }

    // Method to generate report with totalEarnedPoints and totalRedemptionPoints based on given date range
    public void generateReport(int totalEarnedPoints, int totalRedemptionPoints, Date fromDate, Date toDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String fileName = "summary_report_" + dateFormat.format(dateGenerated) + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Title: " + title + "\n");
            writer.write("Date Generated: " + dateFormat.format(dateGenerated) + "\n");

            writer.write("Summary Report:\n");
            writer.write("From Date: " + fromDate + "\n");
            writer.write("To Date: " + toDate + "\n");
            writer.write("Total Earned Points: " + totalEarnedPoints + "\n");
            writer.write("Total Redemption Points: " + totalRedemptionPoints + "\n");

            System.out.println("Summary report generated successfully. File name: " + fileName);
        } catch (IOException e) {
            System.out.println("Error generating summary report: " + e.getMessage());
        }
    }
}
