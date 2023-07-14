package com.example.iotvandergraaf.view.login;

import android.content.Intent;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.iotvandergraaf.R;
import com.example.iotvandergraaf.presenter.login.AuthPresenter;
import com.example.iotvandergraaf.view.charts.SensorsActivity;

import java.util.ArrayList;
import java.util.List;

public class AuthFragment extends Fragment implements iLoginView.iAuth {

    private AuthPresenter authPresenter;

    private EditText emailField;
    private EditText passField;
    private Spinner modeApp;
    private Button loginButton;
    private TextView signUpLink;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_auth, container, false);

        emailField = view.findViewById(R.id.emailField);
        passField =  view.findViewById(R.id.passField);
        modeApp = view.findViewById(R.id.listAppMode);
        loginButton = view.findViewById(R.id.signInButton);
        signUpLink = view.findViewById(R.id.signUpLink);

        emailField.setText("raisnet213@gmail.com");
        passField.setText("under7");

        authPresenter = new AuthPresenter(this);

        loginButton.setOnClickListener(v -> {
            String email = emailField.getText().toString();
            String pass = passField.getText().toString();
            String mode = modeApp.getSelectedItem().toString();
            authPresenter.isExistUser(email,pass,mode);
        });

        signUpLink.setOnClickListener(v -> { switchLayout(); });

        modeApp.setAdapter(initListModeApp());

        return view;
    }

    private ArrayAdapter<String> initListModeApp(){
        List<String> list = new ArrayList<String>();

        list.add("Arduino Proteus");
        list.add("Arduino ESP8266");

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this.getActivity(),android.R.layout.simple_spinner_item, list);

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return spinnerArrayAdapter;
    }

    @Override
    public void emailFailed() {
        emailField.setError("enter a valid email address");
    }

    @Override
    public void passFailed() {
        passField.setError("between 5 and 10 alphanumeric characters");
    }

    @Override
    public void userExistFailed() {
        Toast.makeText(getActivity(),"User's not found",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void matchPassFailed() {
        Toast.makeText(getActivity(),"Incorrect password",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void switchLayout() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container,new SignUpFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void serverFailed(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSensorsActivity(){
        Intent intent = new Intent(getActivity().getApplicationContext(), SensorsActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }
}
