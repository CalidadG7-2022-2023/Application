package com.example.myapplication.model;

public class User {
    private String name;
    private String password;
    private String repassword;

    public User(String name, String password, String repassword) {
        this.name = name;
        this.password = password;
        this.repassword = repassword;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }
}
//