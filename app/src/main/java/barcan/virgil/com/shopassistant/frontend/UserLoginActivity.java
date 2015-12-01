package barcan.virgil.com.shopassistant.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.model.Constants;
import barcan.virgil.com.shopassistant.model.RegularUser;

public class UserLoginActivity extends AppCompatActivity {

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("UserLoginActivity.onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        controller = Controller.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        System.out.println("UserLoginActivity.onCreateOptionsMenu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("UserLoginActivity.onOptionsItemSelected");
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
        EditText editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
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
            finish();
        }
        else {
            //Inform the user that the credentials are not correct and erase the previous credentials
            System.out.println("UserLoginActivity.userLogin: incorrect login credentials!");

            editTextUsername.setText(""); editTextPassword.setText("");
            Toast.makeText(this, Constants.INCORRECT_LOGIN_CREDENTIALS, Toast.LENGTH_LONG).show();
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

        //Finish the app so the user can not get back to this activity
        finish();
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

        //Finish the app so the user can not get back to this activity
        finish();
    }

    /**
     * This method is called when the user has logged in correctly; the MainScreenActivity has to be opened
     */
    private void startMainScreenActivity() {
        Intent intentMainScreen = new Intent(UserLoginActivity.this, UserMainScreenActivity.class);
        //intentMainScreen.putExtra("KEY", "value");
        startActivity(intentMainScreen);
    }
}
