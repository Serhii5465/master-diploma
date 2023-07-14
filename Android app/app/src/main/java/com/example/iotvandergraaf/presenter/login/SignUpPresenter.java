package com.example.iotvandergraaf.presenter.login;

import com.example.iotvandergraaf.model.logn.CountAdmin;
import com.example.iotvandergraaf.model.logn.EmailUser;
import com.example.iotvandergraaf.model.logn.ResponseData;
import com.example.iotvandergraaf.model.logn.User;
import com.example.iotvandergraaf.network.AuthRetrofitInstance;
import com.example.iotvandergraaf.network.iApiAuth;
import com.example.iotvandergraaf.view.login.iLoginView;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpPresenter implements iAuthPresenter.iSignUpPresenter{

    private iLoginView.iSignUp register;

    public SignUpPresenter(iLoginView.iSignUp register) {
        this.register = register;
    }

    @Override
    public boolean validateData(String... args){
        boolean isValid = true;

        String email = (String)args[0];
        String name = (String)args[1];
        String password = (String)args[2];
        String confirmPass = (String)args[3];

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() || email == null) {
            register.emailFailed();
            isValid = false;
        }

        if(name.isEmpty() || name.length() < 4 || name.length() > 8 || name == null){
            register.userNameFailed();
            isValid = false;
        }

        if (password.isEmpty() || password.length() < 5 || password.length() > 10 || password == null) {
            register.passFailed();
            isValid = false;
        }

        if(confirmPass.isEmpty() || !confirmPass.equals(password) || confirmPass == null){
            register.confirmPassFailed();
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
        String name = (String)args[1];
        String pass = (String)args[2];
        String type = (String)args[4];

        iApiAuth api = AuthRetrofitInstance.getInstance().getApi();

        api.getUser(new EmailUser(email)).enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()){
                    ResponseData data = response.body();
                    if(data.getCodeStatus() == 0 && data.getElement() != null){
                        System.out.println(response.body().toString());
                        register.addUserFailed();
                    } else if(type.equals("admin") && data.getCodeStatus() == 1 && data.getElement() == null){
                        getAdmins(api,args);
                    } else {
                        addUserToDb(api,args);
                    }
                } else {
                    register.serverFailed(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                register.serverFailed(t.toString());
            }
        });

/*
        User user = dbHelper.getContact(email);

        if(user == null){
            if(type.equals("admin")){
               int countAdmins = dbHelper.isAdminExist();
               if(countAdmins == 1){
                   register.addAdminFailed();
                   return;
               }
            }

            String salt = EncryptPass.getRandomString();
            String hashPass = EncryptPass.getSecurePassword(pass,salt);

            dbHelper.addUser(new User(email,name,hashPass,type,salt));

        } else {
            register.addUserFailed();
        }
         */
    }

    @Override
    public void addUserToDb(iApiAuth api, String... args) {
        String salt = EncryptPass.getRandomString();
        String hashPass = EncryptPass.getSecurePassword(args[2],salt);
        api.addUser(new User(args[0],args[1],hashPass,args[4],salt)).enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()){
                    ResponseData data = response.body();
                    if(data.getCodeStatus() == 0 ){
                        register.switchLayout();
                        register.messageInvite();
                    }
                } else {
                    register.serverFailed(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                register.serverFailed(t.toString());
            }
        });
    }

    @Override
    public void getAdmins(iApiAuth api, String... args) {
        api.getCountAdmins().enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.isSuccessful()){
                    ResponseData data = response.body();
                    if(data.getCodeStatus() == 0 && data.getElement() != null){
                        CountAdmin countAdmin = new Gson().fromJson(data.getElement(),CountAdmin.class);
                        if(countAdmin.getCount() == 1){
                            register.addAdminFailed();
                        } else {
                            addUserToDb(api,args);
                        }
                    }
                } else {
                    register.serverFailed(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                register.serverFailed(t.toString());
            }
        });
    }
}
