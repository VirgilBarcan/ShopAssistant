package barcan.virgil.com.shopassistant.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by virgil on 30.11.2015.
 */
public class Company {

    private String companyName;
    private String companyInfo;
    //private Timetable openHours;
    private Location location;
    private List<Product> allProducts;
    private List<Product> availableProducts;
    private Double companyRating;

    public Company() {
        companyInfo = "";
        companyRating = 0.0;
        location = new Location();

        allProducts = new ArrayList<>();
        availableProducts = new ArrayList<>();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Product> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(List<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public List<Product> getAvailableProducts() {
        return availableProducts;
    }

    public void setAvailableProducts(List<Product> availableProducts) {
        this.availableProducts = availableProducts;
    }

    public Double getCompanyRating() {
        return companyRating;
    }

    public void setCompanyRating(Double companyRating) {
        this.companyRating = companyRating;
    }
}
