package barcan.virgil.com.shopassistant.frontend.regular;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;
import barcan.virgil.com.shopassistant.model.Constants;

public class UserRootFragment extends Fragment {

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("UserLoginFragment.onCreateView");
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        rootView = inflater.inflate(R.layout.root_fragment, container, false);

        //Add the UserLoginFragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.root_frame, new UserLoginFragment());
        transaction.commit();

        return rootView;
    }
}
