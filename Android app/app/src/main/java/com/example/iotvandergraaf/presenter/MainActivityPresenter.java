package com.example.iotvandergraaf.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;

import com.example.iotvandergraaf.view.login.MainActivity;

import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class MainActivityPresenter implements iMainActivityPresenter {

    private MainActivity activity;

    public MainActivityPresenter(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void init() throws ExecutionException, InterruptedException {
        if(!isWifiConnected()){
            activity.connectionFailed("Please,check you Wifi connection");
        }
/**/
        if(!new AsyncWorkerCheckerNetwork()
                .execute("http://10.0.2.2:80").get()){
            activity.connectionFailed("Registration server is not available");
        }

        if(!new AsyncWorkerCheckerNetwork()
                .execute("http://10.0.2.2:8080").get()){
            activity.connectionFailed("ThingsBoard server is not available");
        }


    }

    @Override
    public boolean isWifiConnected(){
        ConnectivityManager cm =
                (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private class AsyncWorkerCheckerNetwork extends AsyncTask<String,Void,Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            try{
                URL myUrl = new URL(strings[0]);
                URLConnection connection = myUrl.openConnection();
                connection.setConnectTimeout(1500);
                connection.connect();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

}
