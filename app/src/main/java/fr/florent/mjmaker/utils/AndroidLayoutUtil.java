package fr.florent.mjmaker.utils;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Util class for Android binding data
 */
public abstract class AndroidLayoutUtil {

    public static String getTextFromEditText(View view, int id) {
        return ((EditText) view.findViewById(id)).getText().toString();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
