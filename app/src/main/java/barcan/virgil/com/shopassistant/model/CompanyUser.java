package barcan.virgil.com.shopassistant.model;

/**
 * Created by virgil on 28.11.2015.
 */
public class CompanyUser extends User {

    private Company companyRepresented;

    public CompanyUser() {
        super.setUserID(0);
        super.setUsername("");
        super.setPassword("");
        super.setFullName(""); //Name Surname from CompanyName
        this.companyRepresented = new Company();
    }

    public Company getCompanyRepresented() {
        return companyRepresented;
    }

    public void setCompanyRepresented(Company companyRepresented) {
        this.companyRepresented = companyRepresented;
    }

    @Override
    public String toString() {
        return "CompanyUser{" +
                "username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", pathToImage='" + getPathToImage() + '\'' +
                '}';
    }

}
