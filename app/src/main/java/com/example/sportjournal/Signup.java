package com.example.sportjournal;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sportjournal.db.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class Signup extends AppCompatActivity implements View.OnClickListener {
    private EditText et_lastName, et_firsName, et_secondName, et_dateBirthday, et_email, et_password1, et_password2;
    private Calendar date = Calendar.getInstance();
    private Spinner gender_list;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference users;
    private int FLAG_CHEKED = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);
        initialization();
    }

    //Инициализация компонентов
    private void initialization() {
        //Поиск элементов по id
        et_lastName = findViewById(R.id.textLastName);
        et_firsName = findViewById(R.id.textFirstName);
        et_secondName = findViewById(R.id.textSecondName);
        et_email = findViewById(R.id.email);
        et_password1 = findViewById(R.id.users_password_1);
        et_password2 = findViewById(R.id.users_password_2);
        et_dateBirthday = findViewById(R.id.birthday);
        gender_list = findViewById(R.id.gender);
        ImageButton imgBtn_createUser = findViewById(R.id.user_create);
        Button btn_back = findViewById(R.id.button_back);
        //Заполнение выпадаюдщего списка
        Function.createArrayForSpinner(gender_list, this);
        //Создание маски
        Function.createPatternForDateBirthday(et_dateBirthday);
        //Установка слушателей нажатия
        et_dateBirthday.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        imgBtn_createUser.setOnClickListener(this);
        //Инициализация элементов firebase
        db = FirebaseDatabase.getInstance();
        users = db.getReference(User.USER_KEY);
        mAuth = FirebaseAuth.getInstance();
    }

    //Обработчик нажатий
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                Function.openSignin(this);
                break;
            case R.id.birthday:
                setDate(v);
                break;
            case R.id.user_create:
                createUser();
                break;
        }
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(Signup.this, d,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, monthOfYear);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate();
        }
    };

    //Установка выбранного значения
    private void setInitialDate() {
        et_dateBirthday.setText(DateUtils.formatDateTime(this,
                date.getTimeInMillis(),
                DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    //Создание пользователя
    protected void createUser() {
        String email, first_name, last_name, second_name, birthday, gender, password1, password2;

        first_name = et_firsName.getText().toString().trim();
        last_name = et_lastName.getText().toString().trim();

        if (et_secondName.getText().toString().trim().isEmpty())
            second_name = "не указано";
        else second_name = et_secondName.getText().toString().trim();

        FLAG_CHEKED = et_email.getText().toString().trim().lastIndexOf('@');

        if (FLAG_CHEKED != -1)
            email = et_email.getText().toString().trim();
        else {
            Snackbar.make(findViewById(R.id.body_signup_page), "Не правильный email!", BaseTransientBottomBar.LENGTH_SHORT).show();
            email = "";
        }
        password1 = et_password1.getText().toString().trim();
        password2 = et_password2.getText().toString().trim();
        if (et_dateBirthday.getText().toString().trim().isEmpty())
            birthday = "не указано";
        else birthday = et_dateBirthday.getText().toString().trim();
        if (gender_list.getSelectedItem().toString().trim().isEmpty())
            gender = "не указано";
        else gender = gender_list.getSelectedItem().toString().trim();

        if (first_name.isEmpty() || last_name.isEmpty() || email.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
            Snackbar.make(findViewById(R.id.body_signup_page), "Вы заполнили не все поля!", BaseTransientBottomBar.LENGTH_SHORT).show();
        } else {
            if (password1.equals(password2)) {
                mAuth.createUserWithEmailAndPassword(email, password1)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String id = mAuth.getUid();
                                    User user = new User(id, last_name, first_name, second_name, email, "", gender, birthday, "");
                                    users.child(id).setValue(user);
                                    Snackbar.make(findViewById(R.id.body_signup_page), "Регистрация прошла успешно.", BaseTransientBottomBar.LENGTH_SHORT).show();
                                } else {
                                    Snackbar.make(findViewById(R.id.body_signup_page), "Такой пользователь уже существует!", BaseTransientBottomBar.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Snackbar.make(findViewById(R.id.body_signup_page), "Пароли не совпадают!", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        }
    }
}