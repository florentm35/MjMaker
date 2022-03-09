package fr.florent.mjmaker.fragment.scenario.recyclerview;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.service.model.FieldSetScenario;
import fr.florent.mjmaker.utils.AbstractLinearAdapter;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class FieldSetAdapter extends AbstractLinearAdapter<FieldSetScenario> {

    public enum EnumAction {
        ADD, EDIT, DELETE;
    }

    public interface IEventAction {
        void action(EnumAction action, FieldSetScenario fieldSetScenario);
    }

    private final IEventAction handler;

    public FieldSetAdapter(Context context, List<FieldSetScenario> values, IEventAction handler) {
        super(context, values);
        this.handler = handler;
    }

    @Override
    public int getLayout(int viewType) {
        return R.layout.fieldset_row;
    }

    @Override
    public void onBindViewHolder(View view, int position) {
        FieldSetScenario item = values.get(position);

        AndroidLayoutUtil.setTextViewText(view, R.id.tv_title, item.getTitle());

        view.findViewById(R.id.add).setOnClickListener(v -> handler.action(EnumAction.ADD, item));
        view.findViewById(R.id.edit).setOnClickListener(v -> handler.action(EnumAction.EDIT, item));
        view.findViewById(R.id.delete).setOnClickListener(v -> handler.action(EnumAction.DELETE, item));
        view.findViewById(R.id.more).setOnClickListener(v -> switchVisibilityListElement(view));

    }

    private void switchVisibilityListElement(View view) {
        ImageButton btn = view.findViewById(R.id.more);
        RecyclerView recyclerView = view.findViewById(R.id.list_element);
        if(recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
            btn.setImageResource(R.drawable.material_more);
        } else {
            // TODO load element
            recyclerView.setVisibility(View.VISIBLE);
            btn.setImageResource(R.drawable.material_less);
        }
    }


}
