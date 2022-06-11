package fr.florent.mjmaker.fragment.map.adapter;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.service.model.GameMap;
import fr.florent.mjmaker.utils.AbstractLinearWithHeaderAdapter;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class GameMapAdapter extends AbstractLinearWithHeaderAdapter<GameMap> {

    private final static String TAG = GameMapAdapter.class.getName();

    public enum EnumAction {
        EDIT, DELETE;
    }

    public interface IEventAction {
        void action(EnumAction action, GameMap gameMap);
    }

    private final IEventAction handler;

    public GameMapAdapter(Context context, List<GameMap> gameMaps, IEventAction handler) {
        super(context, gameMaps);
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

            GameMap gameMap = values.get(position - 1);

            AndroidLayoutUtil.setTextViewText(view, R.id.tv_game, gameMap.getGame() != null ? gameMap.getGame().getName() : "");
            AndroidLayoutUtil.setTextViewText(view, R.id.tv_theme, gameMap.getTheme() != null ? gameMap.getTheme().getName() : "");
            AndroidLayoutUtil.setTextViewText(view, R.id.tv_name, gameMap.getName());

            view.findViewById(R.id.view).setOnClickListener(v -> handler.action(EnumAction.EDIT, gameMap));
            view.findViewById(R.id.delete).setOnClickListener(v -> handler.action(EnumAction.DELETE, gameMap));
        }
    }
}
