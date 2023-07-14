package com.example.iotvandergraaf.model.logn;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("email")
    private String email;

    @SerializedName("name")
    private String name;

    @SerializedName("pass")
    private String pass;

    @SerializedName("privilege")
    private String type;

    @SerializedName("others")
    private String salt;

    public User(String email, String name, String pass, String type, String salt) {
        this.email = email;
        this.name = name;
        this.pass = pass;
        this.type = type;
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                ", type='" + type + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}