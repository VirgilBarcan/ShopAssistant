package barcan.virgil.com.shopassistant.backend.backend.database;

import android.provider.BaseColumns;

/**
 * This class contains constants referring to all tables in the Database
 * Each sub-class represents a table from the database and the String constants are the column names
 * Created by virgil on 30.11.2015.
 */
public class TablesContracts {

    public static abstract class Category implements BaseColumns {
        public static final String TABLE_NAME = "CATEGORY";
        public static final String COLUMN_NAME_CATEGORY_ID = "categoryID";
        public static final String COLUMN_NAME_CATEGORY_NAME = "categoryName";
    }

    public static abstract class Company implements BaseColumns {
        public static final String TABLE_NAME = "COMPANY";
        public static final String COLUMN_NAME_COMPANY_ID = "companyID";
        public static final String COLUMN_NAME_COMPANY_NAME = "companyName";
        public static final String COLUMN_NAME_COMPANY_INFO = "companyInfo";
        public static final String COLUMN_NAME_COMPANY_RATING = "companyRating";
    }

    public static abstract class CompanyCompanyUser implements BaseColumns {
        public static final String TABLE_NAME = "COMPANY_COMPANY_USER";
        public static final String COLUMN_NAME_COMPANY_ID = "companyID";
        public static final String COLUMN_NAME_COMPANY_USER_ID = "companyUserID";
        public static final String COLUMN_NAME_LOCATION_ID = "locationID";
    }

    public static abstract class CompanyUser implements BaseColumns {
        public static final String TABLE_NAME = "COMPANY_USER";
        public static final String COLUMN_NAME_COMPANY_USER_ID = "companyUserID";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_PATH_TO_IMAGE = "pathToImage";
    }

    public static abstract class Location implements BaseColumns {
        public static final String TABLE_NAME = "LOCATION";
        public static final String COLUMN_NAME_LOCATION_ID = "locationID";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_COUNTRY_NAME = "countryName";
        public static final String COLUMN_NAME_CITY_NAME = "cityName";
        public static final String COLUMN_NAME_STREET_NAME = "streetName";
        public static final String COLUMN_NAME_STREET_NO = "streetNo";
        public static final String COLUMN_NAME_ZIP = "ZIP";
        public static final String COLUMN_NAME_FULL_ADDRESS = "fullAddress";
    }

    public static abstract class PreferredCategory implements BaseColumns {
        public static final String TABLE_NAME = "PREFERRED_CATEGORY";
        public static final String COLUMN_NAME_REGULAR_USER_ID = "regularUserID";
        public static final String COLUMN_NAME_CATEGORY_ID = "categoryID";
    }

    public static abstract class PreferredCompany implements BaseColumns {
        public static final String TABLE_NAME = "PREFERRED_COMPANY";
        public static final String COLUMN_NAME_REGULAR_USER_ID = "regularUserID";
        public static final String COLUMN_NAME_COMPANY_ID = "companyID";
    }

    public static abstract class Price implements BaseColumns {
        public static final String TABLE_NAME = "PRICE";
        public static final String COLUMN_NAME_PRICE_ID = "priceID";
        public static final String COLUMN_NAME_PRICE_VALUE = "priceValue";
        public static final String COLUMN_NAME_PRICE_CURRENCY = "priceCurrency";
    }

    public static abstract class Product implements BaseColumns {
        public static final String TABLE_NAME = "PRODUCT";
        public static final String COLUMN_NAME_PRODUCT_ID = "productID";
        public static final String COLUMN_NAME_PRODUCT_NAME = "productName";
        public static final String COLUMN_NAME_PRODUCT_CATEGORY = "productCategory";
        public static final String COLUMN_NAME_PRODUCT_RATING = "productRating";
    }

    public static abstract class ProductCompany implements BaseColumns {
        public static final String TABLE_NAME = "PRODUCT_COMPANY";
        public static final String COLUMN_NAME_PRODUCT_ID = "productID";
        public static final String COLUMN_NAME_COMPANY_ID = "companyID";
        public static final String COLUMN_NAME_PRODUCT_PRICE_ID = "productPriceID";
    }

    public static abstract class RegularUser implements BaseColumns {
        public static final String TABLE_NAME = "REGULAR_USER";
        public static final String COLUMN_NAME_REGULAR_USER_ID = "regularUserID";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_PATH_TO_IMAGE = "pathToImage";
    }

    public static abstract class ShoppingList implements BaseColumns {
        public static final String TABLE_NAME = "SHOPPING_LIST";
        public static final String COLUMN_NAME_REGULAR_USER_ID = "regularUserID";
        public static final String COLUMN_NAME_PRODUCT_ID = "productID";
        public static final String COLUMN_NAME_COMPANY_ID = "companyID";
    }

}
