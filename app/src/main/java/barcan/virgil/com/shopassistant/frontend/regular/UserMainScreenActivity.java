package barcan.virgil.com.shopassistant.frontend.regular;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
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
import barcan.virgil.com.shopassistant.model.Constants;
import barcan.virgil.com.shopassistant.model.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserMainScreenActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private CoordinatorLayout coordinatorLayout;

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_logged);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller = Controller.getInstance();

        //Start the location service?
        if (controller.startService()) {
            controller.startLocationService();
        }
        else {
            controller.stopLocationService();
        }

        //Choose the right fragment to show:
        // if the activity was opened by a notification, show the shopping list fragment
        // else show the home fragment
        String fragmentToShowString = "UserHomeFragment";
        String shopProductsToShow = "ALL";
        if (getIntent() != null && getIntent().getStringExtra(Constants.FRAGMENT_TO_START) != null) {
            fragmentToShowString = getIntent().getStringExtra(Constants.FRAGMENT_TO_START);
            shopProductsToShow = getIntent().getStringExtra(Constants.SHOP_PRODUCTS_TO_SHOW);
        }
        //controller.setShopToShow(shopProductsToShow);

        if (fragmentToShowString.equals("UserHomeFragment")) {
            UserHomeFragment fragmentHome = new UserHomeFragment();
            FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
            fragmentTransactionHome.replace(R.id.frame, fragmentHome);
            fragmentTransactionHome.commit();
        }
        else {
            UserShoppingListFragment fragmentShoppingList = new UserShoppingListFragment();
            fragmentShoppingList.setShopToShow(shopProductsToShow);
            FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
            fragmentTransactionHome.replace(R.id.frame, fragmentShoppingList);
            fragmentTransactionHome.commit();
        }

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

                controller.setShopToShow("ALL");

                switch (menuItem.getItemId()) {
                    case R.id.goToHome:
                        Snackbar.make(coordinatorLayout, "Home selected", Snackbar.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), "Home selected", Toast.LENGTH_SHORT).show();

                        startHomeFragment();

                        return true;

                    case R.id.goToProducts:
                        Snackbar.make(coordinatorLayout, "Search products selected", Snackbar.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), "Search products selected", Toast.LENGTH_SHORT).show();

                        startProductsFragment();

                        return true;

                    case R.id.shoppingList:
                        Snackbar.make(coordinatorLayout, "Shopping list selected", Snackbar.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), "Shopping list selected", Toast.LENGTH_SHORT).show();

                        startShoppingListFragment();

                        return true;

                    case R.id.goToAccount:
                        Snackbar.make(coordinatorLayout, "Account selected", Snackbar.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), "Account selected", Toast.LENGTH_SHORT).show();

                        startAccountFragment();

                        return true;

                    case R.id.goToSettings:
                        Snackbar.make(coordinatorLayout, "Settings selected", Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "Notifications selected", Toast.LENGTH_SHORT).show();

                        startSettingsFragment();

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
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        System.out.println("UserMainScreenActivity.onCreateOptionsMenu: getComponentName=" + getComponentName());
        System.out.println("UserMainScreenActivity.onCreateOptionsMenu: searchableInfo=" + searchManager.getSearchableInfo(getComponentName()));

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
                startSettingsFragment();

                break;

            default:
                System.out.println("MainActivity.onOptionsItemSelected: ERROR! MenuItem id not recognized!");
        }

        return super.onOptionsItemSelected(item);
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
        UserHomeFragment fragmentHome = new UserHomeFragment();
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
        UserProductsFragment fragmentProducts = new UserProductsFragment();
        FragmentTransaction fragmentTransactionProducts = getSupportFragmentManager().beginTransaction();
        fragmentTransactionProducts.replace(R.id.frame, fragmentProducts);
        fragmentTransactionProducts.addToBackStack("Products");
        fragmentTransactionProducts.commit();
    }

    /**
     * This method starts the ShoppingList fragment when the user clicks on the ShoppingList button
     * in the NavigationView (Drawer)
     */
    private void startShoppingListFragment() {
        UserShoppingListFragment fragmentShoppingList = new UserShoppingListFragment();
        fragmentShoppingList.setShopToShow("ALL");
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
        UserAccountFragment fragmentAccount = new UserAccountFragment();
        FragmentTransaction fragmentTransactionAccount = getSupportFragmentManager().beginTransaction();
        fragmentTransactionAccount.replace(R.id.frame, fragmentAccount);
        fragmentTransactionAccount.addToBackStack("Account");
        fragmentTransactionAccount.commit();
    }

    /**
     * This method starts the Settings fragment when the user clicks on the Settings button
     * in the NavigationView (Drawer)
     */
    private void startSettingsFragment() {

        UserSettingsFragment fragmentSettings = new UserSettingsFragment();

        FragmentTransaction fragmentTransactionSettings = getSupportFragmentManager().beginTransaction();
        fragmentTransactionSettings.replace(R.id.frame, fragmentSettings);
        fragmentTransactionSettings.addToBackStack("Settings");
        fragmentTransactionSettings.commit();

    }
}