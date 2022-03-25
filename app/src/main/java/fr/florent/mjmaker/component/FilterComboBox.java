package fr.florent.mjmaker.component;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;
import fr.florent.mjmaker.utils.ItemSelect;

/**
 * Filter combo box component
 * @param <T>
 */
public class FilterComboBox<T> extends androidx.appcompat.widget.AppCompatAutoCompleteTextView{

    public interface IEventSelect<T> {
        void action(T value);
    }

    public FilterComboBox(Context context) {
        super(context);
    }

    public FilterComboBox(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public FilterComboBox(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }


    /**
     * Affect the items to the list
     *
     * @param lstItem The items
     */
   public void setItems(List<ItemSelect<T>> lstItem) {
       AutoCompleteAdapter<T> adapter = new AutoCompleteAdapter<T>(getContext(),
               R.layout.item_string_row,
               lstItem
       );

       this.setAdapter(adapter);
   }

    @Override
    public void setOnItemClickListener(AdapterView.OnItemClickListener l) {
        super.setOnItemClickListener(l);
    }

    /**
     * Set the select callback item
     *
     * @param event The callback
     */
    public void setOnItemClickListener(IEventSelect<T> event) {
        this.setOnItemClickListener((adapterView, view1, i, l) -> {
            ItemSelect<T> item = (ItemSelect<T>) adapterView.getItemAtPosition(i);
            if (item != null) {
                event.action(item.getValue());
            }
        });
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused && getFilter()!=null) {
            performFiltering(getText(), 0);
        }
    }

    /**
     * Simple text adapter
     * @param <T>
     */
    private static class AutoCompleteAdapter<T> extends ArrayAdapter<ItemSelect<T>> {

        private final Context context;
        private final int resourceId;
        private final List<ItemSelect<T>> items, tempItems, suggestions;

        public AutoCompleteAdapter(@NonNull Context context, int resourceId, List<ItemSelect<T>> items) {
            super(context, resourceId, items);
            this.items = items;
            this.context = context;
            this.resourceId = resourceId;
            tempItems = new ArrayList<>(items);
            suggestions = new ArrayList<>();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                view = inflater.inflate(resourceId, parent, false);
            }
            ItemSelect<T> item = getItem(position);
            AndroidLayoutUtil.setTextViewText(view, R.id.tv_title, item.getLabel());

            return view;
        }

        @Override
        public ItemSelect<T> getItem(int position) {
            return items.get(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Filter getFilter() {
            return filter;
        }

        private final Filter filter = new Filter() {
            @Override
            public CharSequence convertResultToString(Object resultValue) {
                ItemSelect<T> item = (ItemSelect<T>) resultValue;
                return item.getLabel();
            }

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                if (charSequence != null) {
                    suggestions.clear();
                    String value = charSequence.toString();
                    for (ItemSelect<T> item : tempItems) {
                        if (value.isEmpty() || item.getLabel().toLowerCase().startsWith(value.toLowerCase())) {
                            suggestions.add(item);
                        }
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                ArrayList<ItemSelect<T>> tempValues = (ArrayList<ItemSelect<T>>) filterResults.values;
                if (filterResults.count > 0) {
                    clear();
                    for (ItemSelect<T> item : tempValues) {
                        add(item);
                    }
                    notifyDataSetChanged();
                } else {
                    clear();
                    notifyDataSetChanged();
                }
            }
        };
    }
}
