package barcan.virgil.com.shopassistant.frontend.regular;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;

/**
 * Created by virgil on 08.12.2015.
 */
public class UserLoggedNotificationsFragment extends Fragment {

    private Controller controller;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //TODO: Add new layout
        view = inflater.inflate(R.layout.user_logged_notifications, container, false);

        controller = Controller.getInstance();

        return view;
    }

}
