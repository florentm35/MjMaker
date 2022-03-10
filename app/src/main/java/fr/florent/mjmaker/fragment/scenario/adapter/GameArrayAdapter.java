package fr.florent.mjmaker.fragment.scenario.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.service.model.Game;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class GameArrayAdapter extends ArrayAdapter<Game> {

    private final Context context;
    private final int resourceId;
    private final List<Game> items, tempItems, suggestions;

    public GameArrayAdapter(@NonNull Context context, int resourceId, List<Game> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<>(items);
        suggestions = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resourceId, parent, false);
        }
        Game game = getItem(position);
        AndroidLayoutUtil.setTextViewText(view, R.id.tv_title, game.getName());

        return view;
    }

    @Nullable
    @Override
    public Game getItem(int position) {
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

    @NonNull
    @Override
    public Filter getFilter() {
        return gameFilter;
    }

    private final Filter gameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Game game = (Game) resultValue;
            return game.getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                String value = charSequence.toString();
                for (Game game : tempItems) {
                    if (value.isEmpty() || game.getName().toLowerCase().startsWith(value.toLowerCase())) {
                        suggestions.add(game);
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
            ArrayList<Game> tempValues = (ArrayList<Game>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (Game gameObj : tempValues) {
                    add(gameObj);
                }
                notifyDataSetChanged();
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };
}
