package com.example.sportjournal;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.Activity;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.slots.Slot;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class Function {
    //    if field empty return true
    //    if field is not empty return flase
    public static Boolean checkFieldForEmpty(EditText text) {
        Boolean status = false;
        String t = text.getText().toString().trim();
        if (t.length() == 0) {
            status = true;
        }
        return status;
    }

    public static void createToast(Activity activity, String text){
        Toast toast = Toast.makeText( activity, text, LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

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

    public static  void createArrayForSpinner(Spinner spinner, Activity activity){
        String[] gender_array = {"не выбрано", "мужской", "женский"};
        ArrayAdapter<String> adapter = new ArrayAdapter(activity, android.R.layout.simple_spinner_item, gender_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
