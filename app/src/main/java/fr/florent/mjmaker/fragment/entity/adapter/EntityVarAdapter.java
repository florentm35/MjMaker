package fr.florent.mjmaker.fragment.entity.adapter;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.service.model.EntityVar;
import fr.florent.mjmaker.utils.AbstractLinearAdapter;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class EntityVarAdapter extends AbstractLinearAdapter<EntityVar> {

    public enum EnumAction {
        UPDATE;
    }

    public interface IEventAction {
        void action(EnumAction action, EntityVar entityVar);
    }

    private final IEventAction handler;

    public EntityVarAdapter(Context context, List<EntityVar> values, IEventAction handler) {
        super(context, values);
        this.handler = handler;
    }

    @Override
    public int getLayout(int viewType) {
        return R.layout.row_entity_variable;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        View view = holder.itemView;

        EntityVar entityVar = getItem(position);

        AndroidLayoutUtil.setTextViewText(view, R.id.tv_label, entityVar.getTemplateVar().getLabel());


        AndroidLayoutUtil.clearExtendedEditTextTextChange(view, R.id.et_value);
        AndroidLayoutUtil.setTextViewText(view, R.id.et_value, entityVar.getValue());
        AndroidLayoutUtil.setExtendedEditTextTextChange(view, R.id.et_value, t -> onValueChanged(entityVar, t));

    }

    public void onValueChanged(EntityVar entityVar, String text) {
        entityVar.setValue(text);
        handler.action(EnumAction.UPDATE, entityVar);
    }
}

