package com.example.sportjournal;

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
    private Button btn_back;
    private TextView textView_createAcc;
    private EditText field_login, filed_password;
    private FirebaseAuth mAuth;
    private String login, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initialization_component();
    }

    private void initialization_component() {
        //Search elements on activity by id
        btn_back = findViewById(R.id.signin_btn);
        textView_createAcc = findViewById(R.id.textCreateNewAccount);
        field_login = findViewById(R.id.textFieldLogin);
        filed_password = findViewById(R.id.textFieldPassword);
        //firebase object initialization
        mAuth = FirebaseAuth.getInstance();
        //Find elements and set onClick listener
        btn_back.setOnClickListener(this);
        textView_createAcc.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin_btn:
                authorization();
                break;
            case R.id.textCreateNewAccount:
                Function.openSignup(this);
                break;
        }
    }

    private void authorization() {
        login = field_login.getText().toString().trim();
        password = filed_password.getText().toString().trim();
        if (login.isEmpty() || password.isEmpty()) {
            Function.createToast(SigninActivity.this, "Логин и пароль не могут быть пустыми.");
        } else {
            mAuth.signInWithEmailAndPassword(login, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Function.openUserActivity(SigninActivity.this);
                            } else {
                                Function.createToast(SigninActivity.this, "Не правильный логин или пароль");
                            }
                        }
                    });
        }
    }
}