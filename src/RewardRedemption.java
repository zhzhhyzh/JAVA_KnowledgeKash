
public class RewardRedemption {

    private static RewardCatalogue catalogue = new RewardCatalogue();

//    public RewardRedemption(RewardCatalogue catalogue) {
//        this.catalogue = catalogue;
//    }

    public static void redeemProduct(int productId, String fileName) {
        if (catalogue.productExists(productId)) {
            if (catalogue.getProductStock(productId) > 0) {
                catalogue.updateProductStock(productId, -1,fileName);
                System.out.println("Product redeemed successfully.");
            } else {
                System.out.println("Product is out of stock. Can't redeem.");
            }
        } else {
            System.out.println("Product not found in the catalogue.");
        }
    }

    

}
