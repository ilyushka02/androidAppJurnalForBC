package com.example.sportjournal;

public class User {
    public static String USER_KEY = "USER";
    public String id, L_Name, F_Name, S_Name, email, gender, data_birthday;

    public User() {
    }

    public User(String id, String l_Name, String f_Name, String s_Name, String email, String gender, String data_birthday) {
        this.id = id;
        L_Name = l_Name;
        F_Name = f_Name;
        S_Name = s_Name;
        this.email = email;
        this.gender = gender;
        this.data_birthday = data_birthday;
    }

}
