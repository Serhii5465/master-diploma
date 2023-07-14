package com.example.iotvandergraaf.model.logn;

import com.google.gson.annotations.SerializedName;

public class EmailUser {

    @SerializedName("email")
    private String email;

    public EmailUser(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "EmailUser{" +
                "email='" + email + '\'' +
                '}';
    }
}
