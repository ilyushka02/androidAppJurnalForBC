package com.example.sportjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;

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
        ImageButton imgBtn_createUser = findViewById(R.id.user_create);
        imgBtn_createUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                openMain();
                break;
            case R.id.user_create:
                Log.d(MainActivity.class.getSimpleName(), "Create btn detected ------------- click click click");
                createUser();
                break;
        }
    }

    private void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
   
    protected void createUser() {
        //Находим элементы и забираем с них текст
        EditText et_lastName = findViewById(R.id.textLastName);
//        Log.d(Signup.class.getSimpleName() ,"Last name ------------- " + et_lastName.getText());
        EditText et_firsName = findViewById(R.id.textFirstName);
//        Log.d(Signup.class.getSimpleName() ,"First name ------------- " + et_firsName.getText());
        EditText et_secondName = findViewById(R.id.textSecondName);
//        Log.d(Signup.class.getSimpleName() ,"Second name ------------- " + et_secondName.getText());

        if (et_lastName.length() == 0 | et_firsName.length() == 0) {
            Log.d(Signup.class.getSimpleName(), "Error ------------- ");
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Вы не заполнили поля", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}