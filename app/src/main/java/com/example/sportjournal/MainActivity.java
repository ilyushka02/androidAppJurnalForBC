package com.example.sportjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private  TextView textView_createAcc;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        //Находим элементы и устанавливаем на них слушатель нажатий
        Button btn_back = findViewById(R.id.signin_btn);
        btn_back.setOnClickListener(this);
        TextView textView_createAcc = findViewById(R.id.textCreateNewAccount);
        textView_createAcc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signin_btn:
                Log.d(MainActivity.class.getSimpleName() ,"Sign in btn detected ------------- click click click ");
                break;
            case R.id.textCreateNewAccount:
                openSignup();
                break;
        }
    }

    private void openSignup() {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }
}