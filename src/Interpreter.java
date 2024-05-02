
import java.util.Scanner;
import java.util.InputMismatchException;
import java.lang.NullPointerException;

public class Interpreter {

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
        Client[] realClient = new Client[1];
        Admin realAdmin;
        RewardCatalogue[] rewardCatalogue = new RewardCatalogue[1];

        if (checkFileInd) {
            Scanner scanner = new Scanner(System.in);
            boolean running = true;
            boolean menuError = false;
            boolean errorFlag = false;
            while (running) {
                asciiArt();
                do {
                    System.out.println(DIVIDER);
                    System.out.println("Welcome to KnowledgeKash");
                    System.out.println(DIVIDER);
                    System.out.println("1. Register");
                    System.out.println("2. Login");
                    System.out.println("3. Exit");
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
                                case 3:
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
                                            break;
                                        case 2:
                                            errorFlag = false;
                                            callRewardsCatalogue(rewardCatalogue);
                                            break;
                                        case 3:
                                            errorFlag = false;
                                            System.out.println("Check Transaction History");
                                            break;
                                        case 4:
                                            errorFlag = false;
                                            callCheckUser(scanner);
                                            break;
                                        case 0:
                                            errorFlag = false;
//                                        System.out.println("Exiting...");
                                            logout(scanner, loggedIn, adminFlag);

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
                                    int choice = scanner.nextInt();
                                    scanner.nextLine(); // Consume newline

                                    // Process user choice
                                    switch (choice) {
                                        case 1:
                                            errorFlag = false;
                                            callAnswerQuestion(scanner);
                                            break;
                                        case 2:
                                            errorFlag = false;

                                            callRedeemRewards(rewardCatalogue, scanner, username);
                                            break;
                                        case 3:
                                            errorFlag = false;
                                            callProfile(username, scanner);
                                            break;
                                        case 0:
                                            errorFlag = false;
                                            logout(scanner, loggedIn, adminFlag);

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

        String tempSave = User.login(username, password);

        // Check if login was successful
        if (tempSave != null) {
            if (username.equals("admin1") || username.equals("admin2") || username.equals("admin3")) {
                adminFlag[0] = true;
            } else {
                PassINusername[0] = tempSave;

            }
            clearScreen();
            loggedIn[0] = true;
            System.out.println("Login successful! Welcome, " + tempSave + "!");
            return false;
        } else {
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
            System.out.println("Are you sure?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    loggedIn[0] = false; // Set loggedIn to false
                    adminFlag[0] = false;
                    System.out.println("Logged out successfully.");
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

    public static void callRewardsCatalogue(RewardCatalogue[] rewardCatalogue) {
        Scanner scanner = new Scanner(System.in);
        boolean errorFlag = false;
        boolean running = true;
        do {

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
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Invalid input. Please try again.");
                    scanner.nextLine();
                    errorFlag = true;
                }
            } while (errorFlag);
        } while (running);
        scanner.close();
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
            System.out.println("Please Enter rewardId:");
            int rewardId = scanner.nextInt();
            String showInfo = RewardCatalogue.viewProduct(rewardId);
            if (showInfo != null) {
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
                } while (!stock.isEmpty() && !isValidInteger(point));
                name = name.isEmpty() ? RewardCatalogue.getTempName() : name;
                description = description.isEmpty() ? RewardCatalogue.getTempDescription() : description;
                int pointCost = point.isEmpty() ? RewardCatalogue.getTempPoint() : Integer.parseInt(point);
                int stockAmount = point.isEmpty() ? RewardCatalogue.getTempStock() : Integer.parseInt(stock);
                RewardCatalogue.updateProduct(rewardId, name, description, pointCost, stockAmount);
            }

        } else {
            System.out.println(DIVIDER);
            System.out.println("KnowledgeKash Admin > Manage Rewards > Delete Product");
            System.out.println(DIVIDER);
            System.out.println("Please Enter rewardId:");
            int rewardId = scanner.nextInt();
            String showInfo = RewardCatalogue.viewProduct(rewardId);
            if (showInfo != null) {
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

                        }
                        break;
                    case 2:
                        if (page > 1) {
                            page--;
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
            } while (errorFlag);
        }
    }

    public static void callRedeemRewards(RewardCatalogue[] rewardCatalogue, Scanner scanner, String[] username) {
        boolean errorFlag = false;
        int choices = 0;
        System.out.println(DIVIDER);
        System.out.println("KnowledgeKash Menu > Redeem Rewards");
        System.out.println(DIVIDER);
        RewardRedemption.listProducts();
        System.out.print("Enter RewardId (Enter[0] to Exit):");
        do {
            try {
                choices = scanner.nextInt();
                errorFlag = false;
                if (choices < 0) {
                    System.out.println("Please enter valid input:");
                    System.out.print("Enter RewardId (Enter[0] to Exit):");
                    errorFlag = true;
                } else if (choices > 0 && choices < 1001) {
                    System.out.println("Please enter valid rewardId(Exp: 1001):");
                    System.out.print("Enter RewardId (Enter[0] to Exit):");
                    errorFlag = true;
                }

            } catch (InputMismatchException ex) {
                System.out.println("Please enter valid input:");
                System.out.print("Enter RewardId (Enter[0] to Exit):");
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
                String responseGetter = scanner.nextLine();
                if (responseGetter.equals("1")) {
                    RewardRedemption.redeemProduct(choices);

                    PointManagement pm = new PointManagement();
                    pm.getClient(username[0]);
                    int tempInt = rewardCatalogue[0].getTempPoint();
                    pm.IncreasePoints(tempInt);
                    TransactionHistory th = new TransactionHistory(username[0], 'E', rewardCatalogue[0].getTempPoint());
                    th.writeTransactionToFile();
                }
            }
        }
    }

    public static void callProfile(String[] username, Scanner scanner) {
        System.out.println(DIVIDER);
        System.out.println("KnowledgeKash Menu > Profile");
        System.out.println(DIVIDER);
        PointManagement pm = new PointManagement();
        pm.getClient(username[0]);
        System.out.println(pm.toString());
        System.out.println("1. View/Update Profile");
        System.out.println("0. Back");
        System.out.println("Enter your choice:");
        int choice = 0;
        boolean errorFlag = false;

        do {
            try {
                choice = scanner.nextInt();
                errorFlag = false;
                if (choice != 1 && choice != 0) {
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
        }
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
        System.out.println(DIVIDER);
        System.out.println("KnowledgeKash Admin > Manage Question");
        System.out.println(DIVIDER);
        qr.listQuestion(page);
        int lastRecord;
        boolean errorFlag = false;
        while (page >= 1) {
            System.out.println("Page: " + page);
            lastRecord = Admin.listUsers(page);
            System.out.println("1. Next Page");
            System.out.print(page == 1 ? "" : "2. Previous Page\n");
            System.out.println("3. Create Question");
            System.out.println("4. Update Question");
            System.out.println("5. Delete Question");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");
            do {
                try {
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

                            }
                            break;
                        case 2:
                            if (page > 1) {
                                page--;
                                errorFlag = false;
                            } else {
                                System.out.println("Invalid choice. Please try again.");
                                errorFlag = true;
                            }
                            break;
                        case 3:
                            manageQuestion('A', scanner);
                            errorFlag = false;
                            break;
                        case 4:
                            manageQuestion('C', scanner);
                            errorFlag = false;
                            break;
                        case 5:
                            manageQuestion('D', scanner);
                            errorFlag = false;
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
                String confirmKey = scanner.nextLine();
                if (confirmKey.equals("1")) {
                    qr.deleteQuestion(choice);
                }
            }
        }
    }

    public static void callAnswerQuestion(Scanner scanner) {
        boolean errorFlag = false;
        System.out.println(DIVIDER);
        System.out.println("KnowledgeKash Menu > Answer Question");
        System.out.println(DIVIDER);
        System.out.println("1. Question's answer by Selection");     
        System.out.println("2. Question's answer by Boolean");
        System.out.println("3. Question's answer by String");      
        System.out.print("Choose your selection: ");
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

    }

}
