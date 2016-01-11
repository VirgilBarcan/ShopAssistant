package barcan.virgil.com.shopassistant.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by virgil on 30.11.2015.
 */
public class Company {

    private String companyID;
    private String companyName;
    private String companyInfo;
    //private Timetable openHours;
    private Location location;
    private List<Product> allProducts;
    private List<Product> availableProducts;
    private Double companyRating;

    public Company() {
        companyID = "";
        companyName = "";
        companyInfo = "";
        companyRating = 0.0;
        location = new Location();

        allProducts = new ArrayList<>();
        availableProducts = new ArrayList<>();
    }

    public Company(Company company) {
        this.companyID = new String(company.getCompanyID());
        this.companyName = new String(company.getCompanyName());
        this.companyInfo = new String(company.getCompanyInfo());
        this.companyRating = new Double(company.getCompanyRating());
        this.location = new Location(company.getLocation());

        //TODO: Test if this is really correct! May need some cloning
        this.allProducts = new ArrayList<>(); this.allProducts.addAll(company.getAllProducts());
        this.availableProducts = new ArrayList<>(); this.availableProducts.addAll(company.getAvailableProducts());
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        return !(companyName != null ? !companyName.equals(company.companyName) : company.companyName != null);

    }

    @Override
    public int hashCode() {
        return companyName != null ? companyName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyID='" + companyID + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyInfo='" + companyInfo + '\'' +
                ", location=" + location +
                ", companyRating=" + companyRating +
                '}';
    }
}
