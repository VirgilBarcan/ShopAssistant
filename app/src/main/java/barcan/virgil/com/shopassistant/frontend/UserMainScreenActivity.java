package barcan.virgil.com.shopassistant.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import barcan.virgil.com.shopassistant.R;

public class UserMainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_logged);
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
     * This function is called when the Login button from the main screen is pressed
     * @param view the view
     */
    public void loginMain(View view) {
        System.out.println("MainActivity.loginMain");

        //TODO: Start the login activity
        Intent intentLoginActivity = new Intent(UserMainScreenActivity.this, UserLoginActivity.class);
        //intentLoginActivity.putExtra("KEY", "value");
        startActivity(intentLoginActivity);
    }

    /**
     * This function is called when the Register button from the main screen is pressed
     * @param view the view
     */
    public void registerMain(View view) {
        System.out.println("MainActivity.registerMain");

        //TODO: Start the register activity
        Intent intentRegisterActivity = new Intent(UserMainScreenActivity.this, UserRegisterActivity.class);
        //intentLoginActivity.putExtra("KEY", "value");
        startActivity(intentRegisterActivity);
    }
}