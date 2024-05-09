
import java.io.*;
import java.util.ArrayList;

public class RewardCatalogue {

    private static final String DELIMITER = ",";
    private static final String DIVIDER = "-------------------------------------------------------------------------------------------";
    private static ArrayList<Integer> rewardId = new ArrayList<>();
    private static ArrayList<String> name = new ArrayList<>();
    private static ArrayList<String> description = new ArrayList<>();
    private static ArrayList<Integer> pointCost = new ArrayList<>();
    private static ArrayList<Integer> stock = new ArrayList<>();
    private static int itemCount;

    public static String tempName;
    public static String tempDescription;
    public static int tempPoint;
    public static int tempStock;

    static {
        loadProductsFromFile();
    }

    private static void loadProductsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("product.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0].trim());
                String prodName = data[1];
                String prodDesc = data[2];
                int cost = Integer.parseInt(data[3].trim());
                int prodStock = Integer.parseInt(data[4].trim());
                rewardId.add(id);
                name.add(prodName);
                description.add(prodDesc);
                pointCost.add(cost);
                stock.add(prodStock);
                itemCount++;
            }
        } catch (IOException e) {
            System.err.println("Error loading products from file: " + e.getMessage());
        }
    }

    public static void addProduct(String productName, String productDescription, int cost, int productStock, String fileName) {
        int id = getRunningRewardId(fileName) + 1;
        rewardId.add(id);
        name.add(productName);
        description.add(productDescription);
        pointCost.add(cost);
        stock.add(productStock);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(String.format("%d,%s,%s,%d,%d%n", id, productName, productDescription, cost, productStock));
        } catch (IOException e) {
            System.err.println("Error adding product to file: " + e.getMessage());
        }
        System.out.println("Product added successfully with ID: " + id);
    }

    public static void updateProduct(int productId, String newName, String newDescription, int newPointCost, int newStock, String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName)); BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".tmp"))) {

            String line;
            boolean updated = false;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(DELIMITER);
                if (userData.length >= 1) {
                    String productIdStr = String.valueOf(productId);
                    String storedProductId = userData[0];
                    String pointCostStr = String.valueOf(newPointCost);
                    String stockStr = String.valueOf(newStock);
                    if (productIdStr.equals(storedProductId)) {
                        // Construct the updated line
                        String updatedLine = productIdStr + DELIMITER + newName + DELIMITER
                                + newDescription + DELIMITER + pointCostStr + DELIMITER + stockStr;
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
                System.err.println("rewardId not found.");
            }
        } catch (IOException e) {
            System.err.println("Error updating user data: " + e.getMessage());
        }

        // Replace the original file with the temporary file
        try {
            File originalFile = new File(fileName);
            File tempFile = new File(fileName + ".tmp");

            if (!tempFile.exists() || !tempFile.canRead()) {
                System.err.println("Temporary file is missing or cannot be read.");
            }

            if (!originalFile.delete()) {
                System.err.println("Failed to delete the original file.");
            }

            if (!tempFile.renameTo(originalFile)) {
                System.err.println("Failed to replace the original file with the temporary file.");
            }

            System.out.println("Product updated.");
        } catch (Exception ex) {
            System.err.println("Error replacing files: " + ex.getMessage());
        }

    }

    public static void deleteProduct(int rewardId, String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName)); BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".tmp"))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",", -1);

                if (userData.length > 0 && Integer.parseInt(userData[0]) == rewardId) {
                    found = true;
                    // Skip writing this line to the temporary file (effectively deleting it)
                } else {
                    // Write the line to the temporary file
                    writer.write(line);
                    writer.newLine();
                }

            }
            if (!found) {
                System.out.println("Question with ID " + rewardId + " not found.");
            } else {
                System.out.println("Question with ID " + rewardId + " deleted successfully.");
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

    private static int findProductIndex(int productId) {
        for (int i = 0; i < rewardId.size(); i++) {
            if (rewardId.get(i) == productId) {
                return i;
            }
        }
        return -1;
    }

    public void listProducts(String fileName) {
        if (itemCount == 0) {
            System.out.println("No products available.");
            return;
        }

        // Print table header
        System.out.println(DIVIDER);
        System.out.printf("%-12s | %-20s | %-30s | %-10s | %-6s|%n",
                "Product ID", "Name", "Description", "Point Cost", "Stock");
        System.out.println(DIVIDER);

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0].trim());
                String prodName = data[1];
                String prodDesc = data[2];
                int cost = Integer.parseInt(data[3].trim());
                int prodStock = Integer.parseInt(data[4].trim());
                System.out.printf("%-12d | %-20s | %-30s | %10d | %5d |%n", id, prodName, prodDesc, cost, prodStock);
            }
        } catch (IOException e) {
            System.err.println("Error reading products from file: " + e.getMessage());
        }
        System.out.println(DIVIDER);
    }

    public static void setTempName(String tempName) {
        RewardCatalogue.tempName = tempName;
    }

    public static void setTempDescription(String tempDescription) {
        RewardCatalogue.tempDescription = tempDescription;
    }

    public static void setTempPoint(int tempPoint) {
        RewardCatalogue.tempPoint = tempPoint;
    }

    public static void setTempStock(int tempStock) {
        RewardCatalogue.tempStock = tempStock;
    }

    public static String getTempName() {
        return tempName;
    }

    public static String getTempDescription() {
        return tempDescription;
    }

    public static int getTempPoint() {
        return tempPoint;
    }

    public static int getTempStock() {
        return tempStock;
    }

    public static String viewProduct(int productId, String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                if (id == productId) {
                    setTempName(data[1]);
                    setTempDescription(data[2]);
                    setTempPoint(Integer.parseInt(data[3].trim()));
                    setTempStock(Integer.parseInt(data[4].trim()));

                    return "Product ID: " + id + "\nName: " + data[1] + "\nDescription: " + data[2] + "\nPoint Cost: " + data[3] + "\nStock: " + data[4];
                }
            }
            System.out.println("Product not found.");

            return null;

        } catch (IOException e) {
            System.err.println("Error reading products from file: " + e.getMessage());
        }
        return null;
    }

    public int getProductStock(int productId) {
        int index = rewardId.indexOf(productId);
        if (index != -1) {
            return stock.get(index);
        } else {
            return 0; // Product not found, return 0 stock
        }
    }

    public void updateProductStock(int productId, int change, String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName)); BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".tmp"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                if (id == productId) {
                    int updatedStock = Integer.parseInt(data[4].trim()) + change;
                    data[4] = String.valueOf(updatedStock);
                }
                writer.write(String.join(",", data));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating product stock: " + e.getMessage());
        }

        // Replace the original file with the temporary file
        try {
            File originalFile = new File(fileName);
            File tempFile = new File(fileName + ".tmp");

            if (!tempFile.exists() || !tempFile.canRead()) {
                System.err.println("Temporary file is missing or cannot be read.");
            }

            if (!originalFile.delete()) {
                System.err.println("Failed to delete the original file.");
            }

            if (!tempFile.renameTo(originalFile)) {
                System.err.println("Failed to replace the original file with the temporary file.");
            }

        } catch (Exception ex) {
            System.err.println("Error replacing files: " + ex.getMessage());
        }
    }

    public boolean productExists(int productId) {
        return findProductIndex(productId) != -1;
    }

    private static int getRunningRewardId(String fileName) {
        int tempRewardId = 1001;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",", -1);

                if (userData.length > 0 && Integer.parseInt(userData[0]) > tempRewardId) {
                    tempRewardId = Integer.parseInt(userData[0]);
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading question file: " + e.getMessage());
            return -1;
        }
        return tempRewardId;
    }
}
