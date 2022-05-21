package fr.florent.mjmaker.fragment.map.adapter;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.service.model.MapGame;
import fr.florent.mjmaker.utils.AbstractLinearWithHeaderAdapter;

public class MapAdapter extends AbstractLinearWithHeaderAdapter<MapGame> {

    private final static String TAG = MapAdapter.class.getName();

    public enum EnumAction {
        EDIT, DELETE;
    }

    public interface IEventAction {
        void action(EnumAction action, MapGame mapGame);
    }

    private final IEventAction handler;

    public MapAdapter(Context context, List<MapGame> mapGames, IEventAction handler) {
        super(context, mapGames);
        this.handler = handler;
    }

    @Override
    public int getLayout(int viewType) {
        if (ViewHolder.HEADER == viewType) {
            return R.layout.list_template_header;
        } else {
            return R.layout.list_template_row;
        }
    }

    @Override
    public void onBindViewHolder(View view, int viewType, int position) {
        if (viewType == ViewHolder.ROW) {

            if (position % 2 == 1) {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_purple));
            } else {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.light_purple));
            }

            MapGame mapGame = values.get(position - 1);

            /*AndroidLayoutUtil.setTextViewText(view, R.id.tv_game, template.getGame() != null ? template.getGame().getName() : "");
            AndroidLayoutUtil.setTextViewText(view, R.id.tv_theme, template.getTheme() != null ? template.getTheme().getName() : "");
            AndroidLayoutUtil.setTextViewText(view, R.id.tv_name, template.getName());*/

            view.findViewById(R.id.view).setOnClickListener(v -> handler.action(EnumAction.EDIT, mapGame));
            view.findViewById(R.id.delete).setOnClickListener(v -> handler.action(EnumAction.DELETE, mapGame));
        }
    }
}
