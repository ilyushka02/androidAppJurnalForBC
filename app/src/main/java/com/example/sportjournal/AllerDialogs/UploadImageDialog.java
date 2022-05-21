package com.example.sportjournal.AllerDialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.sportjournal.Function;
import com.example.sportjournal.R;
import com.example.sportjournal.Signin;

public class UploadImageDialog extends AppCompatDialogFragment {
    Function f = new Function();
    Signin s = new Signin();
    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Внимание!")
                .setIcon(R.drawable.warning_icon)
                .setMessage("Вы действительно хотите изменить фото профиля?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getActivity(), "OK!", Toast.LENGTH_LONG)
                        .show();
            }
        });
        builder.setCancelable(true);;

        return builder.create();
    }
}
