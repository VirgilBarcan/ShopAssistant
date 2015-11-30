package barcan.virgil.com.shopassistant.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by virgil on 28.11.2015.
 */
public class RegularUser extends  User{

    private List<Category> preferredCategories;
    private List<Company> preferredCompanies;
    private List<Product> shoppingList;

    public RegularUser() {
        super.setUserID(0);
        super.setUsername("");
        super.setPassword("");
        super.setFullName("");

        this.preferredCategories = new ArrayList<>();
        this.preferredCompanies = new ArrayList<>();
        this.shoppingList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "RegularUser{" +
                "username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", pathToImage='" + getPathToImage() + '\'' +
                '}';
    }
}
