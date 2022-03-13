package fr.florent.mjmaker.fragment.scenario.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.component.ExtendedEditText;
import fr.florent.mjmaker.fragment.scenario.ScenarioFragment;
import fr.florent.mjmaker.service.markdown.EnumMark;
import fr.florent.mjmaker.service.markdown.MarkDownService;
import fr.florent.mjmaker.service.model.FieldSetElement;
import fr.florent.mjmaker.service.model.TextElement;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;
import lombok.Getter;
import lombok.Setter;

public class FieldSetElementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final MarkDownService markDownService = MarkDownService.getInstance();

    private static final String TAG = FieldSetElementAdapter.class.getName();

    private List<FieldSetElement> lstElement;
    private final Context context;
    private final IHandlerAction handler;

    public enum EnumAction {
        UPDATE, DELETE;
    }


    @Setter
    ScenarioFragment.EnumState state;

    public interface IHandlerAction {
        void update(EnumAction action, FieldSetElement element);
    }

    public FieldSetElementAdapter(Context context, List<FieldSetElement> lstElement, ScenarioFragment.EnumState state, IHandlerAction handler) {
        this.context = context;
        this.lstElement = lstElement;
        this.state = state;
        this.handler = handler;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layoutId = 0;

        switch (ViewHolder.EnumViewType.getValue(viewType)) {

            case TEXT:
                layoutId = R.layout.text_element_row;
                break;
            case ENTITY:
            case BATTLE:
            case MAP:
                throw new RuntimeException("Not implemented");
        }

        return new ViewHolder(
                LayoutInflater.from(context).inflate(layoutId, parent, false)
                , viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        FieldSetElement element = lstElement.get(position);
        View view = holder.itemView;
        switch (((ViewHolder) holder).getViewType()) {

            case TEXT:
                TextElement textElement = (TextElement) element.getElement();
                switch (state) {
                    case VIEW:
                        Spanned text = Html.fromHtml(markDownService.parseMarkDown(textElement.getText()), Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM);
                        AndroidLayoutUtil.setTextViewText(view, R.id.tv_text, text);
                        view.findViewById(R.id.tv_text).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.ll_editeur).setVisibility(View.GONE);
                        view.findViewById(R.id.layout_actions).setVisibility(View.GONE);
                        break;
                    case EDIT:
                        // Clear the text changed listener before init value
                        AndroidLayoutUtil.clearExtendedEditTextTextChange(view, R.id.et_text);
                        AndroidLayoutUtil.setTextViewText(view, R.id.et_text, textElement.getText());
                        AndroidLayoutUtil.setExtendedEditTextTextChange(view, R.id.et_text, (value) -> {
                            Log.d(TAG, String.format("old value : %s, new value : %s", textElement.getText(), value));
                            textElement.setText(value);
                            handler.update(EnumAction.UPDATE, element);
                        });


                        view.findViewById(R.id.delete).setOnClickListener((v -> handler.update(EnumAction.DELETE, element)));

                        // Markdown editor button
                        ExtendedEditText editText = view.findViewById(R.id.et_text);
                        view.findViewById(R.id.bold).setOnClickListener((v -> applyMarkdownTag(EnumMark.BOLD,editText)));
                        view.findViewById(R.id.italic).setOnClickListener((v -> applyMarkdownTag(EnumMark.ITALIC,editText)));
                        view.findViewById(R.id.underline).setOnClickListener((v -> applyMarkdownTag(EnumMark.UNDERLINE,editText)));
                        view.findViewById(R.id.strikethrough).setOnClickListener((v -> applyMarkdownTag(EnumMark.STRICKETHROUGH,editText)));

                        view.findViewById(R.id.info).setOnClickListener((v)-> openModalInfo());

                        view.findViewById(R.id.ll_editeur).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.tv_text).setVisibility(View.GONE);
                        view.findViewById(R.id.layout_actions).setVisibility(View.VISIBLE);

                        break;
                }

                break;
            case ENTITY:
            case BATTLE:
            case MAP:
                throw new RuntimeException("Not implemented");
        }

    }

    private void openModalInfo() {

        Resources res = context.getResources();
        InputStream is = res.openRawResource(R.raw.markdown_references);

        String text = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        AndroidLayoutUtil.openModalInfo(context, text);
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

    @Override
    public int getItemViewType(int position) {

        ViewHolder.EnumViewType type = ViewHolder.EnumViewType.getValue(lstElement.get(position).getDType());

        if (type != null) {
            return type.getViewType();
        } else {
            throw new RuntimeException("Not implemented");
        }
    }

    @Override
    public int getItemCount() {
        return lstElement.size();
    }

    public void setItems(List<FieldSetElement> items) {
        lstElement = items;
    }

    public void updateItem(FieldSetElement value) {
        notifyItemChanged(lstElement.indexOf(value));
    }

    public void removeItem(FieldSetElement value) {
        notifyItemRemoved(lstElement.indexOf(value));
        lstElement.remove(value);
    }

    public void addItem(FieldSetElement value) {
        lstElement.add(value);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        private final int viewType;

        public enum EnumViewType {
            TEXT(FieldSetElement.TypeElement.TEXT, 0),
            ENTITY(FieldSetElement.TypeElement.ENTITY, 1),
            BATTLE(FieldSetElement.TypeElement.BATTLE, 2),
            MAP(FieldSetElement.TypeElement.MAP, 3);

            private final FieldSetElement.TypeElement type;
            @Getter
            private final int viewType;

            EnumViewType(FieldSetElement.TypeElement type, int viewType) {
                this.type = type;
                this.viewType = viewType;
            }

            public static EnumViewType getValue(FieldSetElement.TypeElement type) {
                return Arrays.stream(EnumViewType.values())
                        .filter(e -> e.type == type)
                        .findFirst()
                        .orElse(null);
            }

            public static EnumViewType getValue(int viewType) {
                return Arrays.stream(EnumViewType.values())
                        .filter(e -> e.viewType == viewType)
                        .findFirst()
                        .orElse(null);
            }

        }

        public ViewHolder(View v, int viewType) {
            super(v);
            this.viewType = viewType;
        }

        public EnumViewType getViewType() {
            return EnumViewType.getValue(viewType);
        }
    }
}
