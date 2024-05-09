
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Client extends User {

    protected String name;
    protected String phoneNumber;
    protected String email;

    private static final String DELIMITER = ",";

    public Client() {
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Client(String username, String password, String name, String email, String phoneNumber) {
        super(username, password);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;

    }

    public Client(String username, String password) {
        super(username, password);
    }

    private static String getValidationErrorMessage(String username, String password, String confirmPassword, String name, char type) {
        if (type == 'A') {
            String errorM = "";
            if (username == null || username.isEmpty()) {
                errorM += "Name is required.\n";
            }
            if (password == null || password.isEmpty()) {
                errorM += "Password is required.\n";
            }
            if (confirmPassword == null || confirmPassword.isEmpty()) {
                errorM += "Confirm password is required.\n";
            }
            if (!password.isEmpty() && !confirmPassword.isEmpty()) {
                if (!password.equals(confirmPassword)) {
                    errorM += "Password and confirm password do not match.\n";
                }
            }
            if (name == null || name.isEmpty()) {
                errorM += "Name is required.\n";
            }
            if (!errorM.isEmpty()) {
                return errorM;
            } else {
                return null;
            }
        } else if (type == 'C') {
            String errorM = "";
            if (name == null || name.isEmpty()) {
                errorM += "Name is required.\n";
            }
            if (password == null || password.isEmpty()) {
                errorM += "Password is required.\n";
            }
//            if (confirmPassword == null || confirmPassword.isEmpty()) {
//                errorM += "Confirm password is required.\n";
//            }
//            if (!password.isEmpty() && !confirmPassword.isEmpty()) {
//                if (!password.equals(confirmPassword)) {
//                    errorM += "Password and confirm password do not match.\n";
//                }
//            }
            if (!errorM.isEmpty()) {
                return errorM;
            } else {
                return null;
            }
        }
        return null;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static Client register(String newUsername, String newPassword, String confirmedPassword, String newName, String newEmail, String newPhoneNumber, String fileName) {
        // Validate input parameters
        String errorMessage = getValidationErrorMessage(newUsername, newPassword, confirmedPassword, newName, 'A');
        if (errorMessage == null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                boolean usernameExists = false;
                while ((line = reader.readLine()) != null) {
                    String[] userData = line.split(DELIMITER);
                    if (userData.length >= 1) {
                        String storedUsername = userData[0];
                        if (newUsername.equals(storedUsername)) {
                            System.err.println("Username already exists.");
                            usernameExists = true;
                            break;
                        }
                    }
                }
                if (!usernameExists) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                        String WriteUserData = String.join(DELIMITER,
                                newUsername, newPassword, newName, newEmail,
                                newPhoneNumber, "false", "0", "0", "0", null);
                        writer.write(WriteUserData);
                        writer.newLine();
                        return new Client(newUsername, newPassword, newName, newPhoneNumber, newEmail); // Registration successful
                    } catch (IOException e) {
                        System.err.println("Error in register: " + e.getMessage());
                        return null; // Registration failed
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading user data from file: " + e.getMessage());
            }
        } else {
            System.err.println("Failed to register: " + errorMessage);
        }
        return null;
    }

    public boolean updateProfile(String username, String newPassword, String newName, String newPhoneNumber, String newEmail, String fileName) {
        // Validate input parameters
        String errorMessage = getValidationErrorMessage(username, newPassword, "",newName, 'C');
        if (errorMessage == null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName)); BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".tmp"))) {

                String line;
                boolean updated = false;
                while ((line = reader.readLine()) != null) {
                    String[] userData = line.split(DELIMITER);
                    if (userData.length >= 1) {
                        String storedUsername = userData[0];
                        String adminFlag = userData[5];
                        String totalEarn = userData[6];

                        String totalRedeem = userData[7];
                        String totalExp = userData[8];
                        String lastTranDate = userData[9];

                        if (username.equals(storedUsername)) {
                            // Construct the updated line
                            String updatedLine = username + DELIMITER + newPassword + DELIMITER
                                    + newName + DELIMITER + newEmail + DELIMITER + newPhoneNumber
                                    + DELIMITER + adminFlag + DELIMITER + totalEarn + DELIMITER + totalRedeem
                                    + DELIMITER + totalExp + DELIMITER + lastTranDate;
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
                    System.err.println("Username not found.");
                    return false;
                }
            } catch (IOException e) {
                System.err.println("Error updating user data: " + e.getMessage());
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

                System.out.println("Account updated.");
                return true;
            } catch (Exception ex) {
                System.err.println("Error replacing files: " + ex.getMessage());
                return false;
            }
        } else {
            System.err.println("Failed to update : " + errorMessage);
            return false;
        }
    }

    public static Client getClient(String username, String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(DELIMITER);
                if (userData[0].equals(username)) {
                    String password = userData[1];
                    String name = userData[2];
                    String phoneNumber = userData[3];
                    String email = userData[4];
                    return new Client(username, password, name, phoneNumber, email);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user data from file: " + e.getMessage());
            return null;
        }
        System.out.println("Error reading user data from file: ");
        return null; // User not found or error occurred

    }

    public boolean updatePassword(String username, String oldPsw, String newPsw, String conPsw, String fileName) {
        if (!newPsw.equals(conPsw)) {
            System.out.println("New password does not match the confirmation password.");
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName)); BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".tmp"))) {

            String line;
            boolean updated = false;

            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(DELIMITER);

                if (userData[0].equals(username) && userData[1].equals(oldPsw)) {
                    // Update password
                    userData[1] = newPsw;
                    updated = true;
                }

                writer.write(String.join(DELIMITER, userData));
                writer.newLine();
            }

            if (!updated) {
                System.err.println("Username or old password is incorrect.");
                return false;
            }

        } catch (IOException e) {
            System.err.println("Error reading or writing user data: " + e.getMessage());
            return false;
        }

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

            System.out.println("Account updated.");
            return true;
        } catch (Exception ex) {
            System.err.println("Error replacing files: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public String toString() {
        return "Username:" + username + "\n"
                + "Password:" + password + "\n"
                + "Name:" + this.name + "\n"
                + "Phone Number:" + this.phoneNumber + "\n"
                + "Email:" + this.email;
    }
}
