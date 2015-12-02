package barcan.virgil.com.shopassistant.frontend;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import barcan.virgil.com.shopassistant.R;

/**
 * Created by virgil on 03.12.2015.
 */
public class UserMainFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private static int sectionNumber;
    private static Activity mainActivity;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static UserMainFragment newInstance(Activity mainActivity, int sectionNumber) {
        UserMainFragment.sectionNumber = sectionNumber;
        UserMainFragment.mainActivity = mainActivity;
        UserMainFragment userMainFragment = new UserMainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        userMainFragment.setArguments(args);

        return userMainFragment;
    }

    public UserMainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_main, container, false);

        TextView sectionLabel = (TextView) rootView.findViewById(R.id.section_label);
        sectionLabel.setText("Section " + UserMainFragment.sectionNumber);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((UserMainScreenActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

}
