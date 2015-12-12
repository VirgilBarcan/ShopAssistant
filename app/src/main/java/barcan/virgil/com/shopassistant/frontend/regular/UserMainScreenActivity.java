package barcan.virgil.com.shopassistant.frontend.regular;

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

import java.util.List;
import java.util.Map;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.backend.service.LocationService;
import barcan.virgil.com.shopassistant.frontend.LocationActivity;
import barcan.virgil.com.shopassistant.model.Company;
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

        //Small test
        List<Company> userShoppingListCompanies = controller.getShoppingListCompanies(controller.getConnectedUser());

        //Start the location service
        startLocationService();

        UserLoggedHomeFragment fragmentHome = new UserLoggedHomeFragment();
        FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
        fragmentTransactionHome.replace(R.id.frame, fragmentHome);
        fragmentTransactionHome.commit();

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

                        //TODO: Create fragments and classes
                        startHomeFragment();

                        return true;

                    case R.id.goToProducts:
                        Toast.makeText(getApplicationContext(), "Search products selected", Toast.LENGTH_SHORT).show();

                        //TODO: Create fragments and classes
                        startProductsFragment();

                        return true;

                    case R.id.shoppingList:
                        Toast.makeText(getApplicationContext(), "Shopping list selected", Toast.LENGTH_SHORT).show();

                        //TODO: Create fragments and classes
                        startShoppingListFragment();

                        return true;

                    case R.id.goToAccount:
                        Toast.makeText(getApplicationContext(), "Account selected", Toast.LENGTH_SHORT).show();

                        //TODO: Create fragments and classes
                        startAccountFragment();

                        return true;

                    case R.id.goToNotifications:
                        Toast.makeText(getApplicationContext(), "Notifications selected", Toast.LENGTH_SHORT).show();

                        //TODO: Create fragments and classes
                        startNotificationsFragment();

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
                //TODO: Create fragments and classes
                startProductsFragment();
                break;

            case R.id.action_shopping_list:
                System.out.println("MainActivity.onOptionsItemSelected: Shopping List pressed!");
                //TODO: React to shopping list
                //TODO: Create fragments and classes
                startShoppingListFragment();
                break;

            case R.id.action_settings:
                System.out.println("MainActivity.onOptionsItemSelected: Settings pressed!");
                //TODO: React to settings

                //Just a small test
                openLocationActivity();

                break;

            default:
                System.out.println("MainActivity.onOptionsItemSelected: ERROR! MenuItem id not recognized!");
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method starts the LocationService
     * The LocationService gets GPS position and checks if shops are close to the user
     * If a shop that sells something the user wants is close, the Service notifies
     */
    private void startLocationService() {
        Intent intentLocationService = new Intent(this, LocationService.class);
        //intentLocationService.putExtra("KEY", "value");
        this.startService(intentLocationService);
    }

    /**
     * This method adds user related info to the navigation header
     * @param navigationView the NavigationView whose header we want to process
     */
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

    /**
     * This method starts the Home fragment when the user clicks on the Home button
     * in the NavigationView (Drawer)
     */
    private void startHomeFragment() {
        UserLoggedHomeFragment fragmentHome = new UserLoggedHomeFragment();
        FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
        fragmentTransactionHome.replace(R.id.frame, fragmentHome);
        fragmentTransactionHome.addToBackStack("Home");
        fragmentTransactionHome.commit();
    }

    /**
     * This method starts the Products fragment when the user clicks on the Products button
     * in the NavigationView (Drawer)
     */
    private void startProductsFragment() {
        UserLoggedProductsFragment fragmentProducts = new UserLoggedProductsFragment();
        FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
        fragmentTransactionHome.replace(R.id.frame, fragmentProducts);
        fragmentTransactionHome.addToBackStack("Products");
        fragmentTransactionHome.commit();
    }

    /**
     * This method starts the ShoppingList fragment when the user clicks on the ShoppingList button
     * in the NavigationView (Drawer)
     */
    private void startShoppingListFragment() {
        UserLoggedShoppingListFragment fragmentShoppingList = new UserLoggedShoppingListFragment();
        FragmentTransaction fragmentTransactionShoppingList = getSupportFragmentManager().beginTransaction();
        fragmentTransactionShoppingList.replace(R.id.frame, fragmentShoppingList);
        fragmentTransactionShoppingList.addToBackStack("Shopping List");
        fragmentTransactionShoppingList.commit();
    }

    /**
     * This method starts the Account fragment when the user clicks on the Account button
     * in the NavigationView (Drawer)
     */
    private void startAccountFragment() {
        UserLoggedAccountFragment fragmentAccount = new UserLoggedAccountFragment();
        FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
        fragmentTransactionHome.replace(R.id.frame, fragmentAccount);
        fragmentTransactionHome.addToBackStack("Account");
        fragmentTransactionHome.commit();
    }

    /**
     * This method starts the Notifications fragment when the user clicks on the Notifications button
     * in the NavigationView (Drawer)
     */
    private void startNotificationsFragment() {
        UserLoggedNotificationsFragment fragmentNotifications = new UserLoggedNotificationsFragment();
        FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
        fragmentTransactionHome.replace(R.id.frame, fragmentNotifications);
        fragmentTransactionHome.addToBackStack("Notifications");
        fragmentTransactionHome.commit();
    }



    private void openLocationActivity() {
        Intent intentLocationActivity = new Intent(this, LocationActivity.class);
        //intentLocationActivity.putExtra("KEY", "value");
        startActivity(intentLocationActivity);

        //Finish the app so the user can not get back to this activity
        //finish();
    }

}