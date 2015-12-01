package barcan.virgil.com.shopassistant.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.model.CompanyUser;
import barcan.virgil.com.shopassistant.model.Constants;

public class CompanyLoginActivity extends AppCompatActivity {

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_login);

        controller = Controller.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This function is called when the Login button from the company login screen is pressed
     * It gets the data introduced in the fields and saves it
     * @param view the view
     */
    public void companyLogin(View view) {
        System.out.println("CompanyLoginActivity.companyLogin");
        EditText editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
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
            finish();
        }
        else {
            //Inform the user that the credentials are not correct and erase the previous credentials
            System.out.println("CompanyLoginActivity.companyLogin: incorrect login credentials!");

            editTextUsername.setText(""); editTextPassword.setText("");
            Toast.makeText(this, Constants.INCORRECT_LOGIN_CREDENTIALS, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This function is called when the User button from the company login screen is pressed
     * @param view the view
     */
    public void startUserLoginActivity(View view) {
        System.out.println("CompanyLoginActivity.startUserLoginActivity");
        //TODO: Handle user login
        Intent intentUserLoginActivity = new Intent(CompanyLoginActivity.this, UserLoginActivity.class);
        //intentUserLoginActivity.putExtra("KEY", "value");
        startActivity(intentUserLoginActivity);
    }

    /**
     * This function is called when the Register button from the company login screen is pressed
     * @param view the view
     */
    public void startCompanyRegisterActivity(View view) {
        System.out.println("CompanyLoginActivity.startCompanyRegisterActivity");
        //TODO: Handle company register
        Intent intentCompanyRegisterActivity = new Intent(CompanyLoginActivity.this, CompanyRegisterActivity.class);
        //intentCompanyRegisterActivity.putExtra("KEY", "value");
        startActivity(intentCompanyRegisterActivity);
    }

    private void startCompanyMainScreenActivity() {
        //TODO: Add a new activity for a company main screen

        Intent intentMainScreen = new Intent(CompanyLoginActivity.this, UserMainScreenActivity.class);
        //intentMainScreen.putExtra("KEY", "value");
        startActivity(intentMainScreen);
    }
}
