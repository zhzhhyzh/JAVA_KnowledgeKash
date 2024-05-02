
public class RewardRedemption {

    private static RewardCatalogue catalogue = new RewardCatalogue();

//    public RewardRedemption(RewardCatalogue catalogue) {
//        this.catalogue = catalogue;
//    }

    public static void redeemProduct(int productId) {
        if (catalogue.productExists(productId)) {
            if (catalogue.getProductStock(productId) > 0) {
                catalogue.updateProductStock(productId, -1);
                System.out.println("Product redeemed successfully.");
            } else {
                System.out.println("Product is out of stock. Cannot redeem.");
            }
        } else {
            System.out.println("Product not found in the catalogue.");
        }
    }

    public static void listProducts() {
        catalogue.listProducts();
    }

}
