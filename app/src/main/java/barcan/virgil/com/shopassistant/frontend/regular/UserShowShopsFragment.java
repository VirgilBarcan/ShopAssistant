package barcan.virgil.com.shopassistant.frontend.regular;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.backend.ShopsListViewAdapter;
import barcan.virgil.com.shopassistant.model.Category;
import barcan.virgil.com.shopassistant.model.Company;
import barcan.virgil.com.shopassistant.model.Constants;

/**
 * Created by virgil on 22.12.2015.
 */
public class UserShowShopsFragment extends Fragment {

    private Controller controller;
    private View view;
    private ListView listViewCategoriesList;

    private List<Company> companiesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_logged_show_shops, container, false);

        controller = Controller.getInstance();

        populateShopsList();

        return view;
    }

    /**
     * This method is used to fill the shops list
     * It gets all shops from the Controller
     */
    private void populateShopsList() {
        listViewCategoriesList = (ListView) view.findViewById(R.id.listViewShopsList);

        //Get the user's shopping list
        companiesList = controller.getAllCompanies();

        ShopsListViewAdapter shopsListViewAdapter = new ShopsListViewAdapter(getActivity(), companiesList);
        listViewCategoriesList.setAdapter(shopsListViewAdapter);

        listViewCategoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Company company = (Company) parent.getItemAtPosition(position);

                //Start the fragment that shows all products sold by a shop
                startProductsFragment(company);
            }
        });
    }

    /**
     * This method starts the Products fragment when the user clicks on a Company from the list
     */
    private void startProductsFragment(Company company) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.COMPANY_ID, company.getCompanyID());

        UserProductsFragment fragmentProducts = new UserProductsFragment();
        fragmentProducts.setArguments(bundle);
        FragmentTransaction fragmentTransactionHome = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransactionHome.replace(R.id.frame, fragmentProducts);
        fragmentTransactionHome.addToBackStack("Categories");
        fragmentTransactionHome.commit();
    }

}
