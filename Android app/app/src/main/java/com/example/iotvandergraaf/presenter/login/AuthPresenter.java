package com.example.iotvandergraaf.presenter.login;

import com.example.iotvandergraaf.model.logn.EmailUser;
import com.example.iotvandergraaf.model.logn.ResponseData;
import com.example.iotvandergraaf.model.logn.User;
import com.example.iotvandergraaf.network.AuthRetrofitInstance;
import com.example.iotvandergraaf.network.iApiAuth;
import com.example.iotvandergraaf.presenter.ApplicationModePresenter;
import com.example.iotvandergraaf.view.login.iLoginView;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthPresenter implements iAuthPresenter{

    private iLoginView.iAuth auth;

    public AuthPresenter(iLoginView.iAuth auth) {
        this.auth = auth;
    }

    @Override
    public boolean validateData(String... args) {
        boolean isValid = true;
        String email = args[0];
        String password = args[1];

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() || email == null) {
            auth.emailFailed();
            isValid = false;
        }

        if (password.isEmpty() || password.length() < 5 || password.length() > 10 || password == null){
            auth.passFailed();
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void isExistUser(String... args) {

        if(validateData(args) == false){
            return;
        }

        String email = (String)args[0];
        String password = (String)args[1];
        String modeApp = (String)args[2];

        Gson gson = new Gson();
        iApiAuth api = AuthRetrofitInstance.getInstance().getApi();

        api.getUser(new EmailUser(email)).enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()){
                    ResponseData data = response.body();
                    if(data.getCodeStatus() == 0 && data.getElement() != null){
                        User user = gson.fromJson(response.body().getElement(),User.class);
                        String matchPass = EncryptPass.getSecurePassword(password,user.getSalt());

                        if(user.getPass().equals(matchPass)){
                            ApplicationModePresenter.init(modeApp,user.getType());
                            auth.showSensorsActivity();
                        } else {
                            auth.matchPassFailed();
                        }
                    } else {
                        auth.userExistFailed();
                    }
                } else {
                    auth.serverFailed(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                auth.serverFailed(t.toString());
            }
        });

/*
         User user = dbHelper.getContact(email);

        if(user == null){
            auth.userExistFailed();
        } else {
            String matchPass = EncryptPass.getSecurePassword(password,user.getSalt());

            System.out.println("Pass " + password);
            System.out.println("Salt from database " + user.getSalt());
            System.out.println("Pass from database " + user.getPass());
            System.out.println("matchPass " + matchPass);

            if(user.getPass().equals(matchPass)){

            } else {
                auth.matchPassFailed();
            }
        }
 */
    }
}
