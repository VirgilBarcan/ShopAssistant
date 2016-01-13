package barcan.virgil.com.shopassistant.backend;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import barcan.virgil.com.shopassistant.frontend.company.CompanyLoginFragment;
import barcan.virgil.com.shopassistant.frontend.regular.UserRootFragment;

/**
 * Created by virgil on 01.12.2015.
 */
public class AppSectionsPageAdapter extends FragmentPagerAdapter {

    public AppSectionsPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: //User
                return new UserRootFragment();
            case 1: //Company
                return new CompanyLoginFragment();
            default:
                System.out.println("AppSectionsPageAdapter.getItem: ERROR! Position invalid!");
                break;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2; //User and Company pages
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: //User
                return "User";
            case 1: //Company
                return "Company";
            default:
                System.out.println("AppSectionsPageAdapter.getItem: ERROR! Position invalid!");
                break;
        }

        return null;
    }
}
