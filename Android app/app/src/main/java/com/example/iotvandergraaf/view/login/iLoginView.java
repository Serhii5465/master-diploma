package com.example.iotvandergraaf.view.login;

import android.os.Bundle;

public interface iLoginView {
     void emailFailed();
     void passFailed();
     void switchLayout();
     void serverFailed(String message);

    interface iAuth extends iLoginView{
         void userExistFailed();
         void matchPassFailed();
         void showSensorsActivity();
    }

    interface iSignUp extends iLoginView{
         void userNameFailed();
         void confirmPassFailed();
         void addAdminFailed();
         void addUserFailed();
         void messageInvite();
    }

    interface iBlankView{
         void switchOnAuthFragment(Bundle savedInstanceState);
         void connectionFailed(String message);
    }
}
