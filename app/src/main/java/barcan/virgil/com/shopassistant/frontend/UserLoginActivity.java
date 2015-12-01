package barcan.virgil.com.shopassistant.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.model.RegularUser;

public class UserLoginActivity extends AppCompatActivity {

    Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

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
     * This function is called when the Login button from the user login screen is pressed
     * It gets the data introduced in the fields and saves it
     * @param view the view
     */
    public void userLogin(View view) {
        System.out.println("UserLoginActivity.userLogin");
        EditText editTextUsername = (EditText)findViewById(R.id.editTextUsername);
        EditText editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        //TODO: Check if the user exists and save his data somewhere for the current session
        //ProofOfConcept
        RegularUser regularUser = new RegularUser();
        regularUser.setUsername(username);
        regularUser.setPassword(password);
        System.out.println(regularUser);

        //Start the MainScreen activity if the user credentials are correct
        //TODO: Check if the user credentials are correct
        if (true) {
            //Save the username in sharedPreferences to know that the user was logged in successfully
            controller.saveUsernameLoggedIn(username);

            Intent intentMainScreen = new Intent(UserLoginActivity.this, MainScreenActivity.class);
            //intentMainScreen.putExtra("KEY", "value");
            startActivity(intentMainScreen);
        }
        else {
            //TODO: Inform the user that the credentials are not correct
            System.out.println("UserLoginActivity.userLogin: incorrect login credentials!");
        }
    }

    /**
     * This function is called when the Company button from the user login screen is pressed
     * @param view the view
     */
    public void startCompanyLoginActivity(View view) {
        System.out.println("UserLoginActivity.startCompanyLoginActivity");
        //TODO: Handle company login
        Intent intentCompanyLoginActivity = new Intent(UserLoginActivity.this, CompanyLoginActivity.class);
        //intentCompanyLoginActivity.putExtra("KEY", "value");
        startActivity(intentCompanyLoginActivity);
    }

    /**
     * This function is called when the Register button from the user login screen is pressed
     * @param view the view
     */
    public void startUserRegisterActivity(View view) {
        System.out.println("UserLoginActivity.startUserRegisterActivity");
        //TODO: Handle user register
        Intent intentUserRegisterActivity = new Intent(UserLoginActivity.this, UserRegisterActivity.class);
        //intentUserRegisterActivity.putExtra("KEY", "value");
        startActivity(intentUserRegisterActivity);
    }
}
