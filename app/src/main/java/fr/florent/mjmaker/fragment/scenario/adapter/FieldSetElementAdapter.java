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
import fr.florent.mjmaker.component.MarkdownEditor;
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
                        Spanned text = AndroidLayoutUtil.createHtmlText(
                                markDownService.parseMarkDown(textElement.getText())
                        );
                        AndroidLayoutUtil.setTextViewText(view, R.id.tv_text, text);
                        view.findViewById(R.id.tv_text).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.mde_text).setVisibility(View.GONE);
                        break;
                    case EDIT:
                        Log.d(TAG, "onBindViewHolder: Edit mode " + textElement);
                        MarkdownEditor editor = view.findViewById(R.id.mde_text);
                        editor.setText(textElement.getText());
                        editor.setOnTextChanged((value) -> {
                            Log.d(TAG, String.format("old value : %s, new value : %s", textElement.getText(), value));
                            textElement.setText(value);
                            handler.update(EnumAction.UPDATE, element);
                        });
                        editor.setDeleteHandler(() -> handler.update(EnumAction.DELETE, element));

                        view.findViewById(R.id.tv_text).setVisibility(View.GONE);
                        view.findViewById(R.id.mde_text).setVisibility(View.VISIBLE);
                        break;
                }

                break;
            case ENTITY:
            case BATTLE:
            case MAP:
                throw new RuntimeException("Not implemented");
        }

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
