package fr.florent.mjmaker.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.category.recyclerview.CategoryAdapter;

public abstract class AbstractLinearAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> values;
    protected final Context context;
    private CategoryAdapter.IEventAction handler;

    public AbstractLinearAdapter(Context context, List<T> values) {
        this.values = values;
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position == 0 ? AbstractLinearAdapter.ViewHolder.HEADER : AbstractLinearAdapter.ViewHolder.ROW;
    }

    public void updateItem(T category) {
        notifyItemChanged(values.indexOf(category) + 1);
    }

    public void removeItem(T category) {
        notifyItemRemoved(values.indexOf(category) + 1);
        values.remove(category);

    }

    public void addItem(T category) {
        values.add(category);
    }

    public abstract int getLayout(int viewType);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AbstractLinearAdapter.ViewHolder(
                LayoutInflater.from(context).inflate(getLayout(viewType), parent, false)
                , viewType);
    }

    public abstract void onBindViewHolder(View view, int viewType, int position);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        View view = holder.itemView;
        int viewType = ((CategoryAdapter.ViewHolder) holder).viewType;

        onBindViewHolder(view, viewType, position);

    }

    @Override
    public int getItemCount() {
        return values.size() + 1;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        private final int viewType;
        public static int HEADER = 0;
        public static int ROW = 1;

        public ViewHolder(View v, int viewType) {
            super(v);
            this.viewType = viewType;
        }

    }
}
