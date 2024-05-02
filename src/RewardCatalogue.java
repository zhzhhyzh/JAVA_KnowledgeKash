
import java.io.*;
import java.util.ArrayList;

public class RewardCatalogue {

    private static final String PROD_FILE_PATH = "product.txt";
    private static ArrayList<Integer> rewardId = new ArrayList<>();
    private static ArrayList<String> name = new ArrayList<>();
    private static ArrayList<String> description = new ArrayList<>();
    private static ArrayList<Integer> pointCost = new ArrayList<>();
    private static ArrayList<Integer> stock = new ArrayList<>();
    private static int itemCount = 3; //HAVE INITIAL 3 PRODUCT ADDED

    public static String tempName;
    public static String tempDescription;
    public static int tempPoint;
    public static int tempStock;

    static {
        loadProductsFromFile();
    }

    private static void loadProductsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PROD_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String prodName = data[1];
                String prodDesc = data[2];
                int cost = Integer.parseInt(data[3]);
                int prodStock = Integer.parseInt(data[4]);
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

    public static void addProduct(String productName, String productDescription, int cost, int productStock) {
        int id = 1000 + itemCount + 1;
        rewardId.add(id);
        name.add(productName);
        description.add(productDescription);
        pointCost.add(cost);
        stock.add(productStock);
        itemCount++;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROD_FILE_PATH, true))) {
            writer.write(String.format("%d,%s,%s,%d,%d%n", id, productName, productDescription, cost, productStock));
        } catch (IOException e) {
            System.err.println("Error adding product to file: " + e.getMessage());
        }
        System.out.println("Product added successfully with ID: " + id);
    }

    public static void updateProduct(int productId, String newName, String newDescription, int newPointCost, int newStock) {
        int index = findProductIndex(productId);
        if (index != -1) {
            name.set(index, newName);
            description.set(index, newDescription);
            pointCost.set(index, newPointCost);
            stock.set(index, newStock);
            // Update the product in the file
            updateProductInFile(index);
            System.out.println("Product updated successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public static void deleteProduct(int productId) {
        int index = findProductIndex(productId);
        if (index != -1) {
            rewardId.remove(index);
            name.remove(index);
            description.remove(index);
            pointCost.remove(index);
            stock.remove(index);
            itemCount--;
            // Update the file after removing the product
            updateFileAfterDeletion();
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void updateProductInFile(int index) {
        try (RandomAccessFile file = new RandomAccessFile(PROD_FILE_PATH, "rw")) {
            file.seek(index * 30); // Assuming each line in the file occupies 30 bytes
            file.writeBytes(String.format("%d,%s,%s,%d,%d%n", rewardId.get(index), name.get(index),
                    description.get(index), pointCost.get(index), stock.get(index)));
        } catch (IOException e) {
            System.err.println("Error updating product in file: " + e.getMessage());
        }
    }

    private static void updateFileAfterDeletion() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROD_FILE_PATH))) {
            for (int i = 0; i < itemCount; i++) {
                writer.write(String.format("%d,%s,%s,%d,%d%n", rewardId.get(i), name.get(i),
                        description.get(i), pointCost.get(i), stock.get(i)));
            }
        } catch (IOException e) {
            System.err.println("Error updating file after deletion: " + e.getMessage());
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

    public void listProducts() {
        if (itemCount == 0) {
            System.out.println("No products available.");
            return;
        }

        // Print table header
        System.out.printf("%-12s | %-20s | %-30s | %-10s | %-6s%n",
                "Product ID", "Name", "Description", "Point Cost", "Stock");
        System.out.println("------------------------------------------------------------------------");

        try (BufferedReader reader = new BufferedReader(new FileReader(PROD_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String prodName = data[1];
                String prodDesc = data[2];
                int cost = Integer.parseInt(data[3]);
                int prodStock = Integer.parseInt(data[4]);
                System.out.printf("%-12d | %-20s | %-30s | %-10d | %-6d%n", id, prodName, prodDesc, cost, prodStock);
            }
        } catch (IOException e) {
            System.err.println("Error reading products from file: " + e.getMessage());
        }
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

    public static String viewProduct(int productId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PROD_FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                if (id == productId) {
                    setTempName(data[1]);
                    setTempDescription(data[2]);
                    setTempPoint(Integer.parseInt(data[3]));
                    setTempStock(Integer.parseInt(data[4]));

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

    public void updateProductStock(int productId, int change) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PROD_FILE_PATH)); BufferedWriter writer = new BufferedWriter(new FileWriter(PROD_FILE_PATH + ".tmp"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                if (id == productId) {
                    int updatedStock = Integer.parseInt(data[4]) + change;
                    data[4] = String.valueOf(updatedStock);
                }
                writer.write(String.join(",", data));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating product stock: " + e.getMessage());
        }
    }

    public boolean productExists(int productId) {
        return findProductIndex(productId) != -1;
    }
}
