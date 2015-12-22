package barcan.virgil.com.shopassistant.backend;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import barcan.virgil.com.shopassistant.backend.backend.database.DatabaseHelper;
import barcan.virgil.com.shopassistant.frontend.MainActivity;
import barcan.virgil.com.shopassistant.model.Category;
import barcan.virgil.com.shopassistant.model.Company;
import barcan.virgil.com.shopassistant.model.CompanyUser;
import barcan.virgil.com.shopassistant.model.Constants;
import barcan.virgil.com.shopassistant.model.Product;
import barcan.virgil.com.shopassistant.model.RegularUser;
import barcan.virgil.com.shopassistant.model.User;

/**
 * This class should handle the flow of data from the frontend to the model and back
 * It should have knowledge about the user's data: username, password, shoppingList, productList, etc. <= Database
 * It should be able to save the information in a way that will be safe enough <= Database
 * It should start the location monitoring service => it is a Service itself
 * It should display notifications when needed
 * Created by virgil on 29.11.2015.
 */
public class Controller {

    //The controller needs to access data from the database
    private DatabaseHelper databaseHelper;
    //The controller needs to access info about the user that is logged in
    private SharedPreferences sharedPreferences;
    //The controller needs to know the view
    private MainActivity mainActivity;
    //The controller needs the context
    private Context context;
    //Store the shop whose products will be shown in the shopping list
    private String shopToShow;

    private static Controller instance;

    private Controller() {
        //do nothing here
    }

    private Controller(Context context) {
        this.context = context;

        //Init sharedPreferences
        initializeSharedPreferences();

        //Create DatabaseHelper
        createDatabaseHelper();

        //Check Database
        checkDatabaseHelper();
    }

    /**
     * This function is the getter for the Singleton
     * @return the Controller instance
     */
    public static Controller getInstance() {
        if (Controller.instance == null) {
            Controller.instance = new Controller();
        }

        return Controller.instance;
    }

    /**
     * This function is the getter for the Singleton
     * @param context the Context
     * @return the Controller instance
     */
    public static Controller getInstance(Context context) {
        if (Controller.instance == null) {
            Controller.instance = new Controller(context);
        }

        return Controller.instance;
    }

    /**
     * This method is used to start the DatabaseHelper
     * The DatabaseHelper class handles communication with the database
     */
    public void createDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
            databaseHelper.createDatabase();
        }
    }

    /**
     * This method is used to check if the DatabaseHelper was initialized correctly
     * It performs a get to check if data is fetched
     */
    private void checkDatabaseHelper() {
        //Print all regular users
        Map<String, RegularUser> allRegularUsers = databaseHelper.getAllRegularUsers();

        if(allRegularUsers != null){
            for (RegularUser regularUser : allRegularUsers.values())
                System.out.println(regularUser);
        }

        //Print all users that work at Emag
        Map<String, CompanyUser> allUsersOfCompany = databaseHelper.getAllUsersOfCompany("Emag");

        if(allUsersOfCompany != null){
            for (CompanyUser companyUser : allUsersOfCompany.values())
                System.out.println(companyUser);
        }
    }

    /**
     * This method is called in order to initialize the shared preferences
     */
    public void initializeSharedPreferences() {
        if (sharedPreferences == null)
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context.getApplicationContext());
    }

    /**
     * Setup sharedPreferences when the app is opened for the first time
     */
    public void setupSharedPreferences() {
        initializeSharedPreferences();
        //sharedPreferences = mainActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(Constants.SHARED_PREFERENCES_USERNAME_LOG_IN, "");
        sharedPreferencesEditor.apply();
    }

    /**
     * This function checks if the user is logged in
     * @return true if the user is logged in, false otherwise
     */
    public boolean isLogged() {
        initializeSharedPreferences();

        String usernameLogIn = sharedPreferences.getString(Constants.SHARED_PREFERENCES_USERNAME_LOG_IN, "");

        return !usernameLogIn.isEmpty();
    }

    /**
     * Get the currently connected user
     * @return the currently connected user
     */
    public User getConnectedUser() {
        User connectedUser = null;

        if (isLogged()) {
            String username = sharedPreferences.getString(Constants.SHARED_PREFERENCES_USERNAME_LOG_IN, "");
            connectedUser = databaseHelper.getRegularUser(username);

            if (connectedUser == null)
                connectedUser = databaseHelper.getCompanyUser(username);
            else
                return connectedUser;
        }

        System.out.println("Controller.getConnectedUser: connectedUser=" + connectedUser);

        return connectedUser;
    }

    /**
     * This method is used to save the username of the user that has logged in
     * @param username the username
     */
    public void saveUsernameLoggedIn(String username) {
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(Constants.SHARED_PREFERENCES_USERNAME_LOG_IN, username);
        sharedPreferencesEditor.apply();
    }

    /**
     * This method updates the shared preferences to logout the user
     */
    public void logoutUser() {
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(Constants.SHARED_PREFERENCES_USERNAME_LOG_IN, "");
        sharedPreferencesEditor.apply();
    }

    /**
     * Should the service be started?
     * @return true if the service should be started, false otherwise
     */
    public boolean startService() {
        return sharedPreferences.getBoolean("applicationNotifications", true);
    }

    /**
     * This method checks if the given <username, password> pair exist in the database
     * @param username the username
     * @param password the password
     * @return true if the <username, password> pair exists in the database, false otherwise
     */
    public boolean existsUserInDatabase(String username, String password) {
        //Check regular users
        Map<String, RegularUser> regularUsers = databaseHelper.getAllRegularUsers();
        for (RegularUser regularUser : regularUsers.values()) {
            if (regularUser.getUsername().equals(username) && regularUser.getPassword().equals(password))
                return true;
        }

        //Check company users
        Map<String, CompanyUser> companyUsers = databaseHelper.getAllCompanyUsers();
        for (CompanyUser companyUser : companyUsers.values()) {
            if (companyUser.getUsername().equals(username) && companyUser.getPassword().equals(password))
                return true;
        }

        return false;
    }

    /**
     * Add a new regular user to the database
     * @param regularUser the new regular user to be added
     * @return true if the user was added, false otherwise (if the user already exists or something wrong happened at insert)
     */
    public boolean addNewRegularUser(RegularUser regularUser) {
        //Check if the user doesn't already exist in the database
        if (existsUserInDatabase(regularUser.getUsername(), regularUser.getPassword()))
            return false;

        //Add the new user to the database
        return databaseHelper.addNewRegularUser(regularUser) >= 0;
    }

    /**
     * Add a new company user to the database
     * @param companyUser the new company user to be added
     * @return true if the user was added, false otherwise (if the user already exists or something wrong happened at insert)
     */
    public boolean addNewCompanyUser(CompanyUser companyUser) {
        //Check if the user doesn't already exist in the database
        if (existsUserInDatabase(companyUser.getUsername(), companyUser.getPassword()))
            return false;

        //Add the new user to the database
        return databaseHelper.addNewCompanyUser(companyUser) >= 0;
    }

    /**
     * Get the Product with the given productID
     * @param productID the productID of the wanted product
     * @return the product with the productID or null if it doesn't exist
     */
    public Product getProductWithProductID(String productID) {
        return databaseHelper.getProductWithProductID(productID);
    }

    /**
     * Get the list of all Products
     * @return the list of all Products
     */
    public List<Product> getAllProducts() {
        return databaseHelper.getAllProducts();
    }

    /**
     * Get the list of all Products sold by the Company given by its companyID
     * @param companyID the id of the Company
     * @return the list of all Products sold by the Company
     */
    public List<Product> getAllProductsSoldBy(String companyID) {
        List<Product> allProducts = databaseHelper.getAllProducts();
        List<Product> result = new ArrayList<>();

        for (Product product : allProducts)
            if (product.getProductSeller().getCompanyID().equals(companyID))
                result.add(product);

        return result;
    }

    /**
     * Get the list of all Products in the given Category
     * @param categoryID the id of the Category
     * @return the list of all Products sold by the Company
     */
    public List<Product> getAllProductsInCategory(String categoryID) {
        List<Product> allProducts = databaseHelper.getAllProducts();
        List<Product> result = new ArrayList<>();

        for (Product product : allProducts)
            if (product.getProductCategory().getCategoryID().equals(categoryID))
                result.add(product);

        return result;
    }

    /**
     * Get the product image
     * @param product the product
     * @return the product image
     */
    public Bitmap getProductImage(Product product) {
        return databaseHelper.getProductImage(product.getProductID());
    }

    /**
     * Get the product image
     * @param productID the id of the product
     * @return the product image
     */
    public Bitmap getProductImage(String productID) {
        return databaseHelper.getProductImage(productID);
    }

    /**
     * Get the product image
     * @param product the product
     * @param requiredWidth the required width of the resulting Bitmap
     * @param requiredHeight the required height of the resulting Bitmap
     * @return the product image
     */
    public Bitmap getProductImage(Product product, int requiredWidth, int requiredHeight) {
        return databaseHelper.getProductImage(product.getProductID(), requiredWidth, requiredHeight);
    }

    /**
     * Get the product image
     * @param productID the id of the product
     * @param requiredWidth the required width of the resulting Bitmap
     * @param requiredHeight the required height of the resulting Bitmap
     * @return the product image
     */
    public Bitmap getProductImage(String productID, int requiredWidth, int requiredHeight) {
        return databaseHelper.getProductImage(productID, requiredWidth, requiredHeight);
    }

    /**
     * Get the shopping list of the user
     * @param user the user whose shopping list we want
     * @return the shopping list of the user or null if it doesn't exist
     */
    public List<Product> getUserShoppingList(User user) {
        if (shopToShow == null || shopToShow.equals("ALL"))
            return databaseHelper.getUserShoppingList(user);
        else
            return databaseHelper.getUserShoppingListSortedByShop(user, shopToShow);
    }

    /**
     * Get the map of all Companies from the database
     * A map is used to make it easy to access the objects when having their IDs
     * @return the map of all Companies from the database
     */
    public Map<String, Company> getCompanies() {
        return databaseHelper.getAllCompanies();
    }

    /**
     * Get the list of all Companies selling products from the shopping list of the user
     * @param user the user whose shopping list we want to check
     * @return the list of all Companies selling products from the shopping list of the user
     */
    public List<Company> getShoppingListCompanies(User user) {
        List<Company> userShoppingListCompanies = new ArrayList<>();
        List<Product> userShoppingList = getUserShoppingList(user);
        Map<String, Company> allCompanies = getCompanies();

        for (Product product : userShoppingList) {
            Company company = allCompanies.get(product.getProductSeller().getCompanyID());

            if (!userShoppingListCompanies.contains(company)) {
                userShoppingListCompanies.add(company);
            }
        }

        return userShoppingListCompanies;
    }

    /**
     * Set the shop(s) to show in the shopping list
     * @param shopToShow the shop(s) to show in the shopping list
     */
    public void setShopToShow(String shopToShow) {
        this.shopToShow = shopToShow;
    }

    /**
     * Get the shop(s) to show in the shopping list
     * @return the shop(s) to show in the shopping list
     */
    public String getShopToShow() {
        return shopToShow;
    }

    /**
     * Get the Category with the given categoryID
     * @param categoryID the categoryID of the wanted category
     * @return the category with the categoryID or null if it doesn't exist
     */
    public Category getCategoryWithCategoryID(String categoryID) {
        return databaseHelper.getCategoryWithCategoryID(categoryID);
    }

    /**
     * Get all categories from the Database
     * @return the list of all categories
     */
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();

        for (Category category : databaseHelper.getAllCategories().values())
            categories.add(category);

        return categories;
    }

    public List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<>();

        for (Company company : databaseHelper.getAllCompanies().values())
            companies.add(company);

        return companies;
    }
}
