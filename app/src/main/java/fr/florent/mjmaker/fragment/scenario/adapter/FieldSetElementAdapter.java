package fr.florent.mjmaker.fragment.scenario.adapter;

import android.content.Context;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.component.MarkdownEditor;
import fr.florent.mjmaker.fragment.common.markdown.InternalLinkMovementMethod;
import fr.florent.mjmaker.fragment.scenario.ScenarioFragment;
import fr.florent.mjmaker.service.markdown.MarkDownService;
import fr.florent.mjmaker.service.model.Entity;
import fr.florent.mjmaker.service.model.FieldSetElement;
import fr.florent.mjmaker.service.model.TextElement;
import fr.florent.mjmaker.service.repository.EntityService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;
import lombok.Getter;
import lombok.Setter;

public class FieldSetElementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final MarkDownService markDownService = MarkDownService.getInstance();

    private final EntityService entityService = EntityService.getInstance();

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
                        proccesTextViewMode(view, textElement);
                        break;
                    case EDIT:
                        processTextEditMode(element, view, textElement);
                        break;
                }

                break;
            case ENTITY:
            case BATTLE:
            case MAP:
                throw new RuntimeException("Not implemented");
        }

    }

    private void processTextEditMode(FieldSetElement element, View view, TextElement textElement) {
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
    }

    private void proccesTextViewMode(View view, TextElement textElement) {
        Spanned text = AndroidLayoutUtil.createHtmlText(
                markDownService.parseMarkDown(textElement.getText())
        );
        AndroidLayoutUtil.setTextViewText(view, R.id.tv_text, text);

        TextView textView = view.findViewById(R.id.tv_text);
        textView.setMovementMethod(new InternalLinkMovementMethod(link -> {

            if (link.startsWith("entity://")) {
                String id = link.replace("entity://", "");
                try {
                    Entity entityRefresh = entityService.findBydId(Integer.parseInt(id));
                    AndroidLayoutUtil.openModalInfo(context, entityService.renderEntity(entityRefresh));
                } catch (Exception ex) {
                    AndroidLayoutUtil.showToast(context, context.getString(R.string.msg_entity_not_found));
                }
                return true;
            }

            return false;
        }));
        view.findViewById(R.id.tv_text).setVisibility(View.VISIBLE);
        view.findViewById(R.id.mde_text).setVisibility(View.GONE);
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
