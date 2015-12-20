package barcan.virgil.com.shopassistant.frontend.regular;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;

import java.util.ArrayList;
import java.util.List;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.frontend.customview.CategoryButton;
import barcan.virgil.com.shopassistant.frontend.customview.ProductButton;
import barcan.virgil.com.shopassistant.model.Category;
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
            categoryButton.setCategoryNameString(category.getCategoryName());

            linearLayoutFavouriteCategories.addView(categoryButton);
        }
    }

    private void initShoppingListPreview() {
        List<Product> shoppingList = controller.getUserShoppingList(controller.getConnectedUser());

        LinearLayout linearLayoutShoppingList = (LinearLayout) view.findViewById(R.id.linearLayoutShoppingList);

        for (Product product : shoppingList) {
            System.out.println("UserLoggedHomeFragment.initShoppingListPreview: product=" + product);
            ProductButton productButton = new ProductButton(getActivity().getApplicationContext());
            productButton.setImageSrc(R.mipmap.product_image); //TODO: Use the real image
            productButton.setProductNameString(product.getProductName());
            productButton.setProductSellerString(product.getProductSeller().getCompanyName());
            productButton.setProductPriceString(product.getProductPriceString());

            linearLayoutShoppingList.addView(productButton);
        }
    }

}
