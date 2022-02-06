package com.example.sportjournal;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.slots.Slot;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class Function {

    public static Boolean checkFieldForEmpty(EditText text) {
        Boolean status = false;
        String t = text.getText().toString().trim();
        if (t.length() == 0) {
            status = true;
        }
        return status;
    }

    public static void createPatternForDateBirthday(EditText date){
        Slot[] slots = new UnderscoreDigitSlotsParser().parseSlots("__.__.____");
        FormatWatcher formatWatcher = new MaskFormatWatcher(
                MaskImpl.createTerminated(slots) // маска для серии и номера
        );
        formatWatcher.installOn(date);
    }

    public static void createPatternForPhoneNumber(EditText date){
        FormatWatcher formatWatcher = new MaskFormatWatcher(
                MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER) // маска для серии и номера
        );
        formatWatcher.installOn(date);
    }

    public static  void createArrayForSpinner(Spinner spinner, Activity activity){
        String[] gender_array = {"не выбрано", "мужской", "женский"};
        ArrayAdapter<String> adapter = new ArrayAdapter(activity, android.R.layout.simple_spinner_item, gender_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
