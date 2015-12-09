package barcan.virgil.com.shopassistant.frontend.regular;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.model.Constants;

public class UserLoginFragment extends Fragment {

    private Controller controller;
    private View rootView;
    private Button buttonLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("UserLoginFragment.onCreateView");
        super.onCreate(savedInstanceState);

        controller = Controller.getInstance();

        setHasOptionsMenu(true);

        rootView = inflater.inflate(R.layout.user_login, container, false);

        buttonLogin = (Button) rootView.findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        return rootView;
    }

    /**
     * This function is called when the Login button from the user login screen is pressed
     * It gets the data introduced in the fields and saves it
     */
    public void userLogin() {
        System.out.println("UserLoginFragment.userLogin");
        EditText editTextUsername = (EditText) rootView.findViewById(R.id.editTextUsername);
        EditText editTextPassword = (EditText) rootView.findViewById(R.id.editTextPassword);
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        //Start the UserMainScreenActivity activity if the user credentials are correct
        //Check if the user credentials are correct
        if (controller.existsUserInDatabase(username, password)) {
            //Save the username in sharedPreferences to know that the user was logged in successfully
            controller.saveUsernameLoggedIn(username);

            //Start the MainScreenActivity
            startMainScreenActivity();

            //Finish the app so the user can not get back to this activity
            getActivity().finish();
        }
        else {
            //Inform the user that the credentials are not correct and erase the previous credentials
            System.out.println("UserLoginFragment.userLogin: incorrect login credentials!");

            editTextUsername.setText(""); editTextPassword.setText("");
            Toast.makeText(getActivity(), Constants.INCORRECT_LOGIN_CREDENTIALS, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method is called when the user has logged in correctly; the MainScreenActivity has to be opened
     */
    private void startMainScreenActivity() {
        Intent intentMainScreen = new Intent(getActivity(), UserMainScreenActivity.class);
        //intentMainScreen.putExtra("KEY", "value");
        startActivity(intentMainScreen);

        //Finish the app so the user can not get back to this activity
        getActivity().finish();
    }
}
