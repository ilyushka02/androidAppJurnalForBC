package com.example.sportjournal;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sportjournal.db.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
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

public class UserActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference users;
    private StorageReference storageRef;
    private AppBarConfiguration mAppBarConfiguration;
    private Button btn_update;
    private TextView lastName, firstName, secondName;
    private ImageView avatar1, avatar2;
    public static String userID;
    private Uri uploadPath;
    private Fragment profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        initialization();
        getDataBase();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_contacts)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    private void initialization() {
        // подключаем FragmentManager
        profile = getFragmentManager().findFragmentById(R.id.nav_profile);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        firstName = (TextView) headerView.findViewById(R.id.userFirstName);
        lastName = (TextView) headerView.findViewById(R.id.userLastName);
        secondName = (TextView) headerView.findViewById(R.id.userSecondName);
        avatar1 = (ImageView) headerView.findViewById(R.id.userAvatar);
        btn_update = (Button) headerView.findViewById(R.id.updateProfile);
        mAuth = FirebaseAuth.getInstance();
        users = FirebaseDatabase.getInstance().getReference(User.USER_KEY);
        //Получение id авторизованного пользователя
        userID = mAuth.getCurrentUser().getUid();
        storageRef = FirebaseStorage.getInstance().getReference(userID);
    }

    private void getDataBase() {
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.child(userID).getValue(User.class);
                Picasso.get().load(user.imageURI).into(avatar1);
                firstName.setText(user.F_Name);
                lastName.setText(user.L_Name);
                secondName.setText(user.S_Name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        getSupportActionBar().setTitle("Главная");
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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
                avatar1.setImageURI(data.getData());
                uploadImg();
            }
        }
    }

    private void uploadImg() {
        Bitmap bitmap = ((BitmapDrawable) avatar1.getDrawable()).getBitmap();
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
                saveUser();
            }
        });
    }

    private void saveUser() {
        users.child(userID).child("imageURI").setValue(uploadPath.toString());
    }

    public void OpenAddress(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=Южное ш., 2Г, Нижний Новгород, Россия"));
        startActivity(intent);
    }

    public void openPhone1(View view) {
        String dial = "tel:+7(910)880-87-98";
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
    }

    public void openPhone2(View view) {
        String dial = "tel:+7(910)880-96-47";
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
    }
}