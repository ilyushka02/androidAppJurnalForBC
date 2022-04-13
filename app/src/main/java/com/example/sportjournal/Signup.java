package com.example.sportjournal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;


public class Signup extends AppCompatActivity implements View.OnClickListener {
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);
        //firebase object initialization
        mAuth = FirebaseAuth.getInstance();
        //Заполняем выпадаюдщий список
        Spinner spinner = findViewById(R.id.gender);
        Function.createArrayForSpinner(spinner, this);
        //Создаем маску для дня рождения
        EditText et_dateBirthday = findViewById(R.id.birthday);
        Function.createPatternForDateBirthday(et_dateBirthday);
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
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
    }
   
    protected void createUser() {
        //Находим элементы и забираем с них текст
//        EditText et_lastName = findViewById(R.id.textLastName);
//        EditText et_firsName = findViewById(R.id.textFirstName);
//        EditText et_secondName = findViewById(R.id.textSecondName);
//
//        if (Function.checkFieldForEmpty(et_firsName) | Function.checkFieldForEmpty(et_lastName)) {
//            Log.d(Signup.class.getSimpleName(), "Error: TextField is null");
//            Toast toast = Toast.makeText(getApplicationContext(),
//                    "Вы не заполнили поля!",
//                    Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//            toast.show();
//        }
    }
}