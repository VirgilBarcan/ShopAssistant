package barcan.virgil.com.shopassistant.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;

public class UserMainScreenActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_logged);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller = Controller.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        System.out.println("UserMainScreenActivity.onCreateOptionsMenu: menu=" + menu);

        menu.findItem(R.id.action_search).setVisible(true);
        menu.findItem(R.id.action_shopping_list).setVisible(true);
        menu.findItem(R.id.action_settings).setVisible(true);

        return super.onCreateOptionsMenu(menu);
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
                //TODO: React to settings
                break;
            default:
                System.out.println("MainActivity.onOptionsItemSelected: ERROR! MenuItem id not recognized!");
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is called when the user presses ShoppingList button from the Toolbar
     * It opens the ShoppingListActivity and shows the user's shopping list
     */
    private void openShoppingListActivity() {
        Intent intentShoppingListActivity = new Intent(UserMainScreenActivity.this, UserShoppingListActivity.class);
        //intentShoppingListActivity.putExtra("KEY", "value");
        startActivity(intentShoppingListActivity);
    }

    /**
     * This method is called when the user presses the Account button
     * It opens the UserAccountActivity and shows the user's shopping list
     */
    private void openUserAccountActivity() {
        Intent intentShoppingListActivity = new Intent(UserMainScreenActivity.this, UserShoppingListActivity.class);
        //intentShoppingListActivity.putExtra("KEY", "value");
        startActivity(intentShoppingListActivity);
    }
}