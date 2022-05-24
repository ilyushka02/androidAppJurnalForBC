package com.example.sportjournal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sportjournal.db.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference users;
    private StorageReference storageRef;
    private Calendar date = Calendar.getInstance();
    EditText username_f, username_l, username_s, birthday, phone;
    Button takeImg, updateUser;
    private Spinner gender;
    ImageView avatar;
    User user;
    private Uri uploadPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getSupportActionBar().hide();
        initialization();
        getDataBase();
    }

    private void initialization() {
        users = FirebaseDatabase.getInstance().getReference(User.USER_KEY);
        username_f = findViewById(R.id.Update_firststName);
        username_l = findViewById(R.id.Update_lastName);
        username_s = findViewById(R.id.Update_secondName);
        birthday = findViewById(R.id.Update_Birthday);
        gender = findViewById(R.id.Update_Gender);
        phone = findViewById(R.id.Update_Phone);
        Function.createPatternForPhoneNumber(phone);
        avatar = findViewById(R.id.Update_imgProfile);
        updateUser = findViewById(R.id.Update_saveUser);
        takeImg = findViewById(R.id.Update_takeImg);
        birthday.setOnClickListener(this);
        updateUser.setOnClickListener(this);
        takeImg.setOnClickListener(this);
        storageRef = FirebaseStorage.getInstance().getReference(UserActivity.userID);
        Function.createArrayForSpinner(gender, this);
        Function.createPatternForDateBirthday(birthday);
    }

    private void getDataBase() {
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.child(UserActivity.userID).getValue(User.class);
                if (!user.imageURI.isEmpty()) Picasso.get().load(user.imageURI).into(avatar);
                username_f.setText(user.F_Name);
                username_l.setText(user.L_Name);
                username_s.setText(user.S_Name);
                birthday.setText(user.data_birthday);
                phone.setText(user.phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void openGallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 1) && (data != null) && (data.getData() != null)) {
            if (resultCode == RESULT_OK) {
                avatar.setImageURI(data.getData());
                uploadImg();
            }
        }
    }

    private void uploadImg() {
        Bitmap bitmap = ((BitmapDrawable) avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        StorageReference usRef = storageRef.child("image" + System.currentTimeMillis());
        UploadTask up = usRef.putBytes(bytes);
        Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return usRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                uploadPath = task.getResult();
            }
        });
    }


    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog( UpdateActivity.this, d,
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


    private void setInitialDate() {
        birthday.setText(DateUtils.formatDateTime(this,
                date.getTimeInMillis(),
                DateUtils.FORMAT_NUMERIC_DATE| DateUtils.FORMAT_SHOW_YEAR));
    }

    private void saveUser() {
        users.child(UserActivity.userID).child("L_Name ").setValue(username_l.getText().toString().trim());
        users.child(UserActivity.userID).child("F_Name ").setValue(username_f.getText().toString().trim());
        users.child(UserActivity.userID).child("S_Name ").setValue(username_s.getText().toString().trim());
        users.child(UserActivity.userID).child("phone").setValue(phone.getText().toString().trim());
        users.child(UserActivity.userID).child("gender").setValue(gender.getSelectedItem().toString().trim());
        users.child(UserActivity.userID).child("data_birthday").setValue(birthday.getText().toString().trim());
        users.child(UserActivity.userID).child("imageURI").setValue(uploadPath.toString());
//        Intent intent = new Intent(this, UserActivity.class);
//        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Update_Birthday:
                setDate(view);
                break;
            case R.id.Update_takeImg:
                openGallery(view);
                break;
            case R.id.Update_saveUser:
                saveUser();
                break;
        }
    }
}