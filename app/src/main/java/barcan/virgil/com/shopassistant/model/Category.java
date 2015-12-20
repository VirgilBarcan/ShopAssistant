package barcan.virgil.com.shopassistant.model;

/**
 * Created by virgil on 28.11.2015.
 */
public class Category {

    private String categoryID;
    private String categoryName;
    private String categoryIcon;

    public Category() {
        categoryID = "";
        categoryName = "";
        categoryIcon = "";
    }

    public Category(String categoryName) {
        categoryID = "";
        categoryIcon = "";
        this.categoryName = categoryName;
    }

    public Category(Category category) {
        this.categoryID = new String(category.getCategoryID());
        this.categoryName = new String(category.getCategoryName());
        this.categoryIcon = "";
    }


    public Category(String categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryIcon = "";
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

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryID='" + categoryID + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
