package barcan.virgil.com.shopassistant.frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.model.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserMainScreenActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_logged);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller = Controller.getInstance();

        navigationView = (NavigationView) findViewById(R.id.navigationView);
        addDataToNavigationViewHeader(navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                }
                else {
                    menuItem.setChecked(true);
                }

                drawerLayout.closeDrawers();

                System.out.println("UserMainScreenActivity.onNavigationItemSelected: menuItem.getItemId()=" + menuItem.getItemId());

                switch (menuItem.getItemId()) {
                    case R.id.goToHome:
                        Toast.makeText(getApplicationContext(), "Home selected", Toast.LENGTH_SHORT).show();

                        UserLoggedHomeFragment fragment = new UserLoggedHomeFragment();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame, fragment);
                        fragmentTransaction.commit();
                        return true;

                    case R.id.goToProducts:
                        Toast.makeText(getApplicationContext(), "Search products selected", Toast.LENGTH_SHORT).show();

                        //TODO: Create fragments and classes

                        return true;

                    case R.id.shoppingList:
                        Toast.makeText(getApplicationContext(), "Shopping list selected", Toast.LENGTH_SHORT).show();

                        //TODO: Create fragments and classes

                        return true;

                    case R.id.goToAccount:
                        Toast.makeText(getApplicationContext(), "Account selected", Toast.LENGTH_SHORT).show();

                        //TODO: Create fragments and classes

                        return true;

                    default:
                        System.out.println("UserMainScreenActivity.onNavigationItemSelected: ERROR! Id not supported!");
                }
                return false;
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes.
                // As we don't want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer opens.
                // As we don't want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        System.out.println("UserMainScreenActivity.onCreateOptionsMenu: menu=" + menu);

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
                System.out.println("MainActivity.onOptionsItemSelected: Shopping List pressed!");
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

    private void addDataToNavigationViewHeader(NavigationView navigationView) {
        User connectedUser = controller.getConnectedUser();

        View headerView = LayoutInflater.from(this).inflate(R.layout.navigation_drawer_header, null);

        System.out.println("UserMainScreenActivity.addDataToNavigationViewHeader: connectedUser=" + connectedUser);

        if (connectedUser != null) {
            TextView textViewFullName = (TextView) headerView.findViewById(R.id.fullname);
            textViewFullName.setText(connectedUser.getFullName());

            TextView textViewUsername = (TextView) headerView.findViewById(R.id.username);
            textViewUsername.setText(connectedUser.getUsername());

            //TODO: Add user image to the header
            CircleImageView circleImageView = (CircleImageView) headerView.findViewById(R.id.circleView);
            circleImageView.setImageResource(R.drawable.ic_account_circle);

            navigationView.addHeaderView(headerView);
        }
        else {
            System.out.println("UserMainScreenActivity.addDataToNavigationViewHeader: ERROR! No user is connected! This should not be possible!");
        }
    }

}