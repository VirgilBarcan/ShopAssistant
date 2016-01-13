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
import barcan.virgil.com.shopassistant.model.RegularUser;

public class UserRegisterFragment extends Fragment {

    private Controller controller;
    private View rootView;
    private Button buttonRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("UserRegisterFragment.onCreateView");
        super.onCreate(savedInstanceState);

        controller = Controller.getInstance();

        setHasOptionsMenu(true);

        rootView = inflater.inflate(R.layout.user_register, container, false);

        buttonRegister = (Button) rootView.findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegister();
            }
        });

        return rootView;
    }

    /**
     * This function is called when the Register button from the user login screen is pressed
     * It gets the data introduced in the fields and saves it
     */
    public void userRegister() {
        System.out.println("UserRegisterFragment.userRegister");
        EditText editTextFullName = (EditText) rootView.findViewById(R.id.editTextFullName);
        EditText editTextUsername = (EditText) rootView.findViewById(R.id.editTextUsername);
        EditText editTextPassword = (EditText) rootView.findViewById(R.id.editTextPassword);
        EditText editTextConfirmPassword = (EditText) rootView.findViewById(R.id.editTextConfirmPassword);
        String fullName = editTextFullName.getText().toString();
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        String passwordConfirm = editTextConfirmPassword.getText().toString();

        if (password.equals(passwordConfirm)) {
            //TODO: Create the new user and save the data
            //ProofOfConcept
            RegularUser regularUser = new RegularUser();
            regularUser.setFullName(fullName);
            regularUser.setUsername(username);
            regularUser.setPassword(password);
            System.out.println(regularUser);

            //Add the new user to the database
            if (controller.addNewRegularUser(regularUser)) {
                //Save the username in sharedPreferences to know that the user was logged in successfully
                controller.saveUsernameLoggedIn(username);

                //The user was added => Start the new MainScreenActivity
                startMainScreenActivity();

                //Finish the app so the user can not get back to this activity
                getActivity().finish();
            }
            else {
                //Inform the user something went wrong!
                Toast.makeText(getActivity(), Constants.SOMETHING_WENT_WRONG, Toast.LENGTH_LONG).show();
            }
        }
        else {
            //Inform the user that the passwords do not match! Ask to introduce them again!
            editTextPassword.setText(""); editTextConfirmPassword.setText("");
            Toast.makeText(getActivity(), Constants.PASSWORDS_DON_NOT_MATCH, Toast.LENGTH_LONG).show();
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
