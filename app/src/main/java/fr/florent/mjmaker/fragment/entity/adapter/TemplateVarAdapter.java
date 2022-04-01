package fr.florent.mjmaker.fragment.entity.adapter;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.service.model.TemplateVar;
import fr.florent.mjmaker.utils.AbstractLinearAdapter;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class TemplateVarAdapter extends AbstractLinearAdapter<TemplateVar> {

    public enum EnumAction {
        UPDATE, DELETE;
    }

    public interface IEventAction {
        void action(EnumAction action, TemplateVar templateVar);
    }

    private final IEventAction handler;

    public TemplateVarAdapter(Context context, List<TemplateVar> values, IEventAction handler) {
        super(context, values);
        this.handler = handler;
    }

    @Override
    public int getLayout(int viewType) {
        return R.layout.row_template_variable;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        View view = holder.itemView;

        TemplateVar templateVar = getItem(position);

        AndroidLayoutUtil.clearExtendedEditTextTextChange(view, R.id.et_name);
        AndroidLayoutUtil.setTextViewText(view, R.id.et_name, templateVar.getLabel());
        AndroidLayoutUtil.setExtendedEditTextTextChange(view, R.id.et_name, t -> onNameChange(templateVar, t));

        view.findViewById(R.id.delete).setOnClickListener(v -> handler.action(EnumAction.DELETE, templateVar));
    }

    public void onNameChange(TemplateVar templateVar, String text) {
        templateVar.setLabel(text);
        handler.action(EnumAction.UPDATE, templateVar);
    }
}

