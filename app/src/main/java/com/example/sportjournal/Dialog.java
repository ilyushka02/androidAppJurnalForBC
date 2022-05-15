package com.example.sportjournal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog extends AppCompatDialogFragment {
    Function f = new Function();
    Signin s = new Signin();
    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Ошибка! Подключение к интернету отсутствует!")
                .setIcon(R.drawable.warning_icon)
                .setMessage("Проверьте подключение и повторите попытку!")
                .setPositiveButton("Попробовать снова", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (Function.FLAG_CONNECTING_NETWORK){
                            dialog.cancel();
                        } else {
                          isNetworkAvailable(getContext());
                        }
                    }
                });
        return builder.create();
    }



    //check connection network
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = connectivity.getAllNetworkInfo();
        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    f.FLAG_CONNECTING_NETWORK = true;
                    return true;
                } else {
                    f.FLAG_CONNECTING_NETWORK = false;
                    s.createDialog();
                    return false;
                }
            }
        }
        f.FLAG_CONNECTING_NETWORK = false;
        s.createDialog();
        return false;
    }
}
