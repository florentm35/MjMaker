package fr.florent.mjmaker.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.florent.mjmaker.R;

/**
 * Util class for Android binding data
 */
public abstract class AndroidLayoutUtil {

    public static void setTextViewText(View view, int id, String text) {
        ((TextView) view.findViewById(id)).setText(text);
    }

    public static String getTextViewText(View view, int id) {
        return ((TextView) view.findViewById(id)).getText().toString();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public interface IModalAskTextEvent {
        boolean action(String value);
    }

    public static void openModalAskText(Context context,
                                        LayoutInflater inflater,
                                        String title,
                                        String value,
                                        IModalAskTextEvent onValidate
                                        ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = inflater.inflate(R.layout.modal_ask_text, null);

        if (value != null) {
            AndroidLayoutUtil.setTextViewText(view, R.id.value, value);
        }
        builder.setView(view);

        // Set the dialog title
        builder.setTitle(title)
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        if(onValidate.action(AndroidLayoutUtil.getTextViewText(view, R.id.value))) {
                            dialog.cancel();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.create()
                .show();
    }

}
