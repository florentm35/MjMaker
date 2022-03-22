package fr.florent.mjmaker.component;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.service.markdown.EnumMark;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class MarkdownEditor extends LinearLayout  {

    public interface IDeleteEvent {
        void action();
    }

    private TypedArray attrs;

    private AndroidLayoutUtil.ITextEvent handlerTextChanged;

    public MarkdownEditor(Context context) {
        super(context);
        onCreate();
    }

    public MarkdownEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs = getContext().obtainStyledAttributes(attrs, R.styleable.MarkdownEditor);
        onCreate();
    }

    private void onCreate() {
        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.markdown_layout, this);

        // Clear the text changed listener before init value
        AndroidLayoutUtil.clearExtendedEditTextTextChange(this, R.id.et_text);
        Log.d(TAG, "onCreate: "+this);
        AndroidLayoutUtil.setTextViewText(this, R.id.et_text, "");

        // Markdown editor button
        ExtendedEditText editText = this.findViewById(R.id.et_text);
        this.findViewById(R.id.bold).setOnClickListener((v -> applyMarkdownTag(EnumMark.BOLD,editText)));
        this.findViewById(R.id.italic).setOnClickListener((v -> applyMarkdownTag(EnumMark.ITALIC,editText)));
        this.findViewById(R.id.strikethrough).setOnClickListener((v -> applyMarkdownTag(EnumMark.STRICKETHROUGH,editText)));

        this.findViewById(R.id.info).setOnClickListener((v)-> openModalInfo());

        if (attrs != null) {
            boolean showDeleteBtn = attrs.getBoolean(R.styleable.MarkdownEditor_showDelete, true);
            this.findViewById(R.id.delete).setVisibility(showDeleteBtn ? VISIBLE : GONE);
        }

    }


    private void applyMarkdownTag(EnumMark mark, ExtendedEditText editText) {
        int startSelection=editText.getSelectionStart();
        int endSelection=editText.getSelectionEnd();

        String text = editText.getText().toString();

        StringBuilder str = new StringBuilder(text.substring(0, startSelection));
        str.append(mark.getMakdownTag());
        str.append(text.substring(startSelection, endSelection));
        str.append(mark.getMakdownTag());
        str.append(text.substring(endSelection));

        editText.setText(str.toString());
        editText.setSelection(startSelection+mark.getMakdownTag().length());
    }

    private void openModalInfo() {

        Resources res = getContext().getResources();
        InputStream is = res.openRawResource(R.raw.markdown_references);

        String text = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        AndroidLayoutUtil.openModalInfo(getContext(), text);
    }


    public void setDeleteHandler(IDeleteEvent handler) {
        this.findViewById(R.id.delete).setOnClickListener((v -> handler.action()));
    }

    public void setOnTextChanged(AndroidLayoutUtil.ITextEvent handler) {
        this.handlerTextChanged = handler;
        AndroidLayoutUtil.clearExtendedEditTextTextChange(this, R.id.et_text);
        if(handler != null) {
            AndroidLayoutUtil.setExtendedEditTextTextChange(this, R.id.et_text, handler::action);
        }
    }

    public String getText() {
        return AndroidLayoutUtil.getTextViewText(this, R.id.et_text);
    }

    public void setText(String text) {
        AndroidLayoutUtil.clearExtendedEditTextTextChange(this, R.id.et_text);
        AndroidLayoutUtil.setTextViewText(this, R.id.et_text, text);
        if(handlerTextChanged != null) {
            AndroidLayoutUtil.setExtendedEditTextTextChange(this, R.id.et_text, handlerTextChanged::action);
        }
    }
}
