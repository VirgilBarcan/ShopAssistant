package barcan.virgil.com.shopassistant.frontend.regular;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.frontend.MainActivity;
import barcan.virgil.com.shopassistant.model.User;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by virgil on 08.12.2015.
 */
public class UserLoggedAccountFragment extends Fragment {

    private Controller controller;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //TODO: Add new layout
        view = inflater.inflate(R.layout.user_account, container, false);

        controller = Controller.getInstance();

        //Get information to show: User info: full name, picture
        addUserInfo();

        //Add buttons listeners
        addButtonsListeners();

        return view;
    }

    /**
     * This method adds information about the user: full name and image
     */
    private void addUserInfo() {
        User connectedUser = controller.getConnectedUser();

        System.out.println("UserLoggedAccountFragment.addUserInfo: connectedUser=" + connectedUser);

        if (connectedUser != null) {
            TextView textViewFullName = (TextView) view.findViewById(R.id.textViewNameSurname);
            textViewFullName.setText(connectedUser.getFullName());

            //TODO: Add user image
            CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.circularImageView);
            circleImageView.setImageResource(R.drawable.ic_account_circle);
        }
        else {
            System.out.println("UserLoggedAccountFragment.addUserInfo: ERROR! No user is connected! This should not be possible!");
        }

    }

    /**
     * This method adds listeners for all buttons
     */
    private void addButtonsListeners() {
        //Logout button
        Button buttonLogout = (Button) view.findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(buttonLogoutClickListener());

        //TODO: ChangePassword button + the others
    }

    /**
     * This function returns the ClickListener for the Logout button
     * @return the ClickListener for the Logout button
     */
    private View.OnClickListener buttonLogoutClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //The user has to be disconnected: update Controller data and open the MainActivity
                controller.logoutUser();

                Intent intentStartMainActivity = new Intent(getActivity(), MainActivity.class);
                intentStartMainActivity.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intentStartMainActivity);

                //Finish the activity to make it impossible for the user to come back here
                getActivity().finish();
            }
        };
    }

}
