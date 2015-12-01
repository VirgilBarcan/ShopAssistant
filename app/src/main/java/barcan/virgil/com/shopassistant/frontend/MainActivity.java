package barcan.virgil.com.shopassistant.frontend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Start the controller, which is a service running in background, waiting for location updates
        Controller controller = Controller.getInstance(this, this);

        //TODO: Check if the user is logged in
        if (controller.isLogged()) {
            setContentView(R.layout.activity_main_logged);
        }
        else {
            controller.setupSharedPreferences();

            setContentView(R.layout.activity_main);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:
                System.out.println("MainActivity.onOptionsItemSelected: Search pressed!");
                //TODO: React to search
                break;
            case R.id.action_shopping_list:
                System.out.println("MainActivity.onOptionsItemSelected: ShoppingList pressed!");
                //TODO: React to shopping list
                openShoppingListActivity();
                break;
            case R.id.action_settings:
                System.out.println("MainActivity.onOptionsItemSelected: Settings pressed!");
                //TODO: React to setting
                break;
            default:
                System.out.println("MainActivity.onOptionsItemSelected: ERROR! MenuItem id not recognized!");
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is called when the user presses ShoppingList button from the AppBar
     * It opens the ShoppingListActivity and shows the user's shopping list
     */
    private void openShoppingListActivity() {
        Intent intentShoppingListActivity = new Intent(MainActivity.this, UserShoppingListActivity.class);
        //intentShoppingListActivity.putExtra("KEY", "value");
        startActivity(intentShoppingListActivity);
    }

    /**
     * This function is called when the Login button from the main screen is pressed
     * @param view the view
     */
    public void loginMain(View view) {
        System.out.println("MainActivity.loginMain");

        //TODO: Start the login activity
        Intent intentLoginActivity = new Intent(MainActivity.this, UserLoginActivity.class);
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
        Intent intentRegisterActivity = new Intent(MainActivity.this, UserRegisterActivity.class);
        //intentLoginActivity.putExtra("KEY", "value");
        startActivity(intentRegisterActivity);
    }
}
