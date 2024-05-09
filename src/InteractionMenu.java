
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
    private static final int TRANSACTIONS_PER_PAGE = 20;
    private static final String TRANSACTION_FILE_PATH = "transactionHistory.txt";
    public static final String QUES_FILE_PATH = "question.txt";
    private static final String PROD_FILE_PATH = "product.txt";
    private static final String USER_FILE_PATH = "user.txt";

    public static final String DIVIDER = "=====================================================================";
    private static final String DIVIDER2 = "--------------------------------------------------------------------------------------";

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
                                            clearScreen();
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

        String tempSave = Client.login(username, password, USER_FILE_PATH);

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

            Policy.getDayCount(USER_FILE_PATH);
            Policy.applyPolicy(username, TRANSACTION_FILE_PATH, USER_FILE_PATH);
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
        Client registeredClient = Client.register(username, password, confirmPassword, name, phoneNumber, email, USER_FILE_PATH);
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
            rewardCatalogue[0].listProducts(PROD_FILE_PATH);
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

            RewardCatalogue.addProduct(name, description, point, stock, PROD_FILE_PATH);

        } else if (actType == 'C') {
            System.out.println(DIVIDER);
            System.out.println("KnowledgeKash Admin > Manage Rewards > Update Product");
            System.out.println(DIVIDER);
            System.out.println("Please enter Product ID or [0] for Back:");
            int rewardId = scanner.nextInt();
            String showInfo = RewardCatalogue.viewProduct(rewardId, PROD_FILE_PATH);
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
                RewardCatalogue.updateProduct(rewardId, name, description, pointCost, stockAmount, PROD_FILE_PATH);
            }

        } else {
            System.out.println(DIVIDER);
            System.out.println("KnowledgeKash Admin > Manage Rewards > Delete Product");
            System.out.println(DIVIDER);
            System.out.println("Please Enter Product ID:");
            int rewardId = scanner.nextInt();
            String showInfo = RewardCatalogue.viewProduct(rewardId, PROD_FILE_PATH);
            if (showInfo != null) {
                scanner.nextLine(); //Clear input
                System.out.println(DIVIDER);
                System.out.println("Are you sure to delete?");
                System.out.println("Type [1] is yes");
                String responseGetter = scanner.nextLine();
                if (responseGetter.equals("1")) {
                    RewardCatalogue.deleteProduct(rewardId, PROD_FILE_PATH);
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
        System.out.println("KnowledgeKash Admin > Manage User");
        System.out.println(DIVIDER);
        int page = 1;
        int lastRecord;
        boolean errorFlag = false;
        while (page >= 1) {
            System.out.println("Page: " + page);
            lastRecord = Admin.listUsers(page, USER_FILE_PATH);
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
            pm.getClient(username[0], USER_FILE_PATH);
            int identifier = pm.getAvailablePoints();
            System.out.println(DIVIDER);
            System.out.println("KnowledgeKash Menu > Redeem Rewards");
            System.out.println(DIVIDER);
            rewardCatalogue[0].listProducts(PROD_FILE_PATH);

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
                String showInfo = RewardCatalogue.viewProduct(choices, PROD_FILE_PATH);
                if (showInfo != null) {
                    System.out.println("Are you sure to redeem?");
                    System.out.println("Type [1] is yes");
                    scanner.nextLine(); // Clear input
                    String responseGetter = scanner.nextLine();
                    if (responseGetter.equals("1")) {
                        int tempInt = RewardCatalogue.getTempPoint();
                        if (identifier < tempInt) {
                            clearScreen();
                            System.out.println("Point not enough to proceed.");
                        } else {
                            clearScreen();
                            RewardRedemption.redeemProduct(choices, PROD_FILE_PATH);
                            pm.decreasePoints(tempInt, USER_FILE_PATH);
                            TransactionHistory th = new TransactionHistory(username[0], 'R', tempInt, TRANSACTION_FILE_PATH);
                            th.writeTransactionToFile(TRANSACTION_FILE_PATH);
                            pm.getClient(username[0], USER_FILE_PATH);
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
            pm.getClient(username[0], USER_FILE_PATH);
            System.out.println(pm.toString());
            Policy.showExpired(username[0], USER_FILE_PATH);
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
                        System.out.print("Invalid choice. Please enter again:");
                    }
                } catch (InputMismatchException ex) {
                    System.out.print("Invalid Input. Please enter again:");
                    errorFlag = true;

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
                String[] retrievedRecord = TransactionHistory.findTransactionByUsername(username[0], viewPage, TRANSACTION_FILE_PATH, TRANSACTIONS_PER_PAGE);
                header();
                for (int i = 0; i + 1 < retrievedRecord.length; i += 5) {
                    if (retrievedRecord[i] == null) {
                        break;
                    } else {
                        tableBody(retrievedRecord[i], retrievedRecord[i + 1], retrievedRecord[i + 2], retrievedRecord[i + 3], retrievedRecord[i + 4]);

                    }
                }
                footer();
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

                                if (count > Integer.parseInt(retrievedRecord[100])) {
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
                                    Report.findTransactionByUsername(username[0], TRANSACTION_FILE_PATH);
                                    System.out.print("Enter your choice: ");
                                }
                                break;
                            case 3:
                                if (viewPage > 1) {
                                    Report.findTransactionByUsername(username[0], TRANSACTION_FILE_PATH);
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
            Client client = Client.getClient(username[0], USER_FILE_PATH);
            System.out.println(client.toString());

//            try{
//            }catch (NullPointerException ex){
//                System.out.println("Error here" + ex.getMessage());
//            }
            System.out.println("1. Update Profile");
            System.out.println("2. Update Password");

            System.out.println("0. Back");
            System.out.print("Enter your choice:");

            do {
                try {
                    choice = scanner.nextInt();
                    errorFlag = false;
                    if (choice != 1 && choice != 0 && choice != 2) {
                        errorFlag = true;
                        System.out.print("Invalid choice. Please enter again:");
                    }
                } catch (InputMismatchException ex) {
                    System.out.print("Invalid Input. Please enter again:");
                    errorFlag = true;
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
                client.updateProfile(username[0], password, name, phoneNumber, email, USER_FILE_PATH);

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
                client.updatePassword(username[0], oldPsw, newPsw, conPsw, USER_FILE_PATH);

            }
        }
    }

    public static boolean checkFileIndicator() {

        return CheckFile.checkUserFile(USER_FILE_PATH)
                && CheckFile.checkTransactionFile(TRANSACTION_FILE_PATH)
                && CheckFile.checkProdFile(PROD_FILE_PATH)
                && CheckFile.checkQuesFile(QUES_FILE_PATH);
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
            lastRecord = qr.listQuestion(page, QUES_FILE_PATH);
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
                            QuestionMcq qs = new QuestionMcq();
                            qs.createQuestion(QUES_FILE_PATH);
                            errorFlag = false;
                            break;
                        case 2:
                            QuestionTfq qb = new QuestionTfq();
                            qb.createQuestion(QUES_FILE_PATH);
                            errorFlag = false;
                            break;
                        case 3:
                            QuestionEssay qStr = new QuestionEssay();
                            qStr.createQuestion(QUES_FILE_PATH);
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
                QuestionMcq qs = new QuestionMcq();
                qs.updateQuestion(choice, QUES_FILE_PATH);
            } else if (choice > 100 && choice < 201) {
                QuestionTfq qb = new QuestionTfq();
                qb.updateQuestion(choice, QUES_FILE_PATH);
            } else if (choice > 200 && choice < 301) {
                QuestionEssay qStr = new QuestionEssay();
                qStr.updateQuestion(choice, QUES_FILE_PATH);
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
                qr.viewQuestion(choice, QUES_FILE_PATH);
                System.out.println("Are you sure to delete?");

                System.out.println("Type [1] to confirm");
                scanner.nextLine();
                String confirmKey = scanner.nextLine();
                if (confirmKey.equals("1")) {
                    qr.deleteQuestion(choice, QUES_FILE_PATH);
                }
            }
        }
    }

    public static void callAnswerQuestion(Scanner scanner, String[] username) {
        int choice = 0;
        PointManagement pm = new PointManagement();
        pm.getClient(username[0], USER_FILE_PATH);
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
                            QuestionMcq qs = new QuestionMcq();
                            int questionCountS = qs.getTotalCount(QUES_FILE_PATH);
                            questionCountS = (int) (Math.random() * questionCountS + 1);
                            int qSPoint = qs.answerQuestion(questionCountS, QUES_FILE_PATH);
                            System.out.println("Points earned: " + qSPoint);
                            pm.increasePoints(qSPoint, USER_FILE_PATH);
                            TransactionHistory th1 = new TransactionHistory(username[0], 'E', qSPoint, TRANSACTION_FILE_PATH);
                            th1.writeTransactionToFile(TRANSACTION_FILE_PATH);
                            errorFlag = false;
                            break;
                        case 2:
                            QuestionTfq qb = new QuestionTfq();
                            int questionCountBoo = qb.getTotalCount(QUES_FILE_PATH);
                            questionCountBoo = (int) (Math.random() * questionCountBoo + 101);
                            int qBPoint = qb.answerQuestion(questionCountBoo, QUES_FILE_PATH);
                            System.out.println("Points earned: " + qBPoint);

                            pm.increasePoints(qBPoint, USER_FILE_PATH);
                            TransactionHistory th2 = new TransactionHistory(username[0], 'E', qBPoint, TRANSACTION_FILE_PATH);
                            th2.writeTransactionToFile(TRANSACTION_FILE_PATH);
                            errorFlag = false;
                            break;
                        case 3:
                            QuestionEssay qStr = new QuestionEssay();
                            int questionCountStr = qStr.getTotalCount(QUES_FILE_PATH);
                            questionCountStr = (int) (Math.random() * questionCountStr + 201);
                            int qStrPoint = qStr.answerQuestion(questionCountStr, QUES_FILE_PATH);
                            System.out.println("Points earned: " + qStrPoint);

                            pm.increasePoints(qStrPoint, USER_FILE_PATH);
                            TransactionHistory th3 = new TransactionHistory(username[0], 'E', qStrPoint, TRANSACTION_FILE_PATH);
                            th3.writeTransactionToFile(TRANSACTION_FILE_PATH);
                            errorFlag = false;
                            break;
                        case 0:
                            errorFlag = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                            System.out.print("Please enter valid input:");
                            errorFlag = true;
                            break;
                    }
                } catch (InputMismatchException ex) {
                    System.out.print("Please enter valid input:");
                    scanner.nextLine();
                    errorFlag = true;
                }
            } while (errorFlag);
        } while (choice > 0);
    }

    public static void managePolicy(Scanner scanner) {
        System.out.println("Current Policy Day Count:" + Policy.getDayCount(USER_FILE_PATH));
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
            Policy.setDayCount(policyChoice, USER_FILE_PATH);
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
            String[] retrievedRecord = TransactionHistory.listTransaction(page, TRANSACTION_FILE_PATH, TRANSACTIONS_PER_PAGE);
            header();
            for (int i = 0; i + 1 < retrievedRecord.length; i += 5) {
                if (retrievedRecord[i] == null) {
                    break;
                } else {
                    tableBody(retrievedRecord[i], retrievedRecord[i + 1], retrievedRecord[i + 2], retrievedRecord[i + 3], retrievedRecord[i + 4]);

                }
            }
            footer();
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
                            if (count > Integer.parseInt(retrievedRecord[100])) {
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
                    String[] retrievedRecord = TransactionHistory.listTransactionByType(convertedType, typePage, TRANSACTION_FILE_PATH, TRANSACTIONS_PER_PAGE);
                    header();
                    for (int i = 0; i + 1 < retrievedRecord.length; i += 5) {
                        if (retrievedRecord[i] == null) {
                            break;
                        } else {
                            tableBody(retrievedRecord[i], retrievedRecord[i + 1], retrievedRecord[i + 2], retrievedRecord[i + 3], retrievedRecord[i + 4]);

                        }
                    }
                    footer();
                    System.out.println("1. Next Page");
                    System.out.print(typePage == 1 ? "0. Back\n" : "2.Previous page\n");
                    System.out.print("Enter your choice: ");

                    do {
                        try {
                            int choice = scanner.nextInt();
                            switch (choice) {
                                case 1:
                                    int count = (typePage) * 20 + 1;

                                    if (count > Integer.parseInt(retrievedRecord[100])) {
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
                    String[] retrievedRecord = TransactionHistory.listTransactionByTypeAndDate(convertedType, startDate, endDate, dateTypePage, TRANSACTION_FILE_PATH, TRANSACTIONS_PER_PAGE);
                    header();
                    for (int i = 0; i + 1 < retrievedRecord.length; i += 5) {
                        if (retrievedRecord[i] == null) {
                            break;
                        } else {
                            tableBody(retrievedRecord[i], retrievedRecord[i + 1], retrievedRecord[i + 2], retrievedRecord[i + 3], retrievedRecord[i + 4]);

                        }
                    }
                    footer();
                    System.out.println("1. Next Page");
                    System.out.print(dateTypePage == 1 ? "0. Back\n" : "2.Previous page\n");
                    System.out.print("Enter your choice: ");

                    do {
                        try {
                            int choice = scanner.nextInt();
                            switch (choice) {
                                case 1:
                                    int count = (dateTypePage) * 20 + 1;

                                    if (count > Integer.parseInt(retrievedRecord[100])) {
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
                    String[] retrievedRecord = TransactionHistory.listTransactionByDate(startDate, endDate, datePage, TRANSACTION_FILE_PATH, TRANSACTIONS_PER_PAGE);
                    header();
                    for (int i = 0; i + 1 < retrievedRecord.length; i += 5) {
                        if (retrievedRecord[i] == null) {
                            break;
                        } else {
                            tableBody(retrievedRecord[i], retrievedRecord[i + 1], retrievedRecord[i + 2], retrievedRecord[i + 3], retrievedRecord[i + 4]);

                        }
                    }
                    footer();
                    int TypeLastRecord = Integer.parseInt(retrievedRecord[100]);
                    int tempEarn = Integer.parseInt(retrievedRecord[101]);
                    int tempRedeem = Integer.parseInt(retrievedRecord[102]);
                    int tempExpired = Integer.parseInt(retrievedRecord[103]);

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
                    Client client = Client.getClient(usernameInput, USER_FILE_PATH);
                    int viewPage = 1;
                    if (usernameInput.equals("0")) {
                        errorFlag = false;
                    } else if (client == null) {
                        System.out.println("User not exist");
                        errorFlag = true;
                    } else {
                        errorFlag = false;

                        String[] retrievedRecord = TransactionHistory.findTransactionByUsername(usernameInput, viewPage, TRANSACTION_FILE_PATH, TRANSACTIONS_PER_PAGE);
                        header();
                        for (int i = 0; i + 1 < retrievedRecord.length; i += 5) {
                            if (retrievedRecord[i] == null) {
                                break;
                            } else {
                                tableBody(retrievedRecord[i], retrievedRecord[i + 1], retrievedRecord[i + 2], retrievedRecord[i + 3], retrievedRecord[i + 4]);

                            }
                        }
                        footer();
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

                                        if (count > Integer.parseInt(retrievedRecord[100])) {
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
                                            Report.findTransactionByUsername(usernameInput, TRANSACTION_FILE_PATH);
                                            System.out.print("Enter your choice: ");

                                        }
                                        break;
                                    case 3:
                                        if (viewPage > 1) {
                                            Report.findTransactionByUsername(usernameInput, TRANSACTION_FILE_PATH);
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

    public static void header() {
        System.out.println(DIVIDER2);
        System.out.printf("| %-15s | %-15s | %-10s | %-11s | %-19s |%n", "Transaction ID", "Username", "Type", "Points", "Date");
        System.out.println(DIVIDER2);

    }

    public static void footer() {
        System.out.println(DIVIDER2);
    }

    public static void tableBody(String field1, String field2, String field3, String field4, String field5) {
        System.out.printf("| %-15s | %-15s | %-10s | %-11s | %-19s |%n",
                field1, field2, field3, field4, field5);
    }
}
