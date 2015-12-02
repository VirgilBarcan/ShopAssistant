package barcan.virgil.com.shopassistant.frontend;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;

public class UserMainScreenActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private Toolbar toolbar;
    private NavigationDrawerFragment navigationDrawerFragment;
    private CharSequence title;

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_logged);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller = Controller.getInstance();

        navigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        title = getSupportActionBar().getTitle();

        navigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getFragmentManager();

        switch (position) {
            case 0: //Regular User
                fragmentManager.beginTransaction().replace(R.id.container, UserMainFragment.newInstance(this, position)).commit();
                break;
            case 1: //Company User
                //TODO: Define CompanyUserMainFragment
                //fragmentManager.beginTransaction().replace(R.id.container, CompanyUserMainFragment.newInstance(position)).commit();
                break;
            default:
                System.out.println("UserMainScreenActivity.onNavigationDrawerItemSelected: ERROR! NavigationDrawer position out of bounds!");
                break;
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                title = getString(R.string.title_section1);
                break;
            case 2:
                title = getString(R.string.title_section2);
                break;
            default:
                System.out.println("UserMainScreenActivity.onSectionAttached! ERROR! Section number out of bounds!");
                break;
        }
    }

    public void restoreActionBar() {
        //TODO: Modify to user Toolbar
        //ActionBar actionBar = getActionBar();
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        //actionBar.setDisplayShowTitleEnabled(true);
        //actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        System.out.println("UserMainScreenActivity.onCreateOptionsMenu: menu=" + menu);

        restoreActionBar();

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