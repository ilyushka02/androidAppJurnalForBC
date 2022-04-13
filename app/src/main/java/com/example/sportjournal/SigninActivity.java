package com.example.sportjournal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView_createAcc;
    private Button btn;
    private FirebaseAuth mAuth;
    String login, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        //firebase object initialization
        mAuth = FirebaseAuth.getInstance();
        //Find elements and set onClick listener
        Button btn_back = findViewById(R.id.signin_btn);
        btn_back.setOnClickListener(this);
        TextView textView_createAcc = findViewById(R.id.textCreateNewAccount);
        textView_createAcc.setOnClickListener(this);
        //Create pattern with login field
//        Function.createPatternForPhoneNumber(field_login);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signin_btn:
                Authorization();
                break;
            case R.id.textCreateNewAccount:
                openSignup();
                break;
        }
    }
    private void Authorization(){
        //Find by id element
        EditText field_login = findViewById(R.id.textFieldLogin);
        login = field_login.getText().toString().trim();
        EditText filed_password = findViewById(R.id.textFieldPassword);
        password = filed_password.getText().toString().trim();
        if (login.isEmpty() || password.isEmpty()){
            Function.createToast(SigninActivity.this, "Логин и пароль не могут быть пустыми.");
        } else {
            mAuth.signInWithEmailAndPassword(login, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                openUserActivity();
                            } else {
                                Function.createToast(SigninActivity.this, "Не правильный логин или пароль");
                            }
                        }
                    });
        }
    }

    private void openSignup() {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }

    private void openUserActivity() {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }
}