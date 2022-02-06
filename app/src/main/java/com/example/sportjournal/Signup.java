package com.example.sportjournal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Signup extends AppCompatActivity implements View.OnClickListener {
    String[] gender_array = {"не выбрано", "мужской", "женский"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);
        //Заполняем выпадаюдщий список
        Spinner spinner = findViewById(R.id.gender);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gender_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //Находим элементы и устанавливаем на них слушатель нажатий
        Button btn_back = findViewById(R.id.button_back);
        btn_back.setOnClickListener(this);
        ImageButton imgBtn_createUser = findViewById(R.id.user_create);
        imgBtn_createUser.setOnClickListener(this);
    }
    //Обработчик нажатий,
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                openMain();
                break;
            case R.id.user_create:
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
        EditText et_firsName = findViewById(R.id.textFirstName);
        EditText et_secondName = findViewById(R.id.textSecondName);

        if (Function.checkFieldForEmpty(et_firsName) | Function.checkFieldForEmpty(et_lastName)) {
            Log.d(Signup.class.getSimpleName(), "Error: TextField is null");
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Вы не заполнили поля!",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }
    }
}