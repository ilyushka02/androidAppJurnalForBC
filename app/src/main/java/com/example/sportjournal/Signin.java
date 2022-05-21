package com.example.sportjournal;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sportjournal.AllerDialogs.ErrorConnectorInNetwork;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signin extends AppCompatActivity implements View.OnClickListener {
    private Button btn_back;
    private TextView textView_createAcc;
    private EditText field_login, filed_password;
    private FirebaseAuth mAuth;
    private String login, password;
    Function f = new Function();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signin);
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
        isNetworkAvailable(this);
    }

    //check connection network
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        // get network info for all of the data interfaces (e.g. WiFi, 3G, LTE, etc.)
        NetworkInfo[] info = connectivity.getAllNetworkInfo();

        // make sure that there is at least one interface to test against
        if (info != null) {
            // iterate through the interfaces
            for (int i = 0; i < info.length; i++) {
                // check this interface for a connected state
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    f.FLAG_CONNECTING_NETWORK = true;
                    return true;
                } else {
                    f.FLAG_CONNECTING_NETWORK = false;
                    createDialog();
                    return false;
                }
            }
        }
        f.FLAG_CONNECTING_NETWORK = false;
        return false;
    }

    //create AlertDialog
    public void createDialog(){
        FragmentManager manager = getSupportFragmentManager();
        getSupportFragmentManager();
        ErrorConnectorInNetwork myDialogFragment = new ErrorConnectorInNetwork();
        FragmentTransaction transaction = manager.beginTransaction();
        myDialogFragment.show(transaction, "dialog");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin_btn:
//                openUserActivity(this);
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
            Snackbar.make(findViewById(R.id.body_signin_page), "Логин и пароль не могут быть пустыми!", BaseTransientBottomBar.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(login, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                openUserActivity(Signin.this);
                                finish();
                            } else {
                                Snackbar.make(findViewById(R.id.body_signin_page), "Не правильный логин или пароль!", BaseTransientBottomBar.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void openUserActivity(Context context) {
        Intent intent = new Intent(context, UserActivity.class);
        context.startActivity(intent);
    }
}