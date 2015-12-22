package barcan.virgil.com.shopassistant.frontend.regular;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.backend.ShopsListViewAdapter;
import barcan.virgil.com.shopassistant.model.Company;

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

        populateCategoriesList();

        return view;
    }

    /**
     * This method is used to fill the shops list
     * It gets all shops from the Controller
     */
    private void populateCategoriesList() {
        listViewCategoriesList = (ListView) view.findViewById(R.id.listViewShopsList);

        //Get the user's shopping list
        companiesList = controller.getAllCompanies();

        ShopsListViewAdapter shopsListViewAdapter = new ShopsListViewAdapter(getActivity(), companiesList);
        listViewCategoriesList.setAdapter(shopsListViewAdapter);

        listViewCategoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //TODO: Start the fragment that shows all products sold by a shop
            }
        });
    }

}