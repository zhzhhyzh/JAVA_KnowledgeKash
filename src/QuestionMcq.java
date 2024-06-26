
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class QuestionMcq extends QuestionRepository implements QuestionElements {

    private static int questionId;
    private static final String DELIMITER = ",";

    private static int getRunningQuestionId(String fileName) {
        int missingNumber = -1;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean[] numberExists = new boolean[101];

            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",", -1);

                if (userData.length > 0) {
                    int runningNo = Integer.parseInt(userData[0].trim());
                    if (runningNo >= 1 && runningNo <= 100) {
                        numberExists[runningNo] = true;
                    }
                }

            }
            for (int i = 1; i < numberExists.length; i++) {
                if (!numberExists[i]) {
                    missingNumber = i;
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading question file: " + e.getMessage());
            return -1;
        }
        questionId = missingNumber;
        return missingNumber;
    }

    @Override
    public int pointDistribute(int questionIndex, String answer) {
        answer = answer.toUpperCase();
        switch (questionIndex) {
            case 1:
                if (answerCompare(answer)) {
                    return 1;
                } else {
                    return 0;
                }
            case 2:
                if (answerCompare(answer)) {
                    return 2;
                } else {
                    return 0;
                }
            case 3:
                if (answerCompare(answer)) {
                    return 2;
                } else {
                    return 0;
                }
            case 4:
                if (answerCompare(answer)) {
                    return 2;
                } else {
                    return 0;
                }
            case 5:
                if (answerCompare(answer)) {
                    return 2;
                } else {
                    return 0;
                }
            case 6:
                if (answerCompare(answer)) {
                    return 3;
                } else {
                    return 0;
                }
            case 7:
                if (answerCompare(answer)) {
                    return 4;
                } else {
                    return 0;
                }
            case 8:
                if (answerCompare(answer)) {
                    return 5;
                } else {
                    return 0;
                }
            case 9:
                if (answerCompare(answer)) {
                    return 4;
                } else {
                    return 0;
                }
            case 10:
                if (answerCompare(answer)) {
                    return 5;
                } else {
                    return 0;
                }
            default:
                return 0;
        }

    }

    @Override
    public int answerQuestion(int questionId, String fileName) {
        Scanner scanner = new Scanner(System.in);
        int pointAccummulate = 0;
        boolean dataFound = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",", -1);

                if (userData.length > 0 && Integer.parseInt(userData[0]) == questionId) {
                    System.out.println("Question ID: " + questionId);
                    dataFound = true;
                    int count = 0;
                    for (int i = 1; i < userData.length; i += 2) {
                        if (i + 1 < userData.length) {
                            System.out.println("Question" + (i - count) + ": " + userData[i]);
                            System.out.println("Answer for question" + (i - count) + ": " + userData[i + 1]);

                            int questionIndex = i - count;
                            count++;
                            System.out.print("Enter your answer: ");
                            String answer = scanner.nextLine();
                            int holdPoint = pointDistribute(questionIndex, answer);
                            pointAccummulate += holdPoint;
                            if (holdPoint == 0) {
                                System.out.println("No point will added due to invalid input.");
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
            return 0;
        }
        return pointAccummulate;
    }

    @Override
    public void createQuestion(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            Scanner scanner = new Scanner(System.in);

            // Get the next available question ID
            questionId = getRunningQuestionId(fileName);
            String[] tempSave = new String[20];
            for (int i = 0; i < 20; i++) {
                if (i % 2 == 0) {
                    System.out.println("Enter the question" + (i / 2 + 1) + ":");
                    tempSave[i] = scanner.nextLine();

                } else {
                    System.out.println("Enter the answer" + (i / 2 + 1) + ":");
                    tempSave[i] = scanner.nextLine();

                }
            }

            // Write the question details to the file
            writer.write(questionId + "," + tempSave[0] + ","
                    + tempSave[1] + "," + tempSave[2] + "," + tempSave[3]
                    + "," + tempSave[4] + "," + tempSave[5] + "," + tempSave[6]
                    + "," + tempSave[7] + "," + tempSave[8] + "," + tempSave[9]
                    + "," + tempSave[10] + "," + tempSave[11] + "," + tempSave[12]
                    + "," + tempSave[13] + "," + tempSave[14] + "," + tempSave[15]
                    + "," + tempSave[16] + "," + tempSave[17] + "," + tempSave[18]
                    + "," + tempSave[19]);
            writer.newLine();
            InteractionMenu.clearScreen();

            System.out.println("\n".repeat(100)); // Print 50 newlines

            System.out.println("Question created successfully!");
            System.out.println(DIVIDER);

        } catch (IOException e) {
            System.err.println("Error writing to question file: " + e.getMessage());
        }
    }

    @Override
    public boolean updateQuestion(int questionId, String fileName) {
        String[] tempSaveQuestion = new String[20];
        Scanner scanner = new Scanner(System.in);
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName)); BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".tmp"))) {

            String line;
            boolean updated = false;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(DELIMITER);
                if (userData.length >= 1) {
                    String storedQuestionId = userData[0];

                    if (!storedQuestionId.isEmpty() && questionId == Integer.parseInt(storedQuestionId)) {

                        System.out.println("Question ID: " + questionId);
                        System.out.println("*No changes question or answer click \"Enter\"");
                        int count = 0;
                        for (int i = 1; i < userData.length; i += 2) {
                            int questionIndex = i - count;
                            count++;
                            if (i + 1 < userData.length) {
                                System.out.println("Question" + questionIndex + ": " + userData[i]);
                                System.out.print("Enter question:");

                                String questionChanger = scanner.nextLine();
                                if (questionChanger.isEmpty()) {
                                    tempSaveQuestion[i - 1] = userData[i];
                                } else {
                                    tempSaveQuestion[i - 1] = questionChanger;
                                }
                                System.out.println("Answer" + questionIndex + ": " + userData[i + 1]);
                                System.out.print("Enter answer:");

                                String AnswerChanger = scanner.nextLine();
                                if (AnswerChanger.isEmpty()) {
                                    tempSaveQuestion[i] = userData[i + 1];
                                } else {
                                    tempSaveQuestion[i] = AnswerChanger;
                                }
                            }
                        }
                        // Construct the updated line
                        String updatedLine = questionId + DELIMITER + String.join(DELIMITER, tempSaveQuestion);
                        // Write the updated line to the temporary file
                        writer.write(updatedLine);
                        writer.newLine();
                        updated = true;
                    } else {
                        // Write the unchanged line to the temporary file
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }

            if (!updated) {
                InteractionMenu.clearScreen();

                System.out.println("\n".repeat(100)); // Print 50 newlines

                System.err.println("Question not found.");
                System.out.println(DIVIDER);
                return false;
            }
        } catch (IOException e) {
            System.err.println("Error updating question data: " + e.getMessage());
            return false;
        }

        // Replace the original file with the temporary file
        try {
            File originalFile = new File(fileName);
            File tempFile = new File(fileName + ".tmp");

            if (!tempFile.exists() || !tempFile.canRead()) {
                System.err.println("Temporary file is missing or cannot be read.");
                return false;
            }

            if (!originalFile.delete()) {
                System.err.println("Failed to delete the original file.");
                return false;
            }

            if (!tempFile.renameTo(originalFile)) {
                System.err.println("Failed to replace the original file with the temporary file.");
                return false;
            }
            InteractionMenu.clearScreen();

            System.out.println("\n".repeat(100)); // Print 50 newlines

            System.out.println("Question updated.");
            System.out.println(DIVIDER);
            return true;
        } catch (Exception ex) {
            System.err.println("Error replacing files: " + ex.getMessage());
            return false;
        }

    }

    private boolean answerCompare(String answer) {
        return answer.equals("A") || answer.equals("B") || answer.equals("C") || answer.equals("D") || answer.equals("a") || answer.equals("b") || answer.equals("c") || answer.equals("d");
    }

    @Override
    public int getTotalCount(String fileName) {
        int lineCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] userData = line.split(DELIMITER);
                int found = Integer.parseInt(userData[0]);
                if (found > 0 && found < 101) {
                    lineCount++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user data from file: " + e.getMessage());
            return -1;
        }

        return lineCount;

    }

}
