package fr.florent.mjmaker.fragment.category.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.common.menu.EnumScreen;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.service.model.Category;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Category> categorys;
    private Context context;

    public CategoryAdapter(Context context, List<Category> categorys) {
        this.categorys = categorys;
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position == 0 ? ViewHolder.HEADER : ViewHolder.ROW;
    }

    public void addItem(Category category) {
        categorys.add(category);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        if (ViewHolder.HEADER == viewType) {
            view = LayoutInflater.from(context).inflate(R.layout.list_category_header, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.list_category_row, parent, false);
        }

        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        View view = holder.itemView;
        int viewType = ((ViewHolder) holder).viewType;

        if (viewType == ViewHolder.ROW) {
            Category category = categorys.get(position - 1);

            AndroidLayoutUtil.setTextViewText(view, R.id.tv_name, category.getName());
        }

    }

    @Override
    public int getItemCount() {
        return categorys.size() + 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final int viewType;
        static int HEADER = 0;
        static int ROW = 1;

        public ViewHolder(View v, int viewType) {
            super(v);
            this.viewType = viewType;
        }
    }

}
