package com.example.sportjournal;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.slots.Slot;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class Function extends AppCompatActivity{
    public static boolean FLAG_CONNECTING_NETWORK;
    //functions for open activity
    public static void openSignin(Context context) {
        Intent intent = new Intent(context, Signin.class);
        context.startActivity(intent);
    }

    //create AlertDialog
    public void createDialog(){
        FragmentManager manager = getSupportFragmentManager();
        getSupportFragmentManager();
        Dialog myDialogFragment = new Dialog();
        FragmentTransaction transaction = manager.beginTransaction();
        myDialogFragment.show(transaction, "dialog");
    }

    public static void openSignup(Context context) {
        Intent intent = new Intent(context, Signup.class);
        context.startActivity(intent);
    }

    //function for create toast
    //Заменить тосты на снеки!!!!!!!!!!!
    public static void createToast(Activity activity, String text){
//        Snackbar.make(view, text, BaseTransientBottomBar.LENGTH_SHORT).show();
        Toast toast = Toast.makeText( activity, text, LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    //functions for create pattern
    public static void createPatternForDateBirthday(EditText date){
        Slot[] slots = new UnderscoreDigitSlotsParser().parseSlots("__.__.____");
        FormatWatcher formatWatcher = new MaskFormatWatcher(
                MaskImpl.createTerminated(slots)
        );
        formatWatcher.installOn(date);
    }
    public static void createPatternForSMSCode(EditText sms_code){
        Slot[] slots = new UnderscoreDigitSlotsParser().parseSlots("___-___");
        FormatWatcher formatWatcher = new MaskFormatWatcher(
                MaskImpl.createTerminated(slots)
        );
        formatWatcher.installOn(sms_code);
    }

    public static void createPatternForPhoneNumber(EditText number){
        FormatWatcher formatWatcher = new MaskFormatWatcher(
                MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
        );
        formatWatcher.installOn(number);
    }

    //function for write in array
    public static  void createArrayForSpinner(Spinner spinner, Activity activity){
        String[] gender_array = {"не выбрано", "мужской", "женский"};
        ArrayAdapter<String> adapter = new ArrayAdapter(activity, android.R.layout.simple_spinner_item, gender_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
