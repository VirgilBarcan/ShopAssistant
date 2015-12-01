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
import barcan.virgil.com.shopassistant.backend.backend.database.TablesContracts;
import barcan.virgil.com.shopassistant.model.CompanyUser;
import barcan.virgil.com.shopassistant.model.Constants;
import barcan.virgil.com.shopassistant.model.RegularUser;

public class CompanyRegisterActivity extends AppCompatActivity {

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_register);

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
     * This function is called when the Register button from the user login screen is pressed
     * It gets the data introduced in the fields and saves it
     * @param view the view
     */
    public void companyRegister(View view) {
        System.out.println("CompanyRegisterActivity.companyRegister");
        EditText editTextCompanyUserFullName = (EditText)findViewById(R.id.editTextCompanyUserFullName);
        EditText editTextCompanyName = (EditText)findViewById(R.id.editTextCompanyName);
        EditText editTextUsername = (EditText)findViewById(R.id.editTextUsername);
        EditText editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        EditText editTextConfirmPassword = (EditText)findViewById(R.id.editTextConfirmPassword);
        String companyUserFullName = editTextCompanyUserFullName.getText().toString();
        String companyName = editTextCompanyName.getText().toString();
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        String passwordConfirm = editTextConfirmPassword.getText().toString();

        if (password.equals(passwordConfirm)) {
            //TODO: Create the new user and save the data
            //ProofOfConcept
            CompanyUser companyUser = new CompanyUser();
            companyUser.setFullName(companyUserFullName + " @ " + companyName);
            companyUser.setUsername(username);
            companyUser.setPassword(password);
            System.out.println(companyUser);

            //Add the new user to the database
            if (controller.addNewCompanyUser(companyUser)) {
                //The user was added => Start the new MainScreenActivity
                startCompanyMainScreenActivity();

                //Finish the app so the user can not get back to this activity
                finish();
            }
            else {
                //Inform the user something went wrong!
                Toast.makeText(this, Constants.SOMETHING_WENT_WRONG, Toast.LENGTH_LONG).show();
            }
        }
        else {
            //TODO: Inform the user that the passwords do not match! Ask to introduce them again!
            editTextPassword.setText(""); editTextConfirmPassword.setText("");
            Toast.makeText(this, Constants.PASSWORDS_DONT_MATCH, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method is called when the user has registered correctly; the MainScreenActivity has to be opened
     */
    private void startCompanyMainScreenActivity() {
        //TODO: Add a new activity for a company main screen

        Intent intentMainScreen = new Intent(CompanyRegisterActivity.this, UserMainScreenActivity.class);
        //intentMainScreen.putExtra("KEY", "value");
        startActivity(intentMainScreen);
    }
}
