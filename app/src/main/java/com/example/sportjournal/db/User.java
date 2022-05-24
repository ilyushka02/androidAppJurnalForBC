package com.example.sportjournal.db;

public class User {
    public static String USER_KEY = "USER";
    public String id, L_Name, F_Name, S_Name, email, phone, gender, data_birthday, imageURI;

    public User() {
    }

    public User(String id, String l_Name, String f_Name, String s_Name, String email, String phone, String gender, String data_birthday, String imageURI) {
        this.id = id;
        this.L_Name = l_Name;
        this.F_Name = f_Name;
        this.S_Name = s_Name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.data_birthday = data_birthday;
        this.imageURI = imageURI;
    }

}
