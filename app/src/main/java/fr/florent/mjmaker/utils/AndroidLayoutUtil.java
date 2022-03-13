package fr.florent.mjmaker.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.component.ExtendedEditText;

/**
 * Util class for Android binding data
 */
public abstract class AndroidLayoutUtil {

    public interface ITextWithBooleanReturnEvent {
        boolean action(String value);
    }

    public interface ITextEvent {
        void action(String value);
    }

    public interface IBooleanWithBooleanReturnEvent {
        boolean action(boolean choice);
    }

    public static void setTextViewText(View view, int id, String text) {
        ((TextView) view.findViewById(id)).setText(text != null ? text : "");
    }

    public static void setTextViewText(View view, int id, Spanned text) {
        ((TextView) view.findViewById(id)).setText(text != null ? text : "");
    }

    public static void setTextViewText(View view, int id, Integer text) {
        ((TextView) view.findViewById(id)).setText(text != null ? text.toString() : "");
    }

    public static String getTextViewText(View view, int id) {
        return ((TextView) view.findViewById(id)).getText().toString();
    }

    public static Integer getTextViewInteger(View view, int id) {
        String value = ((TextView) view.findViewById(id)).getText().toString();

        return value != null && !value.isEmpty() ? Integer.parseInt(value) : null;
    }


    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    public static void openModalAskText(Context context,
                                        String title,
                                        String value,
                                        ITextWithBooleanReturnEvent onValidate
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.modal_ask_text, null);

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

                        if (onValidate.action(AndroidLayoutUtil.getTextViewText(view, R.id.value))) {
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


    public static void openModalQuestion(Context context,
                                         String question,
                                         IBooleanWithBooleanReturnEvent onValidate
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.modal_empty, null);

        builder.setView(view);

        // Set the dialog title
        builder.setTitle(question)
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        if (onValidate.action(true)) {
                            dialog.cancel();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (onValidate.action(false)) {
                            dialog.cancel();
                        }
                    }
                });

        builder.create()
                .show();
    }

    public static void openModalInfo(Context context,
                                         String text
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.modal_text, null);

        AndroidLayoutUtil.setTextViewText(view, R.id.tv_text, Html.fromHtml(text, Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM));

        builder.setView(view);

        // Set the dialog title
        builder.setTitle("")
                // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.create()
                .show();
    }

    public static void clearExtendedEditTextTextChange(View view, int id) {
        ExtendedEditText editText = (ExtendedEditText) view.findViewById(id);
        editText.clearTextChangedListeners();
    }

    public static void setExtendedEditTextTextChange(View view, int id, ITextEvent onChange) {
        ExtendedEditText editText = (ExtendedEditText) view.findViewById(id);
        editText.addTextChangedListener(new TextWatcher() {

            String oldText = null;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                oldText = charSequence.toString();
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(oldText == null || !oldText.equals(charSequence.toString())) {
                    onChange.action(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Do nothing
            }
        });
    }

}
