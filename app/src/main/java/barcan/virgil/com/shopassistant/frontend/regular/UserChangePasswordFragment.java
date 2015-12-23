package barcan.virgil.com.shopassistant.frontend.regular;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.backend.Controller;

/**
 * Created by virgil on 22.12.2015.
 */
public class UserChangePasswordFragment extends Fragment {

    private Controller controller;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_logged_change_password, container, false);

        controller = Controller.getInstance();

        listenForChangePassword();

        return view;
    }

    private void listenForChangePassword() {
        Button buttonChangePassword = (Button) view.findViewById(R.id.buttonDoChangePassword);

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if the new password is confirmed
                EditText editTextNewPassword = (EditText) view.findViewById(R.id.editTextNewPassword);
                EditText editTextConfirmNewPassword = (EditText) view.findViewById(R.id.editTextConfirmNewPassword);
                EditText editTextOldPassword = (EditText) view.findViewById(R.id.editTextOldPassword);

                String newPassword = editTextNewPassword.getText().toString();
                String confirmNewPassword = editTextConfirmNewPassword.getText().toString();
                String oldPassword = editTextOldPassword.getText().toString();

                if (newPassword.equals(confirmNewPassword)) {
                    //Check if the user has inserted the correct old password
                    if (controller.getConnectedUser().getPassword().equals(oldPassword)) {
                        //Do change password
                        if (controller.changeRegularUserPassword(newPassword)) {
                            Toast.makeText(getActivity(), "Password changed!", Toast.LENGTH_LONG).show();
                            getActivity().getSupportFragmentManager().popBackStackImmediate();
                        }
                        else {
                            Toast.makeText(getActivity(), "Password not changed!", Toast.LENGTH_LONG).show();
                            editTextOldPassword.setText("");
                            editTextNewPassword.setText("");
                            editTextConfirmNewPassword.setText("");
                        }
                    }
                    else {
                        Toast.makeText(getActivity(), "The old password is not correct! Please retry!", Toast.LENGTH_LONG).show();
                        editTextOldPassword.setText("");
                        editTextNewPassword.setText("");
                        editTextConfirmNewPassword.setText("");
                    }
                }
                else {
                    Toast.makeText(getActivity(), "The confirm password doesn't match! Please retry!", Toast.LENGTH_LONG).show();
                    editTextOldPassword.setText("");
                    editTextNewPassword.setText("");
                    editTextConfirmNewPassword.setText("");
                }
            }
        });
    }

}
