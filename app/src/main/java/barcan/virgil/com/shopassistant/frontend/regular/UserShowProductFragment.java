package barcan.virgil.com.shopassistant.frontend.regular;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.frontend.customview.ProductButton;
import barcan.virgil.com.shopassistant.model.Constants;
import barcan.virgil.com.shopassistant.model.Product;

/**
 * Created by virgil on 08.12.2015.
 */
public class UserShowProductFragment extends Fragment {

    private Controller controller;
    private Bundle bundle;
    private View view;
    private Product product;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.product_fragment, container, false);

        controller = Controller.getInstance();

        bundle = getArguments();
        String productID = "";

        if (bundle != null) {
            productID = bundle.getString(Constants.PRODUCT_ID);
        }

        product = controller.getProductWithProductID(productID);

        //Set the product info for the given product
        setProductAttributes();

        return view;
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
        ImageView imageViewProductImage = (ImageView) view.findViewById(R.id.imageViewProductImageLarge);
        imageViewProductImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageViewProductImage.setImageResource(R.drawable.app_logo);
    }

    /**
     * This method is called to add the product's name, category, seller and price
     */
    private void setProductInfo() {
        //TODO: Get the product info from the backend and then display it

        TextView textViewProductName = (TextView) view.findViewById(R.id.productName);
        textViewProductName.setText(product.getProductName());

        TextView textViewProductCategory = (TextView) view.findViewById(R.id.productCategory);
        textViewProductCategory.setText(product.getProductCategory().getCategoryName());

        TextView textViewProductSeller = (TextView) view.findViewById(R.id.productSeller);
        textViewProductSeller.setText(product.getProductSeller().getCompanyName());

        TextView textViewProductPrice = (TextView) view.findViewById(R.id.productPrice);
        textViewProductPrice.setText(product.getProductPrice().getPriceValue() + " " + product.getProductPrice().getPriceCurrency());
    }

    /**
     * This method is used to add the similar products list
     * It gets the similar products from the backend and displays them
     */
    private void setSimilarProductsList() {
        //TODO: Get the list of similar products from the backend and then display it
        List<Product> shoppingList = controller.getUserShoppingList(controller.getConnectedUser());

        LinearLayout linearLayoutSimilarProducts = (LinearLayout) view.findViewById(R.id.linearLayoutSimilarProductsList);

        for (Product product : shoppingList) {
            System.out.println("UserShowProductFragment.setSimilarProductsList: product=" + product);
            ProductButton productButton = new ProductButton(getActivity().getApplicationContext());
            productButton.setImageSrc(R.mipmap.product_image); //TODO: Use the real image
            productButton.setProduct(product);
            productButton.setOnClickListener(getProductButtonOnClickListener());

            linearLayoutSimilarProducts.addView(productButton);
        }
    }

    /**
     * Get the ProductButton OnClickListener
     * @return the OnClickListener for the ProductButton
     */
    private View.OnClickListener getProductButtonOnClickListener() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ProductButton productButton = (ProductButton) view;
                System.out.println("UserShowProductFragment.onClick: " + productButton.getProduct().getProductName());

                openProductFragment(productButton.getProduct());
            }
        };
    }

    /**
     * Open the fragment where info about the product are shown
     * @param product the product whose info will be shown
     */
    private void openProductFragment(Product product) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PRODUCT_ID, product.getProductID());

        UserShowProductFragment fragmentShowProduct = new UserShowProductFragment();
        fragmentShowProduct.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragmentShowProduct);
        fragmentTransaction.addToBackStack("Product");
        fragmentTransaction.commit();
    }

}
