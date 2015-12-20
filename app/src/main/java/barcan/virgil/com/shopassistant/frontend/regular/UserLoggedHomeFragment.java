package barcan.virgil.com.shopassistant.frontend.regular;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.frontend.ProductButton;

/**
 * Created by virgil on 08.12.2015.
 */
public class UserLoggedHomeFragment extends Fragment {

    private Controller controller;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_logged_home_fragment, container, false);

        ProductButton productButton = (ProductButton) view.findViewById(R.id.productButton1);
        productButton.setProductNameString("Iphone");
        productButton.setProductSellerString("Altex");
        productButton.setProductPriceString("3399.99 Lei");

        controller = Controller.getInstance();

        return view;
    }

}
