package com.example.sportjournal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sportjournal.db.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class UserActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference users;
    private StorageReference storageRef;
    private AppBarConfiguration mAppBarConfiguration;
    private TextView lastName, firstName, secondName;
    private ImageView avatar1;
    public static String userID;
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
                R.id.nav_home, R.id.nav_profile, R.id.nav_contacts, R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void initialization() {
        //Поиск элементов по id
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        firstName = (TextView) headerView.findViewById(R.id.userFirstName);
        lastName = (TextView) headerView.findViewById(R.id.userLastName);
        secondName = (TextView) headerView.findViewById(R.id.userSecondName);
        avatar1 = (ImageView) headerView.findViewById(R.id.userAvatar);
        mAuth = FirebaseAuth.getInstance();
        users = FirebaseDatabase.getInstance().getReference(User.USER_KEY);
        //Получение id авторизованного пользователя
        userID = mAuth.getCurrentUser().getUid();
        storageRef = FirebaseStorage.getInstance().getReference(userID);
    }

    //Получение данных из БД
    private void getDataBase() {
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.child(userID).getValue(User.class);
                if (!user.image.isEmpty()) Picasso.get().load(user.image).into(avatar1);
                firstName.setText(user.first);
                lastName.setText(user.last);
                secondName.setText(user.second);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    //Открытие адреса спортивного учереждения в гугл картах
    public void OpenAddress(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=Южное ш., 2Г, Нижний Новгород, Россия"));
        startActivity(intent);
    }

    /////////////////////////////////////////////////
    //Функции для подстановки номера в поле вызова//
    ///////////////////////////////////////////////
    public void openPhone1(View view) {
        String dial = "tel:+7(910)880-87-98";
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
    }

    public void openPhone2(View view) {
        String dial = "tel:+7(910)880-96-47";
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
    }
}