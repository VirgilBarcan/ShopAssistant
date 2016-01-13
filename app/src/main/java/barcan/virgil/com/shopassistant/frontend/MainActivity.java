package barcan.virgil.com.shopassistant.frontend;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.AppSectionsPageAdapter;
import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.frontend.regular.UserMainScreenActivity;
import barcan.virgil.com.shopassistant.model.Constants;

public class MainActivity extends AppCompatActivity {

    private AppSectionsPageAdapter appSectionsPageAdapter;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Start the controller, which is a service running in background, waiting for location updates
        controller = Controller.getInstance(this);

        //TODO: Check if the user is logged in (better check to be done! check the DB also)
        if (controller.isLogged()) {

            //TODO: Check to see if regular user or company user
            if (true) {
                //If the activity is started from a notification, then send the user to the specific fragment
                String activityToStart = "UserMainScreenActivity";
                String fragmentToStart = "UserHomeFragment";
                String shopProductsToShow = "ALL";

                if (getIntent() != null) {
                    activityToStart = getIntent().getStringExtra(Constants.ACTIVITY_TO_START);
                    fragmentToStart = getIntent().getStringExtra(Constants.FRAGMENT_TO_START);
                    shopProductsToShow = getIntent().getStringExtra(Constants.SHOP_PRODUCTS_TO_SHOW);
                }

                //Open the activity that has the user already logged
                openRegularUserMainScreenActivity(fragmentToStart, shopProductsToShow);
            }
            else {
                //Open the activity that has the user already logged
                //openCompanyUserMainScreenActivity();
            }
        }
        else {
            //Continue with this activity
            controller.setupSharedPreferences();

            setContentView(R.layout.activity_main);

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            appSectionsPageAdapter = new AppSectionsPageAdapter(getSupportFragmentManager());

            viewPager = (ViewPager) findViewById(R.id.viewPager);
            viewPager.setAdapter(appSectionsPageAdapter);

            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_shopping_list).setVisible(false);
        menu.findItem(R.id.action_settings).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                System.out.println("MainActivity.onOptionsItemSelected: Settings pressed!");
                //TODO: React to setting
                break;
            default:
                System.out.println("MainActivity.onOptionsItemSelected: ERROR! MenuItem id not recognized!");
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is called when the regular user is already logged in
     * The Activity is changed to MainActivityLogged, which has the action bar
     */
    private void openRegularUserMainScreenActivity(String fragmentToStart, String shopProductsToShow) {
        Intent intentUserMainScreenActivity = new Intent(MainActivity.this, UserMainScreenActivity.class);
        intentUserMainScreenActivity.putExtra(Constants.FRAGMENT_TO_START, fragmentToStart);
        intentUserMainScreenActivity.putExtra(Constants.SHOP_PRODUCTS_TO_SHOW, shopProductsToShow);
        startActivity(intentUserMainScreenActivity);

        //Finish the app so the user can not get back to this activity
        finish();
    }

    /**
     * This method is called when the company user is already logged in
     * The Activity is changed to MainActivityLogged, which has the action bar
     */
    private void openCompanyUserMainScreenActivity() {
        Intent intentUserMainScreenActivity = new Intent(MainActivity.this, UserMainScreenActivity.class);
        //intentUserMainScreenActivity.putExtra("KEY", "value");
        startActivity(intentUserMainScreenActivity);

        //Finish the app so the user can not get back to this activity
        finish();
    }

}
