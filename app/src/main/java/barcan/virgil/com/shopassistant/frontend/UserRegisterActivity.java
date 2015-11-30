package barcan.virgil.com.shopassistant.frontend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.model.RegularUser;

public class UserRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);
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
    public void userRegister(View view) {
        System.out.println("UserRegisterActivity.userRegister");
        EditText editTextFullName = (EditText)findViewById(R.id.editTextFullName);
        EditText editTextUsername = (EditText)findViewById(R.id.editTextUsername);
        EditText editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        EditText editTextConfirmPassword = (EditText)findViewById(R.id.editTextConfirmPassword);
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
        }
        else {
            //TODO: Inform the user that the passwords do not match! Ask to introduce them again!
        }
    }
}
