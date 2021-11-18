package com.example.sportjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Signup extends AppCompatActivity implements View.OnClickListener {
//    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);
        //Находим элементы и устанавливаем на них слушатель нажатий
        Button btn_back = findViewById(R.id.button_back);
        btn_back.setOnClickListener(this);
        ImageButton imgBtn_createUser =findViewById(R.id.user_create);
        imgBtn_createUser.setOnClickListener(this);
    }

    private void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_back:
                openMain();
                break;
            case R.id.user_create:
                Log.d(MainActivity.class.getSimpleName() ,"Create btn detected ------------- click click click");
                break;
        }
    }
}