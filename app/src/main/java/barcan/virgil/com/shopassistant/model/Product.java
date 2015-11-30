package barcan.virgil.com.shopassistant.model;

/**
 * Created by virgil on 28.11.2015.
 */
public class Product {

    private String productName;
    private Category productCategory;
    private Company productSeller;
    private Price productPrice;
    private Double productRating;

    public Product() {
        productName = "";
        productCategory = new Category();
        productSeller = new Company();
        productPrice = new Price();
        productRating = 0.0;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Category getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Category productCategory) {
        this.productCategory = productCategory;
    }

    public Double getProductRating() {
        return productRating;
    }

    public void setProductRating(Double productRating) {
        this.productRating = productRating;
    }

    public Price getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Price productPrice) {
        this.productPrice = productPrice;
    }

    public Company getProductSeller() {
        return productSeller;
    }

    public void setProductSeller(Company productSeller) {
        this.productSeller = productSeller;
    }
}
