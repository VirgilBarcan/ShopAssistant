package barcan.virgil.com.shopassistant.frontend.regular;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import barcan.virgil.com.shopassistant.R;

/**
 * Created by virgil on 08.12.2015.
 */
public class UserLoggedHomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_logged_home_fragment, container, false);
        return view;
    }

}
