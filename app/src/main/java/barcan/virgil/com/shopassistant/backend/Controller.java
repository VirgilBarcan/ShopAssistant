package barcan.virgil.com.shopassistant.backend;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import barcan.virgil.com.shopassistant.backend.backend.database.DatabaseHelper;
import barcan.virgil.com.shopassistant.frontend.MainActivity;
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

    private MainActivity mainActivity;

    //The controller needs the context
    private Context context;

    private static Controller instance;

    private Controller() {
        //do nothing here
    }

    private Controller(Context context, MainActivity mainActivity) {
        this.context = context;
        this.mainActivity = mainActivity;

        //Init sharedPreferences
        sharedPreferences = mainActivity.getPreferences(Context.MODE_PRIVATE);

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
     * @param mainActivity the MainActivity
     * @return the Controller instance
     */
    public static Controller getInstance(Context context, MainActivity mainActivity) {
        if (Controller.instance == null) {
            Controller.instance = new Controller(context, mainActivity);
        }

        return Controller.instance;
    }

    /**
     * This method is used to start the DatabaseHelper
     * The DatabaseHelper class handles communication with the database
     */
    private void createDatabaseHelper() {
        databaseHelper = new DatabaseHelper(context);
        try {
            databaseHelper.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
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
     * Setup sharedPreferences when the app is opened for the first time
     */
    public void setupSharedPreferences() {
        sharedPreferences = mainActivity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(Constants.SHARED_PREFERENCES_USERNAME_LOG_IN, "");
        sharedPreferencesEditor.commit();
    }

    /**
     * This function checks if the user is logged in
     * @return true if the user is logged in, false otherwise
     */
    public boolean isLogged() {
        String usernameLogIn = sharedPreferences.getString(Constants.SHARED_PREFERENCES_USERNAME_LOG_IN, "");

        return !usernameLogIn.isEmpty();
    }

    /**
     * This method is used to save the username of the user that has logged in
     * @param username the username
     */
    public void saveUsernameLoggedIn(String username) {
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(Constants.SHARED_PREFERENCES_USERNAME_LOG_IN, username);
        sharedPreferencesEditor.commit();
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
     * Get the Product with the given productID
     * @param productID the productID of the wanted product
     * @return the product with the productID or null if it doesn't exist
     */
    public Product getProductWithProductID(String productID) {
        return databaseHelper.getProductWithProductID(productID);
    }

    /**
     * Get the shopping list of the user
     * @param user the user whose shopping list we want
     * @return the shopping list of the user or null if it doesn't exist
     */
    public List<Product> getUserShoppingList(User user) {
        return databaseHelper.getUserShoppingList(user);
    }
}
