package TP2.Models;

public class User {
    Integer user_id;
    String username;
    String password;

    User(Integer user_id, String username, String password){
        this.user_id = user_id;
        this.username = username;
        this.password = password;
    }
}
