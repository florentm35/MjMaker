package fr.florent.mjmaker.fragment.category.recyclerview;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.service.model.Theme;
import fr.florent.mjmaker.utils.AbstractLinearAdapter;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class SubCategoryAdapter extends AbstractLinearAdapter<Theme> {


    public enum EnumAction {
        ADD, EDIT, DELETE;
    }

    public interface IEventAction {
        void action(EnumAction action, Theme theme);
    }

    private final IEventAction handler;

    public SubCategoryAdapter(Context context, List<Theme> subCategories, IEventAction handler) {
        super(context, subCategories);
        this.handler = handler;
    }

    @Override
    public int getLayout(int viewType) {
        if (ViewHolder.HEADER == viewType) {
            return R.layout.list_subcategory_header;
        } else {
            return R.layout.list_subcategory_row;
        }
    }

    @Override
    public void onBindViewHolder(View view, int viewType, int position) {
        if (viewType == ViewHolder.ROW) {

            if (position % 2 == 1) {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_gray));
            } else {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.light_gray));
            }

            Theme theme = values.get(position - 1);

            AndroidLayoutUtil.setTextViewText(view, R.id.tv_name, theme.getName());

            view.findViewById(R.id.edit).setOnClickListener(v -> handler.action(EnumAction.EDIT, theme));
            view.findViewById(R.id.delete).setOnClickListener(v -> handler.action(EnumAction.DELETE, theme));

        } else {
            view.findViewById(R.id.add).setOnClickListener(v -> handler.action(EnumAction.ADD, null));
        }
    }


}
