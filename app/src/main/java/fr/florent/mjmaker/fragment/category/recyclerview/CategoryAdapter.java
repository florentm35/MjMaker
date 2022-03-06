package fr.florent.mjmaker.fragment.category.recyclerview;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.service.model.Category;
import fr.florent.mjmaker.utils.AbstractLinearAdapter;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class CategoryAdapter extends AbstractLinearAdapter<Category> {


    public enum EnumAction {
        EDIT, DELETE;
    }

    public interface IEventAction {
        void action(EnumAction action, Category category);
    }

    private final IEventAction handler;

    public CategoryAdapter(Context context, List<Category> categorys, IEventAction handler) {
        super(context, categorys);
        this.handler = handler;
    }

    @Override
    public int getLayout(int viewType) {
        if (AbstractLinearAdapter.ViewHolder.HEADER == viewType) {
            return R.layout.list_category_header;
        } else {
            return R.layout.list_category_row;
        }
    }

    @Override
    public void onBindViewHolder(View view, int viewType, int position) {
        if (viewType == AbstractLinearAdapter.ViewHolder.ROW) {

            if (position % 2 == 1) {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.teal_200));
            } else {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            }

            Category category = values.get(position - 1);

            AndroidLayoutUtil.setTextViewText(view, R.id.tv_name, category.getName());

            view.findViewById(R.id.edit).setOnClickListener(v -> {handler.action(EnumAction.EDIT, category);});
            view.findViewById(R.id.delete).setOnClickListener(v -> {handler.action(EnumAction.DELETE, category);});
        }
    }


}
