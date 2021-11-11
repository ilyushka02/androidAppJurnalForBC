package com.example.sportjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private  TextView textView_createAcc;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        addListener();
    }

    public  void  addListener(){
        textView_createAcc =(TextView) findViewById(R.id.textCreateNewAccount);
        btn = (Button)  findViewById(R.id.signin_btn);
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("android.intent.action.Signup");
                        startActivity(intent);
                    }
                }
        );

        textView_createAcc.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("android.intent.action.Signup");
                        startActivity(intent);
                    }
                }
        );
    }

}