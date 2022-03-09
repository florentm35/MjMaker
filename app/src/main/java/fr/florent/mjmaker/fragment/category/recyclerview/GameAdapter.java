package fr.florent.mjmaker.fragment.category.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.service.common.SQLRuntimeException;
import fr.florent.mjmaker.service.model.Game;
import fr.florent.mjmaker.service.model.Theme;
import fr.florent.mjmaker.service.repository.ThemeRepositoryService;
import fr.florent.mjmaker.utils.AbstractLinearWithHeaderAdapter;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;
import fr.florent.mjmaker.utils.DataBaseUtil;

public class GameAdapter extends AbstractLinearWithHeaderAdapter<Game> {

    private static String TAG = GameAdapter.class.getName();

    private ThemeRepositoryService themeRepositoryService = ThemeRepositoryService.getInstance();

    public enum EnumAction {
        EDIT, DELETE;
    }

    public interface IEventAction {
        void action(EnumAction action, Game game);
    }

    private final IEventAction handler;

    public GameAdapter(Context context, List<Game> categories, IEventAction handler) {
        super(context, categories);
        this.handler = handler;
    }

    @Override
    public int getLayout(int viewType) {
        if (AbstractLinearWithHeaderAdapter.ViewHolder.HEADER == viewType) {
            return R.layout.list_game_header;
        } else {
            return R.layout.list_game_row;
        }
    }

    @Override
    public void onBindViewHolder(View view, int viewType, int position) {
        if (viewType == AbstractLinearWithHeaderAdapter.ViewHolder.ROW) {

            if (position % 2 == 1) {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_purple));
            } else {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.light_purple));
            }

            Game game = values.get(position - 1);

            AndroidLayoutUtil.setTextViewText(view, R.id.tv_name, game.getName());

            view.findViewById(R.id.edit).setOnClickListener(v -> handler.action(EnumAction.EDIT, game));
            view.findViewById(R.id.delete).setOnClickListener(v -> handler.action(EnumAction.DELETE, game));

            view.findViewById(R.id.more).setOnClickListener(v -> expendCategory(view, game));
        }
    }

    private void expendCategory(View view, Game game) {
        ImageButton imageButton = view.findViewById(R.id.more);
        RecyclerView recyclerView = view.findViewById(R.id.subcategory_recycler);
        CardView cardView = view.findViewById(R.id.subcategory);

        if (cardView.getVisibility() == View.GONE) {
            ThemeAdapter themeAdapter;
            if (recyclerView.getAdapter() != null) {
                themeAdapter = (ThemeAdapter) recyclerView.getAdapter();
                themeAdapter.setItems(DataBaseUtil.convertForeignCollectionToList(game.getLstTheme()));
            } else {
                themeAdapter = new ThemeAdapter(
                        recyclerView.getContext(),
                        DataBaseUtil.convertForeignCollectionToList(game.getLstTheme()),
                        (a, s) -> onActionSubCategory(recyclerView, a, s, game)
                );
                recyclerView.setLayoutManager(new LinearLayoutManager(context.getApplicationContext()));
                recyclerView.setAdapter(themeAdapter);
            }

            cardView.setVisibility(View.VISIBLE);
            imageButton.setImageResource(R.drawable.material_less);
        } else {
            cardView.setVisibility(View.GONE);
            imageButton.setImageResource(R.drawable.material_more);
        }

    }

    private void onActionSubCategory(RecyclerView view, ThemeAdapter.EnumAction action, Theme theme, Game game) {
        ThemeAdapter themeAdapter = (ThemeAdapter) view.getAdapter();

        switch (action) {

            case ADD:
                AndroidLayoutUtil.openModalAskText(view.getContext(),
                        "Set the name ?",
                        null, v -> this.onValidateModalAskText(v, game, null, themeAdapter));
                break;
            case EDIT:
                AndroidLayoutUtil.openModalAskText(view.getContext(),
                        "Set the name ?",
                        theme.getName(), v -> this.onValidateModalAskText(v, game, theme, themeAdapter));
                break;
            case DELETE:
                themeRepositoryService.delete(theme);
                themeAdapter.removeItem(theme);
                break;
        }

        try {
            game.getLstTheme().refreshCollection();
        } catch (SQLException exception) {
            Log.e(TAG, "Can not be refresh game entity", exception);
            throw new SQLRuntimeException(exception);
        }

    }

    private boolean onValidateModalAskText(String value, Game game, Theme subcategory, ThemeAdapter adapter) {
        boolean isCreation = false;

        if (subcategory == null) {
            subcategory = new Theme();
            isCreation = true;
        }

        if (value == null || value.isEmpty()) {
            AndroidLayoutUtil.showToast(context.getApplicationContext(), "Sub category name can not be empty");
            return false;
        }

        Theme existCategory = themeRepositoryService.findByIdGameAndName(game.getId(), value);

        if (existCategory != null && !existCategory.getId().equals(subcategory.getId())) {
            AndroidLayoutUtil.showToast(context.getApplicationContext(),
                    "A sub category named " + value + " for theme " + game.getName() + " exist");
            return false;
        }

        subcategory.setName(value);

        String message;
        if (isCreation) {

            themeRepositoryService.save(subcategory);
            adapter.addItem(subcategory);
            message = "Theme created";
        } else {
            themeRepositoryService.update(subcategory);
            adapter.updateItem(subcategory);
            message = "Theme modified";
        }


        AndroidLayoutUtil.showToast(context.getApplicationContext(), message);

        return true;
    }

}
