package barcan.virgil.com.shopassistant.frontend.regular;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.frontend.customview.CategoryButton;
import barcan.virgil.com.shopassistant.frontend.customview.ProductButton;
import barcan.virgil.com.shopassistant.model.Category;
import barcan.virgil.com.shopassistant.model.Constants;
import barcan.virgil.com.shopassistant.model.Product;

/**
 * Created by virgil on 08.12.2015.
 */
public class UserLoggedHomeFragment extends Fragment {

    private Controller controller;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_logged_home_fragment, container, false);

        controller = Controller.getInstance();

        initFavouriteCategoriesPreview();

        initShoppingListPreview();

        return view;
    }

    /**
     * Initialize the preview of favourite categories based on the user shopping list
     */
    private void initFavouriteCategoriesPreview() {
        List<Product> shoppingList = controller.getUserShoppingList(controller.getConnectedUser());
        List<Category> favouriteCategories = new ArrayList<>();

        for (Product product : shoppingList) {
            if (!favouriteCategories.contains(product.getProductCategory()))
                favouriteCategories.add(product.getProductCategory());
        }

        LinearLayout linearLayoutFavouriteCategories = (LinearLayout) view.findViewById(R.id.linearLayoutFavouriteCategories);

        for (Category category : favouriteCategories) {
            System.out.println("UserLoggedHomeFragment.initFavouriteCategoriesPreview: category=" + category);
            CategoryButton categoryButton = new CategoryButton(getActivity().getApplicationContext());
            categoryButton.setImageSrc(R.mipmap.product_image); //TODO: User the real image
            categoryButton.setCategory(category);
            categoryButton.setOnClickListener(getCategoryButtonOnClickListener());

            linearLayoutFavouriteCategories.addView(categoryButton);
        }
    }

    /**
     * Initialize the preview of the user shopping list
     */
    private void initShoppingListPreview() {
        List<Product> shoppingList = controller.getUserShoppingList(controller.getConnectedUser());

        LinearLayout linearLayoutShoppingList = (LinearLayout) view.findViewById(R.id.linearLayoutShoppingList);

        for (Product product : shoppingList) {
            System.out.println("UserLoggedHomeFragment.initShoppingListPreview: product=" + product);
            ProductButton productButton = new ProductButton(getActivity().getApplicationContext());
            productButton.setImageSrc(R.mipmap.product_image); //TODO: Use the real image
            productButton.setProduct(product);
            productButton.setOnClickListener(getProductButtonOnClickListener());

            linearLayoutShoppingList.addView(productButton);
        }
    }

    private View.OnClickListener getCategoryButtonOnClickListener() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CategoryButton categoryButton = (CategoryButton) view;
                System.out.println("UserLoggedHomeFragment.onClick: " + categoryButton.getCategory().getCategoryName());
            }
        };
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
                System.out.println("UserLoggedHomeFragment.onClick: " + productButton.getProduct().getProductName());

                openProductFragment(productButton.getProduct());
            }
        };
    }

    private void openProductFragment(Product product) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PRODUCT_ID, product.getProductID());

        UserShowProductFragment fragmentShowProduct = new UserShowProductFragment();
        fragmentShowProduct.setArguments(bundle);
        FragmentTransaction fragmentTransactionHome = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransactionHome.replace(R.id.frame, fragmentShowProduct);
        fragmentTransactionHome.addToBackStack("Product");
        fragmentTransactionHome.commit();
    }

}
