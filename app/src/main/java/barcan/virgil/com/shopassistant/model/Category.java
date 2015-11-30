package barcan.virgil.com.shopassistant.model;

/**
 * Created by virgil on 28.11.2015.
 */
public class Category {

    private String categoryName;

    public Category() {
        categoryName = "";
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
