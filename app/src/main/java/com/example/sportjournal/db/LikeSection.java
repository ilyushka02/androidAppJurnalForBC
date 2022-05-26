package com.example.sportjournal.db;

public class LikeSection {
    public static String KEY = "SECTION";
    public String id, name, trainer, time, day;

    public LikeSection() {
    }

    public LikeSection(String str_id, String str_name, String str_trainer, String str_time, String str_day) {
        this.id = str_id;
        this.name = str_name;
        this.trainer = str_trainer;
        this.time = str_time;
        this.day = str_day;
    }

}
