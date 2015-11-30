package barcan.virgil.com.shopassistant.model;

/**
 * Created by virgil on 28.11.2015.
 */
public class Category {

    private String categoryID;
    private String categoryName;

    public Category() {
        categoryID = "";
        categoryName = "";
    }

    public Category(String categoryName) {
        categoryID = "";
        this.categoryName = categoryName;
    }

    public Category(String categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
