package fr.florent.mjmaker.fragment.entity.adapter;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.service.model.Entity;
import fr.florent.mjmaker.service.model.Template;
import fr.florent.mjmaker.utils.AbstractLinearWithHeaderAdapter;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class EntityAdapter extends AbstractLinearWithHeaderAdapter<Entity> {

    private static String TAG = EntityAdapter.class.getName();

    public enum EnumAction {
        EDIT, DELETE;
    }

    public interface IEventAction {
        void action(EnumAction action, Entity entity);
    }

    private final IEventAction handler;

    public EntityAdapter(Context context, List<Entity> entities, IEventAction handler) {
        super(context, entities);
        this.handler = handler;
    }

    @Override
    public int getLayout(int viewType) {
        if (ViewHolder.HEADER == viewType) {
            return R.layout.list_entity_header;
        } else {
            return R.layout.list_entity_row;
        }
    }

    @Override
    public void onBindViewHolder(View view, int viewType, int position) {
        if (viewType == ViewHolder.ROW) {

            if (position % 2 == 1) {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_purple));
            } else {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.light_purple));
            }

            Entity entity = values.get(position - 1);

            Template template = entity.getTemplate();

            AndroidLayoutUtil.setTextViewText(view, R.id.tv_game, template.getGame() != null ? template.getGame().getName() : "");
            AndroidLayoutUtil.setTextViewText(view, R.id.tv_theme, template.getTheme() != null ? template.getTheme().getName() : "");
            AndroidLayoutUtil.setTextViewText(view, R.id.tv_level, entity.getLevel());
            AndroidLayoutUtil.setTextViewText(view, R.id.tv_name, entity.getName());

            view.findViewById(R.id.view).setOnClickListener(v -> handler.action(EnumAction.EDIT, entity));
            view.findViewById(R.id.delete).setOnClickListener(v -> handler.action(EnumAction.DELETE, entity));
        }
    }
}
