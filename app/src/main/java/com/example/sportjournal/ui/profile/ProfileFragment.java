package com.example.sportjournal.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportjournal.R;
import com.example.sportjournal.UpdateActivity;
import com.example.sportjournal.UserActivity;
import com.example.sportjournal.db.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment{
    private DatabaseReference users;
    private ProfileViewModel galleryViewModel;
    TextView username, birthday, gender, email, phone;
    Button update;
    ImageView avatar;
    View root;
    User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        initialization();
        getDataBase();
        return root;
    }

    private void initialization() {
        users = FirebaseDatabase.getInstance().getReference(User.USER_KEY);
        username = (TextView) root.findViewById(R.id.ProfileUserName);
        birthday = (TextView) root.findViewById(R.id.ProfileBirthday);
        gender = (TextView) root.findViewById(R.id.ProfileGender);
        email = (TextView) root.findViewById(R.id.ProfileEmail);
        phone = (TextView) root.findViewById(R.id.ProfilePhone);
        avatar = (ImageView) root.findViewById(R.id.ProfileAvatar);
        update = (Button) root.findViewById(R.id.updateProfile);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity();
            }
        });
    }

    private void openActivity() {
        Intent intent = new Intent(this.getActivity(), UpdateActivity.class);
        startActivity(intent);
    }

    private void getDataBase() {
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.child(UserActivity.userID).getValue(User.class);
                if (!user.imageURI.isEmpty()) Picasso.get().load(user.imageURI).into(avatar);
                username.setText(user.L_Name + " " + user.F_Name + " " + user.S_Name);
                birthday.setText("дата рождения: " + user.data_birthday);
                gender.setText("пол: " + user.gender);
                email.setText(user.email);
                phone.setText("тел: " + user.phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}