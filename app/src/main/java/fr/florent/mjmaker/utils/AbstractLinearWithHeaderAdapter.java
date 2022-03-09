package fr.florent.mjmaker.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class AbstractLinearWithHeaderAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> values;
    protected final Context context;

    public AbstractLinearWithHeaderAdapter(Context context, List<T> values) {
        this.values = values;
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {
        return position == 0 ? AbstractLinearWithHeaderAdapter.ViewHolder.HEADER : AbstractLinearWithHeaderAdapter.ViewHolder.ROW;
    }

    public void setItems(List<T> items) {
        values = items;
    }

    public void updateItem(T value) {
        notifyItemChanged(values.indexOf(value) + 1);
    }

    public void removeItem(T value) {
        notifyItemRemoved(values.indexOf(value) + 1);
        values.remove(value);
    }

    public void addItem(T value) {
        values.add(value);
    }

    public abstract int getLayout(int viewType);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AbstractLinearWithHeaderAdapter.ViewHolder(
                LayoutInflater.from(context).inflate(getLayout(viewType), parent, false)
                , viewType);
    }

    public abstract void onBindViewHolder(View view, int viewType, int position);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        View view = holder.itemView;
        int viewType = ((AbstractLinearWithHeaderAdapter.ViewHolder) holder).viewType;

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
