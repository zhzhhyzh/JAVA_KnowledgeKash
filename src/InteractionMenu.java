
import java.util.Scanner;
import java.util.InputMismatchException;
import java.lang.NullPointerException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InteractionMenu {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_BOLD = "\u001B[1m";
    public static final String ANSI_RESET_BOLD = "\u001B[21m";
    public static final String DIVIDER = "=====================================================================";

    public static void main(String[] args) {
        boolean checkFileInd = checkFileIndicator();
        boolean[] loggedIn = {false};
        boolean[] adminFlag = {false};
        String[] username = new String[1];
        RewardCatalogue[] rewardCatalogue = new RewardCatalogue[1];
        rewardCatalogue[0] = new RewardCatalogue();

        if (checkFileInd) {
            Scanner scanner = new Scanner(System.in);
            boolean running = true;
            boolean menuError = false;
            boolean errorFlag = false;
            while (running) {
                do {
                    clearScreen();
                    asciiArt();
                    System.out.print("\033[0m");
                    System.out.println(DIVIDER);
                    System.out.println("Welcome to KnowledgeKash");
                    System.out.println(DIVIDER);
                    System.out.println("1. Register");
                    System.out.println("2. Login");
                    System.out.println("0. Exit");
                    System.out.print("Enter your choice: ");

                    do {
                        try {
                            // Get user input
                            int choice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            // Process user choice
                            switch (choice) {
                                case 1:
                                    errorFlag = false;
                                    menuError = register(scanner, username);
                                    break;
                                case 2:
                                    errorFlag = false;

                                    menuError = login(scanner, loggedIn, adminFlag, username);
                                    break;
                                case 0:
                                    errorFlag = false;
                                    System.out.println("Exiting...");
                                    menuError = false;
                                    running = false;

                                    break;
                                default:
                                    errorFlag = true;
                                    System.out.println("Invalid choice. Please try again.");
                            }
                        } catch (InputMismatchException ex) {
                            scanner.next();
                            System.out.println("Invalid input. Please try again.");
                            errorFlag = true;
                        }
                    } while (errorFlag);
                } while (menuError);

                if (running) {

                    do {
                        if (adminFlag[0] && loggedIn[0]) {
                            System.out.println(DIVIDER);
                            System.out.println("KnowledgeKash Admins");
                            System.out.println(DIVIDER);
                            System.out.println("1. Manage Question");
                            System.out.println("2. Manage Rewards");
                            System.out.println("3. Check Transaction History");
                            System.out.println("4. Manage User");
                            System.out.println("0. Logout");
                            System.out.print("Enter your choice: ");
                            do {
                                try {
                                    // Get user input
                                    int choice = scanner.nextInt();
                                    scanner.nextLine(); // Consume newline

                                    // Process user choice
                                    switch (choice) {
                                        case 1:
                                            errorFlag = false;
                                            callManageQuestion(scanner);
                                            clearScreen();
                                            break;
                                        case 2:
                                            errorFlag = false;
                                            clearScreen();
                                            callRewardsCatalogue(rewardCatalogue, scanner);
                                            clearScreen();
                                            break;
                                        case 3:
                                            errorFlag = false;
                                            clearScreen();
                                            callTransactionHistory(scanner);
                                            clearScreen();
                                            break;
                                        case 4:
                                            errorFlag = false;
                                            callCheckUser(scanner);
                                            clearScreen();
                                            break;
                                        case 0:
                                            errorFlag = false;
                                            logout(scanner, loggedIn, adminFlag);
                                            clearScreen();

                                            break;
                                        default:
                                            errorFlag = true;
                                            System.out.println("Invalid choice. Please try again.");
                                    }
                                } catch (InputMismatchException ex) {
                                    scanner.next();
                                    System.out.println("Invalid input. Please try again.");
                                    errorFlag = true;
                                }
                            } while (errorFlag);

                        } else if (loggedIn[0]) {
                            int choice = 0;

                            System.out.println(DIVIDER);
                            System.out.println("KnowledgeKash Menu");
                            System.out.println(DIVIDER);
                            System.out.println("1. Get Points! -- Answer Question");
                            System.out.println("2. Spend My Points! -- Redeem Rewards");
                            System.out.println("3. Profile");
                            System.out.println("0. Logout");
                            System.out.print("Enter your choice: ");
                            do {
                                try {
                                    // Get user input
                                    choice = scanner.nextInt();
                                    scanner.nextLine(); // Consume newline

                                    // Process user choice
                                    switch (choice) {
                                        case 1:
                                            errorFlag = false;
                                            clearScreen();
                                            callAnswerQuestion(scanner, username);

                                            break;
                                        case 2:
                                            errorFlag = false;
                                            clearScreen();
                                            callRedeemRewards(rewardCatalogue, scanner, username);

                                            break;
                                        case 3:
                                            errorFlag = false;
                                            clearScreen();
                                            callProfile(username, scanner);
                                            clearScreen();
                                            break;
                                        case 0:
                                            errorFlag = false;
                                            logout(scanner, loggedIn, adminFlag);
                                            clearScreen();

                                            break;
                                        default:
                                            errorFlag = true;
                                            System.out.println("Invalid choice. Please try again.");
                                    }
                                } catch (InputMismatchException ex) {
                                    scanner.next();
                                    System.out.println("Invalid input. Please try again.");
                                    errorFlag = true;
                                }
                            } while (errorFlag);

                        }
                    } while (loggedIn[0]);
                }
            }

            scanner.close();

        } else {
            System.err.println("Unexpected Error: Check File failed. \nPlease Contact Administrator.");
        }
    }

    private static boolean login(Scanner scanner, boolean[] loggedIn, boolean[] adminFlag, String[] PassINusername) {
        System.out.println(DIVIDER);
        System.out.println("Login");
        System.out.println(DIVIDER);

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        String tempSave = Client.login(username, password);

        // Check if login was successful
        if (tempSave != null) {
            if (username.equals("admin1") || username.equals("admin2") || username.equals("admin3")) {
                adminFlag[0] = true;
            } else {
                PassINusername[0] = tempSave;
            }

            loggedIn[0] = true;
            clearScreen();
            System.out.println("Login successful! Welcome, " + tempSave + "!");

            Policy.getDayCount();
            Policy.applyPolicy(username);
            return false;
        } else {
            clearScreen();
            System.out.println("Login failed. Invalid username or password.");
            return true;
        }
    }

    public static boolean register(Scanner scanner, String[] PassINusername) {
        System.out.println(DIVIDER);
        System.out.println("Register");
        System.out.println(DIVIDER);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        System.out.print("Enter again to confirm your password: ");
        String confirmPassword = scanner.nextLine();
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email(optional): ");
        String email = scanner.nextLine();
        if (email.isEmpty()) {
            email = null;
        }
        System.out.print("Enter your Phone Number(optional): ");
        String phoneNumber = scanner.nextLine();
        if (phoneNumber.isEmpty()) {
            phoneNumber = null;
        }
        clearScreen();
        Client registeredClient = Client.register(username, password, confirmPassword, name, phoneNumber, email);
        if (registeredClient != null) {

            System.out.println("Registration successful!");
            return false;
        } else {
            return true;
        }
    }

    public static void logout(Scanner scanner, boolean[] loggedIn, boolean[] adminFlag) {
        try {
            System.out.println(DIVIDER);
            System.out.println("Are you sure to proceed logout?");

            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    loggedIn[0] = false; // Set loggedIn to false
                    adminFlag[0] = false;
                    System.out.println("Logged out successfully.");
                    clearScreen();
                    break;
                case 2:
                    loggedIn[0] = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    // Recursively call logout method to allow the user to input a valid choice
                    logout(scanner, loggedIn, adminFlag);
                    break;
            }
        } catch (InputMismatchException e) {
            // Clear the invalid input
            scanner.next();
            System.out.println("Invalid input. Please enter a number.");
            // Recursively call logout method to allow the user to input a valid choice
            logout(scanner, loggedIn, adminFlag);
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void callRewardsCatalogue(RewardCatalogue[] rewardCatalogue, Scanner scanner) {
        boolean errorFlag = false;
        boolean running = true;
        do {
            clearScreen();
            System.out.println(DIVIDER);
            System.out.println("KnowledgeKash Admin > Manage Rewards");
            System.out.println(DIVIDER);
            rewardCatalogue[0].listProducts();
            System.out.println("1. Add Rewards");
            System.out.println("2. Update Rewards");
            System.out.println("3. Delete Rewards");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");
            do {
                try {
                    // Get user input
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    // Process user choice
                    switch (choice) {
                        case 1:
                            errorFlag = false;
                            System.out.println("Are you sure to add product?");
                            System.out.println("Type [1] is yes");
                            String responseGetter = scanner.nextLine();
                            if (responseGetter.equals("1")) {
                                manageRewards('A', scanner);
                            }
                            break;
                        case 2:
                            errorFlag = false;
                            manageRewards('C', scanner);
                            break;
                        case 3:
                            errorFlag = false;
                            manageRewards('D', scanner);
                            break;
                        case 0:
                            running = false;
                            errorFlag = false;
                            break;
                        default:
                            errorFlag = true;
                            System.out.println("Invalid choice. Please try again.");
                            System.out.print("Enter your choice:");
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Invalid input. Please try again.");
                    scanner.nextLine();
                    System.out.print("Enter your choice:");
                    errorFlag = true;
                }
            } while (errorFlag);
        } while (running);

    }

    public static void manageRewards(char actType, Scanner scanner) {
        boolean errorFlag = false;

        if (actType == 'A') {
            System.out.println(DIVIDER);
            System.out.println("KnowledgeKash Admin > Manage Rewards > Add Product");
            System.out.println(DIVIDER);
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Description: ");
            String description = scanner.nextLine();
            int point = 0;
            int stock = 0;
            do {
                try {

                    System.out.print("Point Cost: ");
                    point = scanner.nextInt();
                    errorFlag = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Invalid Input. Please Enter again:");
                    scanner.nextLine();
                    errorFlag = true;
                }
            } while (errorFlag);
            do {
                try {

                    System.out.print("Stock: ");
                    stock = scanner.nextInt();
                    errorFlag = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Invalid Input. Please Enter again:");
                    scanner.nextLine();
                    errorFlag = true;

                }
            } while (errorFlag);

            RewardCatalogue.addProduct(name, description, point, stock);

        } else if (actType == 'C') {
            System.out.println(DIVIDER);
            System.out.println("KnowledgeKash Admin > Manage Rewards > Update Product");
            System.out.println(DIVIDER);
            System.out.println("Please enter Product ID or [0] for Back:");
            int rewardId = scanner.nextInt();
            String showInfo = RewardCatalogue.viewProduct(rewardId);
            if (showInfo != null) {
                scanner.nextLine();//Clear input
                System.out.println(showInfo);
                System.out.println(DIVIDER);
                System.out.println("*If data remain unchange then click \"Enter\"");
                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Description: ");
                String description = scanner.nextLine();
                String point;
                String stock;

                do {
                    System.out.print("Point Cost: ");
                    point = scanner.nextLine();
                } while (!point.isEmpty() && !isValidInteger(point));

                do {
                    System.out.print("Stock: ");
                    stock = scanner.nextLine();
                } while (!stock.isEmpty() && !isValidInteger(stock));
                name = name.isEmpty() ? RewardCatalogue.getTempName() : name;
                description = description.isEmpty() ? RewardCatalogue.getTempDescription() : description;
                int pointCost = point.isEmpty() ? RewardCatalogue.getTempPoint() : Integer.parseInt(point);
                int stockAmount = stock.isEmpty() ? RewardCatalogue.getTempStock() : Integer.parseInt(stock);
                RewardCatalogue.updateProduct(rewardId, name, description, pointCost, stockAmount);
            }

        } else {
            System.out.println(DIVIDER);
            System.out.println("KnowledgeKash Admin > Manage Rewards > Delete Product");
            System.out.println(DIVIDER);
            System.out.println("Please Enter Product ID:");
            int rewardId = scanner.nextInt();
            String showInfo = RewardCatalogue.viewProduct(rewardId);
            if (showInfo != null) {
                scanner.nextLine(); //Clear input
                System.out.println(DIVIDER);
                System.out.println("Are you sure to delete?");
                System.out.println("Type [1] is yes");
                String responseGetter = scanner.nextLine();
                if (responseGetter.equals("1")) {
                    RewardCatalogue.deleteProduct(rewardId);
                }
            }
        }
    }

    private static boolean isValidInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            System.out.print(e);
            return false;
        }
    }

    public static void callCheckUser(Scanner scanner) {
        System.out.println(DIVIDER);
        System.out.println("KnowledgeKash Admin > Check User");
        System.out.println(DIVIDER);
        int page = 1;
        int lastRecord;
        boolean errorFlag = false;
        while (page >= 1) {
            System.out.println("Page: " + page);
            lastRecord = Admin.listUsers(page);
            System.out.println("1. Next Page");
            System.out.print(page == 1 ? "" : "2. Previous Page\n");
            System.out.print(page == 1 ? "2. Update Policy\n" : "3. Update Policy\n");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");
            do {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        int count = ((page + 1) - 1) * 20 + 4;

                        if (count >= lastRecord) {
                            System.out.println("This is the last page.");
                            errorFlag = true;
                            System.out.print("Enter your choice again: ");
                        } else {
                            errorFlag = false;
                            page++;
                            clearScreen();

                        }
                        break;
                    case 2:
                        if (page > 1) {
                            page--;
                            clearScreen();

                            errorFlag = false;
                        } else {
                            managePolicy(scanner);
                        }
                        break;
                    case 3:
                        if (page > 1) {
                            managePolicy(scanner);
                            errorFlag = false;
                        } else {
                            errorFlag = true;
                        }
                    case 0:
                        errorFlag = false;
                        page = 0;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        errorFlag = true;
                        break;
                }
            } while (errorFlag);
        }
    }

    public static void callRedeemRewards(RewardCatalogue[] rewardCatalogue, Scanner scanner, String[] username) {
        boolean errorFlag = false;
        int choices = 0;

        do {
            PointManagement pm = new PointManagement();
            pm.getClient(username[0]);
            int identifier = pm.getAvailablePoints();
            System.out.println(DIVIDER);
            System.out.println("KnowledgeKash Menu > Redeem Rewards");
            System.out.println(DIVIDER);
            rewardCatalogue[0].listProducts();

            System.out.println("Current available points: " + identifier + "point(s)");
            System.out.print("Enter Product ID (Enter[0] to Back):");

            do {
                try {
                    choices = scanner.nextInt();
                    errorFlag = false;
                    if (choices < 0) {
                        System.out.println("Please enter valid input:");
                        System.out.print("Enter Product ID (Enter[0] to Back):");
                        errorFlag = true;
                    } else if (choices > 0 && choices < 1001) {
                        System.out.println("Please enter valid Product ID(Exp: 1001):");
                        System.out.print("Enter Product ID (Enter[0] to Back):");
                        errorFlag = true;
                    }

                } catch (InputMismatchException ex) {
                    System.out.println("Please enter valid input:");
                    System.out.print("Enter Product Id (Enter[0] to Back):");
                    scanner.nextLine();
                    errorFlag = true;
                }

            } while (errorFlag);

            if (choices != 0) {
                System.out.println(DIVIDER);
                String showInfo = rewardCatalogue[0].viewProduct(choices);
                if (showInfo != null) {
                    System.out.println("Are you sure to redeem?");
                    System.out.println("Type [1] is yes");
                    scanner.nextLine(); // Clear input
                    String responseGetter = scanner.nextLine();
                    if (responseGetter.equals("1")) {
                        int tempInt = rewardCatalogue[0].getTempPoint();
                        if (identifier < tempInt) {
                            clearScreen();
                            System.out.println("Point not enough to proceed.");
                        } else {
                            clearScreen();
                            RewardRedemption.redeemProduct(choices);
                            pm.decreasePoints(tempInt);
                            TransactionHistory th = new TransactionHistory(username[0], 'R', tempInt);
                            th.writeTransactionToFile();
                            pm.getClient(username[0]);
                            System.out.println("Current available point(s): " + pm.getAvailablePoints());
                        }
                    }
                }
            }
        } while (choices > 0);
    }

    public static void callProfile(String[] username, Scanner scanner) {
        int choice = 0;
        do {
            System.out.println(DIVIDER);
            System.out.println("KnowledgeKash Menu > Profile");
            System.out.println(DIVIDER);
            PointManagement pm = new PointManagement();
            pm.getClient(username[0]);
            System.out.println(pm.toString());
            Policy.showExpired(username[0]);
            System.out.println("1. View/Update Profile");
            System.out.println("2. View Transaction");
            System.out.println("0. Back");
            System.out.print("Enter your choice:");

            boolean errorFlag = false;

            do {
                try {
                    choice = scanner.nextInt();
                    errorFlag = false;
                    if (choice != 1 && choice != 0 && choice != 2) {
                        errorFlag = true;
                        System.out.println("Invalid choice. Please enter again (1/0):");
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Invalid Input. Please enter again (1/0):");
                    scanner.nextLine();
                }
            } while (errorFlag);

            if (choice == 1) {
                callManageProfile(scanner, username);
                choice = 1;
            } else if (choice == 2) {
                clearScreen();
                System.out.println(DIVIDER);
                System.out.println("KnowledgeKash Menu > Profile > View Transaction History");
                System.out.println(DIVIDER);
                int viewPage = 1;
                int viewLastRecord = TransactionHistory.findTransactionByUsername(username[0], viewPage);
                System.out.println("1. Next Page");
                System.out.print(viewPage == 1 ? "2. Generate Report\n" : "2.Previous page\n");
                System.out.print(viewPage == 1 ? "0. Back\n" : "3. Generate Report\n");
                System.out.print(viewPage == 1 ? "" : "0. Back\n");
                System.out.print("Enter your choice: ");

                do {
                    try {
                        choice = scanner.nextInt();
                        switch (choice) {
                            case 1:
                                int count = (viewPage) * 20 + 1;

                                if (count > viewLastRecord) {
                                    System.out.println("This is the last page.");
                                    System.out.print("Enter your choice again: ");
                                } else {
                                    viewPage++;
                                    clearScreen();
                                }
                                break;
                            case 2:
                                if (viewPage > 1) {
                                    viewPage--;
                                    clearScreen();
                                } else {
                                    Report.findTransactionByUsername(username[0]);
                                    System.out.print("Enter your choice: ");
                                }
                                break;
                            case 3:
                                if (viewPage > 1) {
                                    Report.findTransactionByUsername(username[0]);
                                    System.out.print("Enter your choice: ");

                                } else {
                                    System.out.println("Invalid choice. Please try again.");
                                }
                                break;

                            case 0:
                                viewPage = 0;
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                                break;
                        }
                    } catch (InputMismatchException ex) {
                        System.out.println("Please enter valid input:");
                        scanner.nextLine();

                    }
                } while (viewPage > 0);
                choice = 2;
            }
        } while (choice > 0);
    }

    public static void callManageProfile(Scanner scanner, String[] username) {
        int choice = 1;
        boolean errorFlag = false;
        while (choice == 1 || choice == 2) {
            System.out.println(DIVIDER);
            System.out.println("KnowledgeKash Menu > Profile > Manage Profile");
            System.out.println(DIVIDER);
            Client client = Client.getClient(username[0]);
            System.out.println(client.toString());

//            try{
//            }catch (NullPointerException ex){
//                System.out.println("Error here" + ex.getMessage());
//            }
            System.out.println("1. Update Profile");
            System.out.println("2. Update Password");

            System.out.println("0. Back");
            System.out.println("Enter your choice:");

            do {
                try {
                    choice = scanner.nextInt();
                    errorFlag = false;
                    if (choice != 1 && choice != 0 && choice != 2) {
                        errorFlag = true;
                        System.out.println("Invalid choice. Please enter again (1/0):");
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Invalid Input. Please enter again (1/0):");
                    scanner.nextLine();
                }
            } while (errorFlag);

            if (choice == 1) {
                scanner.nextLine();
                System.out.println(DIVIDER);
                System.out.println("*If data remain unchange then click \"Enter\"");
                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Phone Number: ");
                String phoneNumber = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();

                name = name.isEmpty() ? client.getName() : name;
                phoneNumber = phoneNumber.isEmpty() ? client.getPhoneNumber() : phoneNumber;
                email = email.isEmpty() ? client.getEmail() : email;
                String password = client.getPassword();
                clearScreen();
                client.updateProfile(username[0], password, name, phoneNumber, email);

            } else if (choice == 2) {
                scanner.nextLine();
                System.out.println(DIVIDER);
                System.out.print("Enter old password: ");
                String oldPsw = scanner.nextLine();
                System.out.print("Enter new password: ");
                String newPsw = scanner.nextLine();
                System.out.print("Enter again to confirm your password: ");
                String conPsw = scanner.nextLine();
                clearScreen();
                client.updatePassword(username[0], oldPsw, newPsw, conPsw);

            }
        }
    }

    public static boolean checkFileIndicator() {
        //Check and create file
        CheckFile checkfile = new CheckFile();
        return checkfile.checkUserFile()
                && checkfile.checkTransactionFile()
                && checkfile.checkProdFile()
                && checkfile.checkQuesFile();
    }

    public static void asciiArt() {
        System.out.println(ANSI_RED + "  _  __ _   _   ____ __          __ _       ______  _____    _____  ______  _  __            _____  _    _ ");
        System.out.println(ANSI_GREEN + " | |/ /| \\ | | / __ \\\\ \\        / /| |     |  ____||  __ \\  / ____||  ____|| |/ /    /\\     / ____|| |  | |");
        System.out.println(ANSI_YELLOW + " | ' / |  \\| || |  | |\\ \\  /\\  / / | |     | |__   | |  | || |  __ | |__   | ' /    /  \\   | (___  | |__| |");
        System.out.println(ANSI_BLUE + " |  <  | . ` || |  | | \\ \\/  \\/ /  | |     |  __|  | |  | || | |_ ||  __|  |  <    / /\\ \\   \\___ \\ |  __  |");
        System.out.println(ANSI_PURPLE + " | . \\ | |\\  || |__| |  \\  /\\  /   | |____ | |____ | |__| || |__| || |____ | . \\  / ____ \\  ____) || |  | |");
        System.out.println(ANSI_CYAN + " |_|\\_\\|_| \\_| \\____/    \\/  \\/    |______||______||_____/  \\_____||______||_|\\_\\/_/    \\_\\|_____/ |_|  |_|" + ANSI_RESET);
        System.out.println(ANSI_BOLD + "               KNOW-IT!" + ANSI_RESET_BOLD);
    }

    public static void callManageQuestion(Scanner scanner) {
        QuestionRepository qr = new QuestionRepository();
        int page = 1;
        clearScreen();

        System.out.println("\n".repeat(100)); // Print 50 newlines

        System.out.println(DIVIDER);

        System.out.println("KnowledgeKash Admin > Manage Question");
        System.out.println(DIVIDER);

        int lastRecord;
        boolean errorFlag = false;
        while (page >= 1) {
            System.out.println("Page: " + page);
            lastRecord = qr.listQuestion(page);
            System.out.println("1. Next Page");
            System.out.print(page == 1 ? "" : "2. Previous Page\n");
            System.out.print(page == 1 ? "2. Create Question\n" : "3. Create Question\n");
            System.out.print(page == 1 ? "3. Update Question\n" : "4. Update Question\n");
            System.out.print(page == 1 ? "4. Delete Question\n" : "5. Delete Question\n");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");
            do {
                try {
                    int choice = scanner.nextInt();
                    switch (choice) {
                        case 1:
                            int count = (page) * 3 + 1;
//                            System.err.println(count + ">>" + lastRecord);
                            if (count > lastRecord) {
                                System.out.println("This is the last page.");
                                errorFlag = true;
                                System.out.print("Enter your choice again: ");
                            } else {
                                errorFlag = false;
                                page++;
                                clearScreen();

                                System.out.println("\n".repeat(100)); // Print 50 newlines
                                System.out.println(DIVIDER);

                            }

                            break;
                        case 2:
                            if (page > 1) {
                                page--;
                                errorFlag = false;
                                clearScreen();

                                System.out.println("\n".repeat(100)); // Print 50 newlines
                                System.out.println(DIVIDER);
                            } else {
                                clearScreen();
                                manageQuestion('A', scanner);
                                errorFlag = false;

                            }

                            break;
                        case 3:

                            if (page > 1) {
                                clearScreen();
                                manageQuestion('A', scanner);
                                errorFlag = false;
                            } else {
                                clearScreen();
                                manageQuestion('C', scanner);
                                errorFlag = false;
                            }

                            break;
                        case 4:

                            if (page > 1) {
                                clearScreen();
                                manageQuestion('C', scanner);
                                errorFlag = false;
                            } else {
                                clearScreen();
                                manageQuestion('D', scanner);
                                errorFlag = false;
                            }

                            break;
                        case 5:
                            if (page > 1) {
                                clearScreen();
                                manageQuestion('D', scanner);
                                errorFlag = false;
                            } else {
                                System.out.println("Invalid choice. Please try again.");
                                errorFlag = true;
                            }

                            break;
                        case 0:
                            errorFlag = false;
                            page = 0;
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            errorFlag = true;
                            break;
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Please enter valid input:");
                    scanner.nextLine();
                    errorFlag = true;
                }
            } while (errorFlag);
        }
    }

    public static void manageQuestion(char actType, Scanner scanner) {
        boolean errorFlag = false;
        if (actType == 'A') {
            System.out.println(DIVIDER);
            System.out.println("KnowledgeKash Admin > Manage Question > Add Question");
            System.out.println(DIVIDER);
            System.out.println("1. Question answered by Selection");
            System.out.println("2. Question answered by Boolean");
            System.out.println("3. Question answered by Sentence");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");
            do {
                try {
                    int choice = scanner.nextInt();
                    switch (choice) {
                        case 1:
                            QuestionSelect qs = new QuestionSelect();
                            qs.createQuestion();
                            errorFlag = false;
                            break;
                        case 2:
                            QuestionBoolean qb = new QuestionBoolean();
                            qb.createQuestion();
                            errorFlag = false;
                            break;
                        case 3:
                            QuestionString qStr = new QuestionString();
                            qStr.createQuestion();
                            errorFlag = false;
                            break;
                        case 0:
                            errorFlag = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            errorFlag = true;
                            break;
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Please enter valid input:");
                    scanner.nextLine();
                    errorFlag = true;
                }
            } while (errorFlag);

        } else if (actType == 'C') {
            System.out.println(DIVIDER);
            System.out.println("KnowledgeKash Admin > Manage Question > Update Question");
            System.out.println(DIVIDER);
            int choice = 0;
            do {
                System.out.print("Enter Question Id (1-300) OR Type [0] for \"Back\": ");

                try {
                    choice = scanner.nextInt();
                    if (choice >= 0 && choice < 301) {
                        errorFlag = false;
                    } else {
                        errorFlag = true;
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Please enter valid input:");
                    scanner.nextLine();
                    errorFlag = true;
                }
            } while (errorFlag);
            if (choice > 0 && choice < 101) {
                QuestionSelect qs = new QuestionSelect();
                qs.updateQuestion(choice);
            } else if (choice > 100 && choice < 201) {
                QuestionBoolean qb = new QuestionBoolean();
                qb.updateQuestion(choice);
            } else if (choice > 200 && choice < 301) {
                QuestionString qStr = new QuestionString();
                qStr.updateQuestion(choice);
            }
        } else {
            System.out.println(DIVIDER);
            System.out.println("KnowledgeKash Admin > Manage Question > Delete Question");
            System.out.println(DIVIDER);
            int choice = 0;
            do {
                System.out.print("Enter Question Id (1-300) OR Type [0] for \"Back\": ");

                try {
                    choice = scanner.nextInt();
                    if (choice >= 0 && choice < 301) {
                        errorFlag = false;
                    } else {
                        errorFlag = true;
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Please enter valid input:");
                    scanner.nextLine();
                    errorFlag = true;
                }
            } while (errorFlag);
            if (choice != 0) {
                QuestionRepository qr = new QuestionRepository();
                qr.viewQuestion(choice);
                System.out.println("Are you sure to delete?");

                System.out.println("Type [1] to confirm");
                scanner.nextLine();
                String confirmKey = scanner.nextLine();
                if (confirmKey.equals("1")) {
                    qr.deleteQuestion(choice);
                }
            }
        }
    }

    public static void callAnswerQuestion(Scanner scanner, String[] username) {
        int choice = 0;
        PointManagement pm = new PointManagement();
        pm.getClient(username[0]);
        boolean errorFlag = false;
        do {
            System.out.println(DIVIDER);
            System.out.println("KnowledgeKash Menu > Answer Question");
            System.out.println(DIVIDER);
            System.out.println("1. Question's answer by ABCD");
            System.out.println("2. Question's answer by True/False");
            System.out.println("3. Question's answer by Sentences");
            System.out.println("0. Back");

            System.out.print("Choose your selection: ");
            do {
                try {
                    choice = scanner.nextInt();
                    switch (choice) {
                        case 1:
                            QuestionSelect qs = new QuestionSelect();
                            int questionCountS = qs.getTotalCount();
                            questionCountS = (int) (Math.random() * questionCountS + 1);
                            int qSPoint = qs.answerQuestion(questionCountS);
                            System.out.println("Points earned: " + qSPoint);
                            pm.increasePoints(qSPoint);
                            TransactionHistory th1 = new TransactionHistory(username[0], 'E', qSPoint);
                            th1.writeTransactionToFile();
                            errorFlag = false;
                            break;
                        case 2:
                            QuestionBoolean qb = new QuestionBoolean();
                            int questionCountBoo = qb.getTotalCount();
                            questionCountBoo = (int) (Math.random() * questionCountBoo + 101);
                            int qBPoint = qb.answerQuestion(questionCountBoo);
                            System.out.println("Points earned: " + qBPoint);

                            pm.increasePoints(qBPoint);
                            TransactionHistory th2 = new TransactionHistory(username[0], 'E', qBPoint);
                            th2.writeTransactionToFile();
                            errorFlag = false;
                            break;
                        case 3:
                            QuestionString qStr = new QuestionString();
                            int questionCountStr = qStr.getTotalCount();
                            questionCountStr = (int) (Math.random() * questionCountStr + 201);
                            int qStrPoint = qStr.answerQuestion(questionCountStr);
                            System.out.println("Points earned: " + qStrPoint);

                            pm.increasePoints(qStrPoint);
                            TransactionHistory th3 = new TransactionHistory(username[0], 'E', qStrPoint);
                            th3.writeTransactionToFile();
                            errorFlag = false;
                            break;
                        case 0:
                            errorFlag = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            errorFlag = true;
                            break;
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Please enter valid input:");
                    scanner.nextLine();
                    errorFlag = true;
                }
            } while (errorFlag);
        } while (choice > 0);
    }

    public static void managePolicy(Scanner scanner) {
        System.out.println("Current Policy Day Count:" + Policy.getDayCount());
        System.out.print("Enter numbers of day want to change OR [0] for cancel:");
        int policyChoice = 0;
        boolean policyError = false;
        do {
            try {
                policyChoice = scanner.nextInt();
                if (policyChoice < 0) {
                    policyError = true;
                } else {
                    policyError = false;
                }
            } catch (InputMismatchException ex) {
                policyError = true;
                System.out.println("Invalid input. Please enter again:");
            }
        } while (policyError);

        if (policyChoice > 0) {
            Policy.setDayCount(policyChoice);
            clearScreen();
            System.out.println("Policy updated successful.");
        }
    }

    public static void callTransactionHistory(Scanner scanner) {
        boolean errorFlag = false;
        int choice = 0;
        do {
            System.out.println(DIVIDER);
            System.out.println("KnowledgeKash Admins > Transaction History");
            System.out.println(DIVIDER);
            int page = 1;
            System.out.println("Page: " + page);
            int lastRecord = TransactionHistory.listTransaction(page);
            System.out.println("1. Next Page");
            System.out.print(page == 1 ? "2. Find transaction by username\n" : "2.Previous page\n");
            System.out.print(page == 1 ? "3. List by date\n" : "3. Find transaction by username\n");
            System.out.print(page == 1 ? "4. List by transaction type\n" : "4. List by date\n");
            System.out.print(page == 1 ? "5. List by transaction type and date\n" : "5. List by transaction type");
            System.out.print(page == 1 ? "" : "6. List by transaction type and date\n");
            System.out.println("0. Back");
            System.out.println("Enter your choice:");

            do {
                try {
                    choice = scanner.nextInt();
                    switch (choice) {
                        case 1:
                            int count = (page) * 20 + 1;
//                            System.err.println(count + ">>" + lastRecord);
                            if (count > lastRecord) {
                                System.out.println("This is the last page.");
                                errorFlag = true;
                                System.out.print("Enter your choice again: ");
                            } else {
                                errorFlag = false;
                                page++;
                                clearScreen();

                            }
                            break;
                        case 2:
                            if (page > 1) {
                                page--;
                                errorFlag = false;
                                clearScreen();
                            } else {
                                clearScreen();
                                callListTransactionInFilter('V');
                                clearScreen();

                                errorFlag = false;
                            }
                            break;
                        case 3:
                            if (page > 1) {
                                clearScreen();
                                callListTransactionInFilter('V');
                                clearScreen();

                                errorFlag = false;
                            } else {
                                clearScreen();
                                callListTransactionInFilter('D');
                                clearScreen();

                                errorFlag = false;
                            }
                            break;
                        case 4:
                            if (page > 1) {
                                clearScreen();
                                callListTransactionInFilter('D');
                                clearScreen();

                                errorFlag = false;
                            } else {
                                clearScreen();

                                callListTransactionInFilter('T');
                                clearScreen();

                                errorFlag = false;
                            }
                            break;
                        case 5:
                            if (page > 1) {
                                clearScreen();

                                callListTransactionInFilter('T');
                                clearScreen();

                                errorFlag = false;
                            } else {
                                clearScreen();

                                callListTransactionInFilter('B');
                                clearScreen();

                                errorFlag = false;
                            }
                            break;
                        case 6:
                            if (page > 1) {
                                clearScreen();
                                callListTransactionInFilter('B');
                                clearScreen();

                                errorFlag = false;
                            } else {
                                System.out.println("Invalid choice. Please try again.");
                                System.out.print("Enter your choice:");
                                errorFlag = true;
                            }
                        case 0:
                            errorFlag = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            System.out.print("Enter your choice:");
                            errorFlag = true;
                            break;
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Invalid choice. Please try again.");
                    System.out.print("Enter your choice:");
                    scanner.nextLine();
                    errorFlag = true;
                }
            } while (errorFlag);

        } while (choice > 0);

    }

    public static void callListTransactionInFilter(char listType) {
        Scanner scanner = new Scanner(System.in);
        String type;
        char convertedType = 'E';
        int datePage = 1;
        boolean isValidFormat = false;
        LocalDate startDate = null;
        LocalDate endDate = null;
        boolean errorFlag = false;
        switch (listType) {
            case 'T':
                System.out.println(DIVIDER);
                System.out.println("KnowledgeKash Admins > Transaction History > List by transaction type");
                System.out.println(DIVIDER);
                int typePage = 1;
                System.out.print("Enter type[E/R/P] OR [0] for back: ");
                do {
                    type = scanner.nextLine();
                    type = type.toUpperCase();
                    if (type.equals("E") || type.equals("R") || type.equals("P") || type.equals("0")) {
                        if (!type.equals("0")) {
                            convertedType = type.charAt(0);
                        }
                        errorFlag = false;
                    } else {
                        errorFlag = true;
                        System.out.println("Please enter valid input.");
                        System.out.print("Enter type[E/R/P] OR [0] for back: ");
                    }
                } while (errorFlag);
                while (typePage > 0) {
                    System.out.println("Page: " + typePage);
                    int TypeLastRecord = TransactionHistory.listTransactionByType(convertedType, typePage);
                    System.out.println("1. Next Page");
                    System.out.print(typePage == 1 ? "0. Back\n" : "2.Previous page\n");
                    System.out.print("Enter your choice: ");

                    do {
                        try {
                            int choice = scanner.nextInt();
                            switch (choice) {
                                case 1:
                                    int count = (typePage) * 20 + 1;

                                    if (count > TypeLastRecord) {
                                        System.out.println("This is the last page.");
                                        errorFlag = true;
                                        System.out.print("Enter your choice again: ");
                                    } else {
                                        errorFlag = false;
                                        typePage++;
                                        clearScreen();

                                    }
                                    break;
                                case 2:
                                    if (typePage > 1) {
                                        typePage--;
                                        errorFlag = false;
                                        clearScreen();

                                    } else {
                                        System.out.println("Invalid choice. Please try again.");
                                        errorFlag = true;
                                    }
                                    break;

                                case 0:
                                    typePage = 0;
                                    errorFlag = false;
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                                    errorFlag = true;
                                    break;
                            }
                        } catch (InputMismatchException ex) {
                            System.out.println("Please enter valid input:");
                            scanner.nextLine();
                            errorFlag = true;
                        }
                    } while (errorFlag);
                }
                break;
            case 'B':
                System.out.println(DIVIDER);
                System.out.println("KnowledgeKash Admins > Transaction History > List by transaction type and date");
                System.out.println(DIVIDER);
                System.out.print("Enter type[E/R/P] OR [0] for back: ");
                do {
                    type = scanner.nextLine();
                    type = type.toUpperCase();
                    if (type.equals("E") || type.equals("R") || type.equals("P") || type.equals("0")) {
                        if (!type.equals("0")) {
                            convertedType = type.charAt(0);
                        } else {
                            return;
                        }
                        errorFlag = false;
                    } else {
                        errorFlag = true;
                        System.out.println("Please enter valid input.");
                        System.out.print("Enter type[E/R/P] OR [0] for back: ");
                    }
                } while (errorFlag);
                int dateTypePage = 1;
                isValidFormat = false;

                while (!isValidFormat) {
                    System.out.print("Enter start date (yyyy-MM-dd) OR [0] for back: ");
                    String startDateStr = scanner.nextLine();

                    if (startDateStr.equals("0")) {
                        return;
                    }

                    try {
                        startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        isValidFormat = true;
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
                        isValidFormat = false;
                        continue;
                    }

                    System.out.print("Enter end date (yyyy-MM-dd): ");
                    String endDateStr = scanner.nextLine();

                    try {
                        endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        isValidFormat = true;
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
                        isValidFormat = false;
                        continue;
                    }

                    if (startDate.isAfter(endDate)) {
                        System.out.println("Start date cannot be after end date. Please enter valid dates.");
                        isValidFormat = false;
                        continue;
                    }
                }
                while (dateTypePage > 0) {
                    System.out.println("Page: " + dateTypePage);
                    int TypeDateLastRecord = TransactionHistory.listTransactionByTypeAndDate(convertedType, startDate, endDate, dateTypePage);
                    System.out.println("1. Next Page");
                    System.out.print(dateTypePage == 1 ? "0. Back\n" : "2.Previous page\n");
                    System.out.print("Enter your choice: ");

                    do {
                        try {
                            int choice = scanner.nextInt();
                            switch (choice) {
                                case 1:
                                    int count = (dateTypePage) * 20 + 1;

                                    if (count > TypeDateLastRecord) {
                                        System.out.println("This is the last page.");
                                        errorFlag = true;
                                        System.out.print("Enter your choice again: ");
                                    } else {
                                        errorFlag = false;
                                        dateTypePage++;
                                        clearScreen();

                                    }
                                    break;
                                case 2:
                                    if (dateTypePage > 1) {
                                        dateTypePage--;
                                        clearScreen();

                                        errorFlag = false;
                                    } else {
                                        System.out.println("Invalid choice. Please try again.");
                                        errorFlag = true;
                                    }
                                    break;

                                case 0:
                                    dateTypePage = 0;
                                    errorFlag = false;
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                                    errorFlag = true;
                                    break;
                            }
                        } catch (InputMismatchException ex) {
                            System.out.println("Please enter valid input:");
                            scanner.nextLine();
                            errorFlag = true;
                        }
                    } while (errorFlag);
                }
                break;
            case 'D':
                System.out.println(DIVIDER);
                System.out.println("KnowledgeKash Admins > Transaction History > List by date");
                System.out.println(DIVIDER);
                isValidFormat = false;
                while (!isValidFormat) {
                    System.out.print("Enter start date (yyyy-MM-dd) OR [0] for back: ");
                    String startDateStr = scanner.nextLine();

                    if (startDateStr.equals("0")) {
                        // Handle back option
                        System.out.println("Back option selected.");
                        return;
                    }

                    try {
                        startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        isValidFormat = true;
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
                        isValidFormat = false;
                        continue;
                    }

                    System.out.print("Enter end date (yyyy-MM-dd): ");
                    String endDateStr = scanner.nextLine();

                    try {
                        endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        isValidFormat = true;
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
                        isValidFormat = false;
                        continue;
                    }

                    if (startDate.isAfter(endDate)) {
                        System.out.println("Start date cannot be after end date. Please enter valid dates.");
                        isValidFormat = false;
                        continue;
                    }
                }
                while (datePage > 0) {
                    System.out.println("Page: " + datePage);
                    int[] tempReturn = TransactionHistory.listTransactionByDate(startDate, endDate, datePage);
                    int TypeLastRecord = tempReturn[0];
                    int tempEarn = tempReturn[1];
                    int tempRedeem = tempReturn[2];
                    int tempExpired = tempReturn[3];

                    System.out.println("1. Next Page");
                    System.out.print(datePage == 1 ? "2. Generate Report\n" : "2.Previous page\n");
                    System.out.print(datePage == 1 ? "0. Back\n" : "3. Generate Report\n");
                    System.out.print(datePage == 1 ? "" : "0. Back\n");
                    System.out.print("Enter your choice: ");
                    do {
                        try {
                            int choice = scanner.nextInt();
                            switch (choice) {
                                case 1:
                                    int count = (datePage) * 20 + 1;

                                    if (count > TypeLastRecord) {
                                        System.out.println("This is the last page.");
                                        errorFlag = true;
                                        System.out.print("Enter your choice again: ");
                                    } else {
                                        errorFlag = false;
                                        datePage++;
                                        clearScreen();

                                    }
                                    break;
                                case 2:
                                    if (datePage > 1) {
                                        datePage--;
                                        clearScreen();

                                        errorFlag = false;
                                    } else {
                                        errorFlag = false;
                                        Report.generateReport(tempEarn, tempRedeem, tempExpired, startDate, endDate);
                                        System.out.print("Enter your choice: ");
                                    }
                                    break;
                                case 3:
                                    if (datePage > 1) {
                                        Report.generateReport(tempEarn, tempRedeem, tempExpired, startDate, endDate);
                                        System.out.print("Enter your choice: ");
                                    } else {

                                        System.out.println("Invalid choice. Please try again.");
                                        errorFlag = true;
                                    }
                                    break;

                                case 0:
                                    datePage = 0;
                                    errorFlag = false;
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                                    errorFlag = true;
                                    break;
                            }
                        } catch (InputMismatchException ex) {
                            System.out.println("Please enter valid input:");
                            scanner.nextLine();
                            errorFlag = true;
                        }
                    } while (errorFlag);
                }
                break;
            case 'V':
                boolean viewErrorFlag = false;
                System.out.println(DIVIDER);
                System.out.println("KnowledgeKash Admins > Transaction History > Find transaction by username");
                System.out.println(DIVIDER);
                do {
                    System.out.println("Enter username OR [0] for back: ");
                    String usernameInput = scanner.nextLine();
                    Client client = Client.getClient(usernameInput);
                    int viewPage = 1;
                    if (usernameInput.equals("0")) {
                        errorFlag = false;
                    } else if (client == null) {
                        System.out.println("User not exist");
                        errorFlag = true;
                    } else {
                        errorFlag = false;

                        int viewLastRecord = TransactionHistory.findTransactionByUsername(usernameInput, viewPage);
                        System.out.println("1. Next Page");
                        System.out.print(viewPage == 1 ? "2. Generate Report\n" : "2.Previous page\n");
                        System.out.print(viewPage == 1 ? "0. Back\n" : "3. Generate Report\n");
                        System.out.print(viewPage == 1 ? "" : "0. Back\n");
                        System.out.print("Enter your choice: ");

                        do {
                            try {
                                int choice = scanner.nextInt();
                                switch (choice) {
                                    case 1:
                                        int count = (viewPage) * 20 + 1;

                                        if (count > viewLastRecord) {
                                            System.out.println("This is the last page.");
                                            System.out.print("Enter your choice again: ");
                                        } else {
                                            viewPage++;
                                            clearScreen();

                                        }
                                        break;
                                    case 2:
                                        if (viewPage > 1) {
                                            viewPage--;
                                            clearScreen();

                                        } else {
                                            Report.findTransactionByUsername(usernameInput);
                                            System.out.print("Enter your choice: ");

                                        }
                                        break;
                                    case 3:
                                        if (viewPage > 1) {
                                            Report.findTransactionByUsername(usernameInput);
                                            System.out.print("Enter your choice: ");

                                        } else {
                                            System.out.println("Invalid choice. Please try again.");
                                        }
                                        break;

                                    case 0:
                                        viewPage = 0;
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                        break;
                                }
                            } catch (InputMismatchException ex) {
                                System.out.println("Please enter valid input:");
                                scanner.nextLine();
                                errorFlag = true;
                            }
                        } while (viewPage > 0);
                    }

                } while (errorFlag);
                break;
        }
    }
}
