package com.example.myapplication.database;

import com.example.myapplication.model.User;

import java.util.List;

public interface TableUsers {
    long insertData(User user);
    List<User> getUsersNames();
    List<User> getUsers();
}
