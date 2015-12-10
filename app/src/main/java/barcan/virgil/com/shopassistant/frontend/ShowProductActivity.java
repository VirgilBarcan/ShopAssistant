package barcan.virgil.com.shopassistant.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.model.Constants;
import barcan.virgil.com.shopassistant.model.Product;

public class ShowProductActivity extends AppCompatActivity {

    private Controller controller;
    private Toolbar toolbar;
    private Intent intent;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller = Controller.getInstance();

        intent = getIntent();
        String productID = intent.getStringExtra(Constants.PRODUCT_ID);

        product = controller.getProductWithProductID(productID);

        //Set the product info for the given product
        setProductAttributes();
    }

    /**
     * This method is called to add all product attributes to the page
     * It adds the product's image, name, category, seller and price
     * It also searches for similar products and displays those
     */
    private void setProductAttributes() {
        //Add the product image
        setProductImage();

        //Add the product info
        setProductInfo();

        //Add the similar products
        setSimilarProductsList();
    }

    /**
     * This method is called to add the product's image to the ImageView
     */
    private void setProductImage() {
        //TODO: Get the image from the backend and then display it

        //ProofOfConcept
        ImageView imageViewProductImage = (ImageView) findViewById(R.id.imageViewProductImageLarge);
        imageViewProductImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageViewProductImage.setImageResource(R.drawable.app_logo);
    }

    /**
     * This method is called to add the product's name, category, seller and price
     */
    private void setProductInfo() {
        //TODO: Get the product info from the backend and then display it

        TextView textViewProductName = (TextView) findViewById(R.id.productName);
        textViewProductName.setText(product.getProductName());

        TextView textViewProductCategory = (TextView) findViewById(R.id.productCategory);
        textViewProductCategory.setText(product.getProductCategory().getCategoryName());

        TextView textViewProductSeller = (TextView) findViewById(R.id.productSeller);
        textViewProductSeller.setText(product.getProductSeller().getCompanyName());

        TextView textViewProductPrice = (TextView) findViewById(R.id.productPrice);
        textViewProductPrice.setText(product.getProductPrice().getPriceValue() + " " + product.getProductPrice().getPriceCurrency());
    }

    /**
     * This method is used to add the similar products list
     * It gets the similar products from the backend and displays them
     */
    private void setSimilarProductsList() {
        //TODO: Get the list of similar products from the backend and then display it
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //TODO: React to settings

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}