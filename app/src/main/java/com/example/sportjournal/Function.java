package com.example.sportjournal;

import android.widget.EditText;

public class Function {
    public static Boolean checkFieldForEmpty(EditText text) {
        Boolean status = false;
        String t = text.getText().toString().trim();
        if (t.length() == 0) {
            status = true;
        }
        return status;
    }
}
