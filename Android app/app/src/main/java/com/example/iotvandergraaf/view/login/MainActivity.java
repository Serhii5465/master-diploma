package com.example.iotvandergraaf.view.login;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.iotvandergraaf.R;
import com.example.iotvandergraaf.presenter.MainActivityPresenter;

import java.util.concurrent.ExecutionException;

public class MainActivity extends FragmentActivity implements iLoginView.iBlankView  {

    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainActivityPresenter(this);
        try {
            presenter.init();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        switchOnAuthFragment(savedInstanceState);
    }


    @Override
    public void connectionFailed(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Connection error");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                    System.exit(0);
                });
        alertDialog.show();
    }

    @Override
    public void switchOnAuthFragment(Bundle savedInstanceState){
        if(findViewById(R.id.fragment_container) != null){
            if(savedInstanceState!= null){
                return;
            }

            AuthFragment sign = new AuthFragment();
            sign.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().
                    add(R.id.fragment_container,sign).commit();
        }
    }
}
