package com.saran.logindatabasefirebase;

/**
 * Created by core I5 on 4/7/2017.
 */

public class User {
    private String name;
    private String age;
    private String email;
    private String address;
    private String password;

    public User(){}

    public User(String mName, String mAge, String mEmail, String mAddress, String mPassword){
        name = mName;
        age = mAge;
        email = mEmail;
        address = mAddress;
        password = mPassword;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }
}
