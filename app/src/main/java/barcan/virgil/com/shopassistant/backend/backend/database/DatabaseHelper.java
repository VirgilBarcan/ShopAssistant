package barcan.virgil.com.shopassistant.backend.backend.database;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.model.Category;
import barcan.virgil.com.shopassistant.model.Company;
import barcan.virgil.com.shopassistant.model.CompanyUser;
import barcan.virgil.com.shopassistant.model.RegularUser;

/**
 * Created by virgil on 30.11.2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DB_PATH = "/data/data/barcan.virgil.com.shopassistant/databases/";
    public static String DB_NAME = "shopping_assistant.sqlite";
    public static final int DB_VERSION = 1;

    private SQLiteDatabase myDB;
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO: Add code
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: Add code
    }

    @Override
    public synchronized void close(){
        if(myDB != null){
            myDB.close();
        }
        super.close();
    }

    /***
     * Open database
     * @throws SQLException
     */
    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /***
     * Copy database from res/raw to the device internal storage
     * @throws IOException
     */
    public void copyDataBase() throws IOException {
        try {
            Resources resources = context.getResources();
            InputStream myInput = resources.openRawResource(R.raw.shopping_assistant);

            //InputStream myInput = context.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH + DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;

            while((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
            Log.e("ShopAssist-copyDatabase", e.getMessage());
        }

    }

    /***
     * Check if the database doesn't exist on device, create new one
     * @throws IOException
     */
    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();

        if (dbExist) {
            //Do nothing, we already have the database
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e("ShopAssist-create", e.getMessage());
            }
        }
    }

    /***
     * Check if the database is exist on device or not
     * @return true if the database exists, false otherwise
     */
    private boolean checkDatabase() {
        SQLiteDatabase tempDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            tempDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            Log.e("ShopAssist-check", e.getMessage());
        }
        if (tempDB != null)
            tempDB.close();
        return tempDB != null;
    }

    /**
     * Get the list of all RegularUsers from the database
     * @return the list of all RegularUsers from the database
     */
    public List<RegularUser> getAllRegularUsers(){
        List<RegularUser> regularUsers = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TablesContracts.RegularUser.TABLE_NAME, null);

            if(cursor == null) return null;

            String regularUserID;
            String username;
            String password;
            String fullName;
            String pathToImage;

            cursor.moveToFirst();
            do {
                regularUserID = cursor.getString(0);
                username = cursor.getString(1);
                password = cursor.getString(2);
                pathToImage = cursor.getString(3);
                fullName = cursor.getString(4);

                RegularUser regularUser = new RegularUser();
                regularUser.setUserID(Integer.parseInt(regularUserID));
                regularUser.setUsername(username);
                regularUser.setPassword(password);
                regularUser.setFullName(fullName);
                regularUser.setPathToImage(pathToImage);

                regularUsers.add(regularUser);
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            Log.e("ShopAssist", e.getMessage());
        }

        db.close();

        return regularUsers;
    }

    /**
     * Get the list of all CompanyUsers from the database
     * @return the list of all CompanyUsers from the database
     */
    public List<CompanyUser> getAllCompanyUsers(){
        List<CompanyUser> companyUsers = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TablesContracts.CompanyUser.TABLE_NAME, null);

            if(cursor == null) return null;

            String companyUserID;
            String username;
            String password;
            String fullName;
            String pathToImage;

            cursor.moveToFirst();
            do {
                companyUserID = cursor.getString(0);
                username = cursor.getString(1);
                password = cursor.getString(2);
                pathToImage = cursor.getString(3);
                fullName = cursor.getString(4);

                CompanyUser companyUser = new CompanyUser();
                companyUser.setUserID(Integer.parseInt(companyUserID));
                companyUser.setUsername(username);
                companyUser.setPassword(password);
                companyUser.setFullName(fullName);
                companyUser.setPathToImage(pathToImage);

                companyUsers.add(companyUser);
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            Log.e("ShopAssist", e.getMessage());
        }

        db.close();

        return companyUsers;
    }

    /**
     * Get the list of all Categories from the database
     * @return the list of all Categories from the database
     */
    public List<Category> getAllCategories(){
        List<Category> categories = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TablesContracts.Category.TABLE_NAME, null);

            if(cursor == null) return null;

            String categoryID;
            String categoryName;

            cursor.moveToFirst();
            do {

                categoryID = cursor.getString(0);
                categoryName = cursor.getString(1);

                Category category = new Category();
                category.setCategoryID(categoryID);
                category.setCategoryName(categoryName);

                categories.add(category);
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            Log.e("ShopAssist", e.getMessage());
        }

        db.close();

        return categories;
    }

    /**
     * Get the list of all Companies from the database
     * @return the list of all Companies from the database
     */
    public List<Company> getAllCompanies(){
        List<Company> companies = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        try {
            cursor = db.rawQuery("SELECT * FROM " + TablesContracts.Company.TABLE_NAME, null);

            if(cursor == null) return null;

            String companyID;
            String companyName;
            String companyInfo;
            String companyRating;

            cursor.moveToFirst();
            do {

                companyID = cursor.getString(0);
                companyName = cursor.getString(1);
                companyInfo = cursor.getString(2);
                companyRating = cursor.getString(3);

                Company company = new Company();
                company.setCompanyID(companyID);
                company.setCompanyName(companyName);
                company.setCompanyInfo(companyInfo);
                company.setCompanyRating(Double.parseDouble(companyRating));

                companies.add(company);
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            Log.e("ShopAssist", e.getMessage());
        }

        db.close();

        return companies;
    }

    /**
     * Get the list of all CompanyUsers that work for a given Company from the database
     * @return the list of all CompanyUsers that work for a given Company from the database
     */
    public List<CompanyUser> getAllUsersOfCompany(Company company) {
        return this.getAllUsersOfCompany(company.getCompanyName());
    }

    /**
     * Get the list of all CompanyUsers that work for a given Company from the database
     * @return the list of all CompanyUsers that work for a given Company from the database
     */
    public List<CompanyUser> getAllUsersOfCompany(String companyName){
        List<CompanyUser> companyUsers = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;

        try {
            String sqlQuery = "SELECT CU.companyUserID, CU.username, CU.password, CU.pathToImage, CU.fullName " +
                    "FROM " + TablesContracts.CompanyUser.TABLE_NAME + " CU, " +
                    TablesContracts.CompanyCompanyUser.TABLE_NAME + " CCU, " +
                    TablesContracts.Company.TABLE_NAME + " C " +
                    "WHERE CCU.companyUserID = CU.companyUserID " +
                    "AND CCU.companyID = C.companyID " +
                    "AND C.companyName = '" + companyName + "'";
            cursor = db.rawQuery(sqlQuery, null);

            if(cursor == null) return null;

            String companyUserID;
            String username;
            String password;
            String fullName;
            String pathToImage;

            cursor.moveToFirst();
            do {
                companyUserID = cursor.getString(0);
                username = cursor.getString(1);
                password = cursor.getString(2);
                pathToImage = cursor.getString(3);
                fullName = cursor.getString(4);

                CompanyUser companyUser = new CompanyUser();
                companyUser.setUserID(Integer.parseInt(companyUserID));
                companyUser.setUsername(username);
                companyUser.setPassword(password);
                companyUser.setFullName(fullName);
                companyUser.setPathToImage(pathToImage);

                companyUsers.add(companyUser);
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            Log.e("ShopAssist", e.getMessage());
        }

        db.close();

        return companyUsers;
    }
}
