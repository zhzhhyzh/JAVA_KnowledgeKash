
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class QuestionRepository {

    private static final String DELIMITER = ",";
    static final String DIVIDER = "----------------------------------------------------------------------------------------------------------------------------------------------";
    static final String DIVIDER2 = "==============================================================================================================================================";

    

    //Find question by detail, to string method
    public void viewQuestion(int questionId, String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean dataFound = false;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",", -1);

                if (userData.length > 0 && userData[0].matches("\\d+")) {
                    int storedQuestionId = Integer.parseInt(userData[0]);
                    if (storedQuestionId == questionId) {
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
            }
            if (!dataFound) {
                System.out.println("Question with ID " + questionId + " not found.");
            }

        } catch (IOException e) {
            System.err.println("Error reading question file: " + e.getMessage());
        }
    }

    public static int listQuestion(int page, String fileName) {
        //Page must start from 1
        int startLine = 1 + (page - 1) * 3;
        int endLine = startLine + 2;
        int lineCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
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
                                System.out.println("Question " + questionId + "." + questionIndex + ": " + userData[i]);
//                                System.out.println("Answer " + questionId + "." + questionIndex + ": " + userData[i + 1]);
                                count++;
                            }
                        }
                        //Print footer for each
                        System.out.println(DIVIDER2);
                    }
                }

            }
        } catch (IOException e) {
            System.err.println("Error reading user data from file: " + e.getMessage());
            return -1;
        }

        return lineCount ;
    }

    public void deleteQuestion(int questionId, String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName)); BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".tmp"))) {
            String line;
            boolean questionFound = false;

            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",", -1);

                if (userData.length > 0 && userData[0].matches("\\d+")) {
                    int storedQuestionId = Integer.parseInt(userData[0]);
                    if (storedQuestionId == questionId) {
                        questionFound = true;
                        // Skip writing this line to the temporary file (effectively deleting it)
                    } else {
                        // Write the line to the temporary file
                        writer.write(line);
                        writer.newLine();
                    }

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
            File originalFile = new File(fileName);
            File tempFile = new File(fileName + ".tmp");

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

    public int getTotalCount(String fileName) {
        int lineCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
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
