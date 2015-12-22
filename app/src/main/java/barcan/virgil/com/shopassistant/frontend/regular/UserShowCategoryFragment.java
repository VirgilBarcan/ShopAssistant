package barcan.virgil.com.shopassistant.frontend.regular;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.backend.ProductsListViewAdapter;
import barcan.virgil.com.shopassistant.model.Category;
import barcan.virgil.com.shopassistant.model.Constants;
import barcan.virgil.com.shopassistant.model.Product;

/**
 * Created by virgil on 08.12.2015.
 */
public class UserShowCategoryFragment extends Fragment {

    private Controller controller;
    private Bundle bundle;
    private View view;
    private ListView listViewProductsInCategory;
    private Category category;
    private List<Product> productList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_category_fragment, container, false);

        controller = Controller.getInstance();

        bundle = getArguments();
        String categoryID = "";

        if (bundle != null) {
            categoryID = bundle.getString(Constants.CATEGORY_ID);
        }

        category = controller.getCategoryWithCategoryID(categoryID);

        productList = new ArrayList<>();

        //TODO: Get the products in the category list from the Controller
        populateProductsInCategoryList();

        return view;
    }

    /**
     * This method is used to fill the products list
     * It gets all products that are in the Category from the Controller
     * For the clicked product it starts the ShowProductActivity
     */
    private void populateProductsInCategoryList() {
        listViewProductsInCategory = (ListView) view.findViewById(R.id.listViewProductsInCategory);

        //Get the products list
        List<Product> allProducts = controller.getAllProducts();

        for (Product product : allProducts) {
            if (product.getProductCategory().getCategoryID().equals(category.getCategoryID()))
                productList.add(product);
        }

        ProductsListViewAdapter shoppingListViewAdapter = new ProductsListViewAdapter(getActivity(), productList);
        listViewProductsInCategory.setAdapter(shoppingListViewAdapter);

        listViewProductsInCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Product product = (Product) parent.getItemAtPosition(position);

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

        //TODO: Use a fragment instead of an activity to have access to the NavigationView
        /*
        Intent intentShowProductActivity = new Intent(getActivity(), ShowProductActivity.class);
        intentShowProductActivity.putExtra(Constants.PRODUCT_ID, product.getProductID());
        startActivity(intentShowProductActivity);
        */

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
