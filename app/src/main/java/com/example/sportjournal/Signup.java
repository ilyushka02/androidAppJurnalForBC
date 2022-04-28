package com.example.sportjournal;

import android.content.Intent;
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
    private EditText et_lastName, et_firsName, et_secondName, et_dateBirthday, et_email, et_password;
    private Spinner gender_list;
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    private String USER_KEY = "USER";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);
        initialithation_component();
    }

    private void initialithation_component(){
        //Search elements on activity by id
        et_lastName = findViewById(R.id.textLastName);
        et_firsName = findViewById(R.id.textFirstName);
        et_secondName = findViewById(R.id.textSecondName);
        et_email = findViewById(R.id.email);
        et_password = findViewById(R.id.users_password_1);
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
        String id = db.getKey();
        String first_name = et_firsName.getText().toString().trim();
        String last_name = et_lastName.getText().toString().trim();
        String second_name = et_secondName.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String birthday = et_dateBirthday.getText().toString().trim();
        String gender = gender_list.getSelectedItem().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Function.createToast(Signup.this, "Good jopa");
                            User user = new User(id, last_name, first_name, second_name, email, gender, birthday);
                            db.push().setValue(user);
                        } else {
                            Function.createToast(Signup.this, "Error");
                        }
                    }
                });

        //Находим элементы и забираем с них текст
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
    private void openSignin() {
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
    }
}