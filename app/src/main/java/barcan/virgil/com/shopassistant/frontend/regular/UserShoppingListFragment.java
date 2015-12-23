package barcan.virgil.com.shopassistant.frontend.regular;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
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
public class UserShoppingListFragment extends Fragment {

    private Controller controller;
    private View view;
    private ListView listViewShoppingList;
    private List<Product> productList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_shopping_list_fragment, container, false);

        controller = Controller.getInstance();

        //Get the shopping list from the Controller and populate the view
        populateShoppingList();

        //Register listView for context menu
        registerForContextMenu(listViewShoppingList);

        return view;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater contextMenuInflater = getActivity().getMenuInflater();
        contextMenuInflater.inflate(R.menu.context_menu_shopping_list, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.delete_product:
                System.out.println("Delete city: " + info.id);
                //deleteCity(info.id);

                showDeleteProduct().show();

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * The DeleteProduct AlertDialog
     * @return the DeleteProduct AlertDialog
     */
    private AlertDialog showDeleteProduct() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setMessage(R.string.delete_product_from_shopping_list)
                .setPositiveButton(R.string.delete_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("YES");
                    }
                })
                .setNegativeButton(R.string.delete_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("NO");
                    }
                });

        return alertDialogBuilder.create();
    }

    /**
     * This method is used to fill the shopping list
     * It gets all products wanted by the user from the Controller
     * For the clicked product it starts the ShowProductActivity
     */
    private void populateShoppingList() {
        listViewShoppingList = (ListView) view.findViewById(R.id.listViewShoppingList);

        //Get the user's shopping list
        productList = controller.getUserShoppingList(controller.getConnectedUser());

        sortProductsByName();

        ProductsListViewAdapter shoppingListViewAdapter = new ProductsListViewAdapter(getActivity(), productList);
        listViewShoppingList.setAdapter(shoppingListViewAdapter);

        listViewShoppingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        System.out.println("UserShoppingListActivity.openShowProductFragment");

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
