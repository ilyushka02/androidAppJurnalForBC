package com.example.sportjournal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Signup extends AppCompatActivity implements View.OnClickListener {
    private EditText et_lastName, et_firsName, et_secondName, et_dateBirthday, et_email, et_password1, et_password2;
    private Spinner gender_list;
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    private String USER_KEY = "USER";
    private int FLAG_CHEKED = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);
        initialization_component();
    }

    private void initialization_component() {
        //Search elements on activity by id
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
        //Заполняем выпадаюдщий список
        Function.createArrayForSpinner(gender_list, this);
        //Создаем маску для дня рождения
        Function.createPatternForDateBirthday(et_dateBirthday);
        //Находим элементы и устанавливаем на них слушатель нажатий
        btn_back.setOnClickListener(this);
        imgBtn_createUser.setOnClickListener(this);
        db = FirebaseDatabase.getInstance().getReference(USER_KEY);
        mAuth = FirebaseAuth.getInstance();
    }

    //Обработчик нажатий,
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                Function.openSignin(this);
                break;
            case R.id.user_create:
                createUser();
                break;
        }
    }

    protected void createUser() {
        String id = db.getKey();
        String first_name = et_firsName.getText().toString().trim();
        String last_name = et_lastName.getText().toString().trim();
        String second_name;
        if (et_secondName.getText().toString().trim().isEmpty()){
            second_name = "не указано";
        } else {
            second_name = et_secondName.getText().toString().trim();
        }
        String email;
        FLAG_CHEKED = et_email.getText().toString().trim().lastIndexOf('@');
        if (FLAG_CHEKED != -1){
            email = et_email.getText().toString().trim();
        } else {
            Function.createToast(this, "Error: не правильный email!");
            email = "";
        }
        String password1 = et_password1.getText().toString().trim();
        String password2 = et_password2.getText().toString().trim();
        String birthday;
        if (et_dateBirthday.getText().toString().trim().isEmpty()){
            birthday = "не указано";
        } else {
            birthday = et_dateBirthday.getText().toString().trim();
        }
        String gender;
        if (gender_list.getSelectedItem().toString().trim().isEmpty()){
            gender = "не указано";
        } else {
            gender = gender_list.getSelectedItem().toString().trim();
        }

        if (first_name.isEmpty() || last_name.isEmpty() || email.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
            Function.createToast(this, "Error: вы заполнили не все поля!");
        } else {
            if (password1.equals(password2)) {
                mAuth.createUserWithEmailAndPassword(email, password1)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Function.openSignin(Signup.this);
                                    Function.createToast(Signup.this, "Successful!");
                                    User user = new User(id, last_name, first_name, second_name, email, gender, birthday);
                                    db.push().setValue(user);
                                } else {
                                    Function.createToast(Signup.this, "Error signup!");
                                }
                            }
                        });
            } else {
                Function.createToast(this, "Error: пароли не совпадают!");
            }
        }
    }
}