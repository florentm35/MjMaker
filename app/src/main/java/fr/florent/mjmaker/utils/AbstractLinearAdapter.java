package fr.florent.mjmaker.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public abstract class AbstractLinearAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> values;
    protected final Context context;

    public AbstractLinearAdapter(Context context, List<T> values) {
        this.values = values;
        this.context = context;
    }

    public void setItems(List<T> items) {
        values = items;
    }

    public void updateItem(T value) {
        notifyItemChanged(values.indexOf(value));
    }

    public void removeItem(T value) {
        notifyItemRemoved(values.indexOf(value));
        values.remove(value);
    }

    public void addItem(T value) {
        values.add(value);
    }

    public T getItem(int position) {
        return values.get(position);
    }

    public void swapItem(int position, int destination) {
        Collections.swap(values, position, destination);
        notifyItemMoved(position, destination);
    }

    public abstract int getLayout(int viewType);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AbstractLinearAdapter.ViewHolder(
                LayoutInflater.from(context).inflate(getLayout(viewType), parent, false));
    }

    public void onBindViewHolder(View view, int position) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View v) {
            super(v);
        }

    }
}
