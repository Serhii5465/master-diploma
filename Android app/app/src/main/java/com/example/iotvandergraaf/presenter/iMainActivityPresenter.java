package com.example.iotvandergraaf.presenter;

import java.util.concurrent.ExecutionException;

public interface iMainActivityPresenter {
    boolean isWifiConnected();
    void init() throws ExecutionException, InterruptedException;
}
