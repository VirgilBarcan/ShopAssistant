package barcan.virgil.com.shopassistant.frontend.company;

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
import barcan.virgil.com.shopassistant.frontend.regular.UserMainScreenActivity;
import barcan.virgil.com.shopassistant.model.CompanyUser;
import barcan.virgil.com.shopassistant.model.Constants;

public class CompanyLoginFragment extends Fragment {

    private Controller controller;
    private View rootView;
    private Button buttonLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("CompanyLoginFragment.onCreateView");
        super.onCreate(savedInstanceState);

        controller = Controller.getInstance();

        rootView = inflater.inflate(R.layout.company_login, container, false);

        buttonLogin = (Button) rootView.findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                companyLogin();
            }
        });

        return rootView;
    }

    /**
     * This function is called when the Login button from the company login screen is pressed
     * It gets the data introduced in the fields and saves it
     */
    public void companyLogin() {
        System.out.println("CompanyLoginFragment.companyLogin");
        EditText editTextUsername = (EditText) rootView.findViewById(R.id.editTextUsername);
        EditText editTextPassword = (EditText) rootView.findViewById(R.id.editTextPassword);
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        //TODO: Check if the user exists and save his data somewhere for the current session
        //ProofOfConcept
        CompanyUser companyUser = new CompanyUser();
        companyUser.setUsername(username);
        companyUser.setPassword(password);
        System.out.println(companyUser);

        //Start the UserMainScreenActivity activity if the user credentials are correct
        //Check if the user credentials are correct
        if (controller.existsUserInDatabase(username, password)) {
            //Save the username in sharedPreferences to know that the user was logged in successfully
            controller.saveUsernameLoggedIn(username);

            //Start the MainScreenActivity
            startCompanyMainScreenActivity();

            //Finish the app so the user can not get back to this activity
            getActivity().finish();
        }
        else {
            //Inform the user that the credentials are not correct and erase the previous credentials
            System.out.println("CompanyLoginFragment.companyLogin: incorrect login credentials!");

            editTextUsername.setText(""); editTextPassword.setText("");
            Toast.makeText(getActivity(), Constants.INCORRECT_LOGIN_CREDENTIALS, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This function is called when the Register button from the company login screen is pressed
     * @param view the view
     */
    public void startCompanyRegisterActivity(View view) {
        System.out.println("CompanyLoginFragment.startCompanyRegisterActivity");
        //TODO: Handle company register
        Intent intentCompanyRegisterActivity = new Intent(getActivity(), CompanyRegisterActivity.class);
        //intentCompanyRegisterActivity.putExtra("KEY", "value");
        startActivity(intentCompanyRegisterActivity);

        //Finish the app so the user can not get back to this activity
        getActivity().finish();
    }

    private void startCompanyMainScreenActivity() {
        //TODO: Add a new activity for a company main screen

        Intent intentMainScreen = new Intent(getActivity(), UserMainScreenActivity.class);
        //intentMainScreen.putExtra("KEY", "value");
        startActivity(intentMainScreen);

        //Finish the app so the user can not get back to this activity
        getActivity().finish();
    }
}
