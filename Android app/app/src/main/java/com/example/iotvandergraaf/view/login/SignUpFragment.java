package com.example.iotvandergraaf.view.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.iotvandergraaf.R;
import com.example.iotvandergraaf.presenter.login.SignUpPresenter;

import java.util.ArrayList;
import java.util.List;

public class SignUpFragment extends Fragment implements iLoginView.iSignUp {

    private SignUpPresenter presenter;

    private EditText emailField;
    private EditText nameField;
    private EditText passField;
    private EditText confirmPassField;
    private Spinner typeUser;
    private Button signUpButton;
    private TextView signInLink;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        emailField = (EditText) view.findViewById(R.id.registerEmailField);
        nameField = (EditText) view.findViewById(R.id.registerNameField);
        passField = (EditText) view.findViewById(R.id.registerPassField);
        confirmPassField = (EditText) view.findViewById(R.id.registerConfirmPassField);
        typeUser = (Spinner) view.findViewById(R.id.registerListTypeUser);
        signUpButton = (Button) view.findViewById(R.id.signUpButton);
        signInLink = (TextView) view.findViewById(R.id.singInLink);

        presenter = new SignUpPresenter(this);

        signUpButton.setOnClickListener(v -> {
            String email = emailField.getText().toString();
            String name = nameField.getText().toString();
            String pass = passField.getText().toString();
            String confirmPass = confirmPassField.getText().toString();
            String type = typeUser.getSelectedItem().toString();

            presenter.isExistUser(email,name,pass,confirmPass,type);
        });

        signInLink.setOnClickListener(v -> {switchLayout();});

        typeUser.setAdapter(initListUser());

        return view;
    }

    private ArrayAdapter<String> initListUser(){
        List<String> list = new ArrayList<String>();
        list.add("user");
        list.add("admin");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this.getActivity(),android.R.layout.simple_spinner_item, list);

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return spinnerArrayAdapter;
    }


    @Override
    public void confirmPassFailed() {
        confirmPassField.setError("These password does't match");
    }

    @Override
    public void userNameFailed(){
        nameField.setError("Name must be no more than 8 characters.Min length 4 characters");
    }

    @Override
    public void emailFailed() {
        emailField.setError("Enter a valid email address");
    }

    @Override
    public void passFailed() {
        passField.setError("Between 5 and 10 alphanumeric characters");
    }

    @Override
    public void addAdminFailed() {
        Toast.makeText(getActivity(),"Admin already exists",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addUserFailed() {
        Toast.makeText(getActivity(),"This email already exists in database",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void switchLayout() {
        if(getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void serverFailed(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void messageInvite() {
        Toast.makeText(getActivity(),"Please,authorize in system",Toast.LENGTH_SHORT).show();
    }
}
