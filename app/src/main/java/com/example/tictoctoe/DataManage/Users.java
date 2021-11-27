package com.example.tictoctoe.DataManage;

public class Users {

    public String email;
    public String password;

    public Users() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Users(String email,String password) {
        this.email = email;
        this.password = password;
    }

}
