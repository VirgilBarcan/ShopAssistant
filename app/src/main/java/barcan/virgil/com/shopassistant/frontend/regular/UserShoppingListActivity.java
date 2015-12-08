package barcan.virgil.com.shopassistant.frontend.regular;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.ShoppingListViewAdapter;
import barcan.virgil.com.shopassistant.frontend.ShowProductActivity;
import barcan.virgil.com.shopassistant.model.Category;
import barcan.virgil.com.shopassistant.model.Company;
import barcan.virgil.com.shopassistant.model.Price;
import barcan.virgil.com.shopassistant.model.Product;

public class UserShoppingListActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private ListView listViewShoppingList;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_shopping_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateShoppingList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void populateShoppingList() {
        listViewShoppingList = (ListView) findViewById(R.id.listViewShoppingList);

        productList = new ArrayList<>();

        //TODO: Get the list from the backend
        //ProofOfConcept
        Company companyEmag = new Company();
        companyEmag.setCompanyName("Emag");

        Company companyAltex = new Company();
        companyAltex.setCompanyName("Altex");

        Product product1 = new Product();
        product1.setProductName("LG G2");
        product1.setProductCategory(new Category("Smartphone"));
        product1.setProductSeller(companyEmag);
        product1.setProductPrice(new Price(1150.0, "Lei"));

        Product product2 = new Product();
        product2.setProductName("Toshiba Satellite C55A-16D");
        product2.setProductCategory(new Category("Laptop"));
        product2.setProductSeller(companyEmag);
        product2.setProductPrice(new Price(2250.0, "Lei"));

        Product product3 = new Product();
        product3.setProductName("Asus Mouse");
        product3.setProductCategory(new Category("Mouse"));
        product3.setProductSeller(companyAltex);
        product3.setProductPrice(new Price(50.0, "Lei"));

        Product product4 = new Product();
        product4.setProductName("Kindle Paperwhite 4GB");
        product4.setProductCategory(new Category("E-Book Reader"));
        product4.setProductSeller(companyAltex);
        product4.setProductPrice(new Price(400.0, "Lei"));

        Product product5 = new Product();
        product5.setProductName("Samsung S6 Edge");
        product5.setProductCategory(new Category("Smartphone"));
        product5.setProductSeller(companyAltex);
        product5.setProductPrice(new Price(3400.0, "Lei"));

        Product product6 = new Product();
        product6.setProductName("Iphone 6S");
        product6.setProductCategory(new Category("Smartphone"));
        product6.setProductSeller(companyEmag);
        product6.setProductPrice(new Price(3400.0, "Lei"));

        Product product7 = new Product();
        product7.setProductName("Apple Macbook Air 13.3");
        product7.setProductCategory(new Category("Laptop"));
        product7.setProductSeller(companyEmag);
        product7.setProductPrice(new Price(8400.0, "Lei"));

        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        productList.add(product5);
        productList.add(product6);
        productList.add(product7);

        ShoppingListViewAdapter shoppingListViewAdapter = new ShoppingListViewAdapter(this, productList);
        listViewShoppingList.setAdapter(shoppingListViewAdapter);

        listViewShoppingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Product product = (Product) parent.getItemAtPosition(position);

                //TODO: Decide what to do when the user clicks a product; possibly start a new activity and show more info about product
                startShowProductActivity(product);
            }
        });
    }

    /**
     * This method is used to start the ShowProductActivity for the selected product
     * @param product the selected product
     */
    private void startShowProductActivity(Product product) {
        System.out.println("UserShoppingListActivity.startShowProductActivity");

        //TOTO: Send the product id or something
        Intent intentShowProductActivity = new Intent(UserShoppingListActivity.this, ShowProductActivity.class);
        //intent.showProductActivity.putExtra("KEY", "value");
        startActivity(intentShowProductActivity);
    }

}

