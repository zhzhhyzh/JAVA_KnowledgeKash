
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class QuestionRepository {

    private static final String DELIMITER = ",";
    static final String DIVIDER = "----------------------------------------------------------------------------------------------------------------------------------------------";

    public static final String QUES_FILE_PATH = "question.txt";

    //Find question by detail, to string method
    public void viewQuestion(int questionId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(QUES_FILE_PATH))) {
            String line;
            boolean dataFound = false;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",", -1);

                if (userData.length > 0 && Integer.parseInt(userData[0]) == questionId) {
                    System.out.println("Question ID: " + questionId);
                    dataFound = true;
                    int count = 0;
                    for (int i = 1; i < userData.length; i += 2) {
                        int questionIndex = i - count;
                        if (i + 1 < userData.length) {
                            System.out.println("Question" + questionIndex + ": " + userData[i]);
                            System.out.println("Answer" + questionIndex + ": " + userData[i + 1]);
                            count++;
                        }
                    }
                }
            }
            if (!dataFound) {
                System.out.println("Question with ID " + questionId + " not found.");
            }
        } catch (IOException e) {
            System.err.println("Error reading question file: " + e.getMessage());
        }
    }

    public static int listQuestion(int page) {
        //Page must start from 1
        int startLine = 1 + (page - 1) * 3;
        int endLine = startLine + 2;
        int lineCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(QUES_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;
                if (lineCount >= startLine && lineCount <= endLine) {
                    // Split the line using the delimiter ","
                    String[] userData = line.split(DELIMITER);
                    String questionId = userData[0];
                    if (userData.length > 0) {
                        System.out.println("Question ID: " + questionId);
                        int count = 0;
                        for (int i = 1; i < userData.length; i += 2) {
                            if (i + 1 < userData.length) {
                                int questionIndex = i - count;
                                System.out.println("Question" + questionIndex + ": " + userData[i]);
                                System.out.println("Answer" + questionIndex + ": " + userData[i + 1]);
                                count++;
                            }
                        }
                    }
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

    public void deleteQuestion(int questionId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(QUES_FILE_PATH)); BufferedWriter writer = new BufferedWriter(new FileWriter(QUES_FILE_PATH + ".tmp"))) {
            String line;
            boolean questionFound = false;

            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",", -1);

                if (userData.length > 0 && Integer.parseInt(userData[0]) == questionId) {
                    questionFound = true;
                    // Skip writing this line to the temporary file (effectively deleting it)
                } else {
                    // Write the line to the temporary file
                    writer.write(line);
                    writer.newLine();
                }

            }
            if (!questionFound) {
                System.out.println("Question with ID " + questionId + " not found.");
            } else {
                System.out.println("Question with ID " + questionId + " deleted successfully.");
            }
        } catch (IOException e) {
            System.err.println("Error reading question file: " + e.getMessage());
        }
        try {
            File originalFile = new File(QUES_FILE_PATH);
            File tempFile = new File(QUES_FILE_PATH + ".tmp");

            if (tempFile.exists() && tempFile.canRead() && originalFile.delete()) {
                if (!tempFile.renameTo(originalFile)) {
                    System.err.println("Failed to replace the original file with the temporary file.");
                }
            } else {
                System.err.println("Temporary file is missing or cannot be read, or failed to delete the original file.");
            }
        } catch (Exception ex) {
            System.err.println("Error replacing files: " + ex.getMessage());
        }
    }

    public int getTotalCount() {
        int lineCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(QUES_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {

                lineCount++;

            }
        } catch (IOException e) {
            System.err.println("Error reading user data from file: " + e.getMessage());
            return -1;
        }

        return lineCount;

    }

}
