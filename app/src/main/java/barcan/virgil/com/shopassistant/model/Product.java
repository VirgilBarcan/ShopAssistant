package barcan.virgil.com.shopassistant.model;

/**
 * Created by virgil on 28.11.2015.
 */
public class Product {

    private String productID;
    private String productName;
    private Category productCategory;
    private Company productSeller; //redundant data that simplifies access to this information
    private Price productPrice;
    private Double productRating;

    public Product() {
        productID = "";
        productName = "";
        productCategory = new Category();
        productSeller = new Company();
        productPrice = new Price();
        productRating = 0.0;
    }

    public Product(Product product) {
        this.productID = new String(product.getProductID());
        this.productName = new String(product.getProductName());
        this.productCategory = new Category(product.getProductCategory());
        this.productSeller = new Company(product.getProductSeller());
        this.productPrice = new Price(product.getProductPrice());
        this.productRating = new Double(product.getProductRating());
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
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

    public String getProductPriceString() {
        return productPrice.getPriceValue() + " " + productPrice.getPriceCurrency();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (productName != null ? !productName.equals(product.productName) : product.productName != null)
            return false;
        return !(productCategory != null ? !productCategory.equals(product.productCategory) : product.productCategory != null);

    }

    @Override
    public int hashCode() {
        int result = productName != null ? productName.hashCode() : 0;
        result = 31 * result + (productCategory != null ? productCategory.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID='" + productID + '\'' +
                ", productName='" + productName + '\'' +
                ", productCategory=" + productCategory.getCategoryName() +
                ", productSeller=" + productSeller.getCompanyName() +
                ", productPrice=" + productPrice +
                ", productRating=" + productRating +
                '}';
    }
}
