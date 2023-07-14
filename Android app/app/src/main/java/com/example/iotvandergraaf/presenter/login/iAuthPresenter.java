package com.example.iotvandergraaf.presenter.login;

import com.example.iotvandergraaf.network.iApiAuth;

public interface iAuthPresenter {
     boolean validateData(String... args);
     void isExistUser(String... args);

    interface iSignUpPresenter extends iAuthPresenter{
         void addUserToDb(iApiAuth api, String... args);
         void getAdmins(iApiAuth api, String... args);
    }
}
