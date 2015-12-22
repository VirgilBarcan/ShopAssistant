package barcan.virgil.com.shopassistant.frontend.regular;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.backend.ProductsListViewAdapter;
import barcan.virgil.com.shopassistant.model.Constants;
import barcan.virgil.com.shopassistant.model.Product;

/**
 * Created by virgil on 08.12.2015.
 */
public class UserProductsFragment extends Fragment {

    private Controller controller;
    private Bundle bundle;
    private View view;
    private ListView listViewProductsList;
    private List<Product> productList;

    private String categoryID;
    private String companyID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_logged_products, container, false);

        controller = Controller.getInstance();

        bundle = getArguments();
        categoryID = "";
        companyID = "";

        String whatToGet = "ALL";

        if (bundle != null  && bundle.containsKey(Constants.CATEGORY_ID)) {
            categoryID = bundle.getString(Constants.CATEGORY_ID);
            whatToGet = "CATEGORY";
        }
        else {
            if (bundle != null && bundle.containsKey(Constants.COMPANY_ID)) {
                companyID = bundle.getString(Constants.COMPANY_ID);
                whatToGet = "COMPANY";
            }
        }

        populateProductsList(whatToGet);

        return view;
    }

    /**
     * This method is used to fill the products list
     * It gets all products from the Controller
     * For the clicked product it starts the UserShowProductFragment
     */
    private void populateProductsList(String whatToGet) {
        listViewProductsList = (ListView) view.findViewById(R.id.listViewProductsList);

        if (whatToGet.equals("ALL")) {
            //Get all products list
            productList = controller.getAllProducts();
        }
        if (whatToGet.equals("COMPANY")) {
            //Get all products sold by the company
            productList = controller.getAllProductsSoldBy(companyID);
        }
        if (whatToGet.equals("CATEGORY")) {
            //Get all products in the given category
            productList = controller.getAllProductsInCategory(categoryID);
        }

        sortProductsByName();

        ProductsListViewAdapter productsListViewAdapter = new ProductsListViewAdapter(getActivity(), productList);
        listViewProductsList.setAdapter(productsListViewAdapter);

        listViewProductsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Product product = (Product) parent.getItemAtPosition(position);

                openShowProductFragment(product);
            }
        });
    }

    /**
     * Sort the products by name
     */
    private void sortProductsByName() {
        Collections.sort(productList, new Comparator<Product>() {
            @Override
            public int compare(Product lhs, Product rhs) {
                return lhs.getProductName().compareTo(rhs.getProductName());
            }
        });
    }

    /**
     * This method is used to start the UserShowProductFragment for the selected product
     * @param product the selected product
     */
    private void openShowProductFragment(Product product) {
        System.out.println("UserProductsFragment.openShowProductFragment");

        Bundle bundle = new Bundle();
        bundle.putString(Constants.PRODUCT_ID, product.getProductID());

        UserShowProductFragment fragmentShowProduct = new UserShowProductFragment();
        fragmentShowProduct.setArguments(bundle);
        FragmentTransaction fragmentTransactionHome = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransactionHome.replace(R.id.frame, fragmentShowProduct);
        fragmentTransactionHome.addToBackStack("Products");
        fragmentTransactionHome.commit();
    }
}
