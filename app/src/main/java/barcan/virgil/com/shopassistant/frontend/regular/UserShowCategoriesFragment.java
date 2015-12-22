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
import barcan.virgil.com.shopassistant.backend.CategoriesListViewAdapter;
import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.model.Category;
import barcan.virgil.com.shopassistant.model.Constants;

/**
 * Created by virgil on 22.12.2015.
 */
public class UserShowCategoriesFragment extends Fragment {

    private Controller controller;
    private View view;
    private ListView listViewCategoriesList;

    private List<Category> categoriesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_logged_show_categories, container, false);

        controller = Controller.getInstance();

        populateCategoriesList();

        return view;
    }

    /**
     * This method is used to fill the categories list
     * It gets all categories from the Controller
     */
    private void populateCategoriesList() {
        listViewCategoriesList = (ListView) view.findViewById(R.id.listViewCategoriesList);

        //Get the user's shopping list
        categoriesList = controller.getAllCategories();

        sortCategoriesByName();

        CategoriesListViewAdapter categoriesListViewAdapter = new CategoriesListViewAdapter(getActivity(), categoriesList);
        listViewCategoriesList.setAdapter(categoriesListViewAdapter);

        listViewCategoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = (Category) parent.getItemAtPosition(position);

                //Start the fragment that shows all products with a category
                startProductsFragment(category);
            }
        });
    }

    /**
     * Sort categories by name
     */
    private void sortCategoriesByName() {
        Collections.sort(categoriesList, new Comparator<Category>() {
            @Override
            public int compare(Category lhs, Category rhs) {
                return lhs.getCategoryName().compareTo(rhs.getCategoryName());
            }
        });
    }


    /**
     * This method starts the Products fragment when the user clicks on a Category from the list
     */
    private void startProductsFragment(Category category) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.CATEGORY_ID, category.getCategoryID());

        UserProductsFragment fragmentProducts = new UserProductsFragment();
        fragmentProducts.setArguments(bundle);
        FragmentTransaction fragmentTransactionHome = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransactionHome.replace(R.id.frame, fragmentProducts);
        fragmentTransactionHome.addToBackStack("Products");
        fragmentTransactionHome.commit();
    }

}
