package barcan.virgil.com.shopassistant.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.model.CompanyUser;

public class CompanyLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_login);
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
        System.out.println("UserLoginActivity.companyLogin");
        EditText editTextUsername = (EditText)findViewById(R.id.editTextUsername);
        EditText editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        //TODO: Check if the user exists and save his data somewhere for the current session
        //ProofOfConcept
        CompanyUser companyUseregularUser = new CompanyUser();
        companyUseregularUser.setUsername(username);
        companyUseregularUser.setPassword(password);
        System.out.println(companyUseregularUser);
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
}
