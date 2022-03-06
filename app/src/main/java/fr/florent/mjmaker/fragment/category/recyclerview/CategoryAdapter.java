package fr.florent.mjmaker.fragment.category.recyclerview;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.service.model.Game;
import fr.florent.mjmaker.service.model.Theme;
import fr.florent.mjmaker.service.repository.ThemeRepositoryService;
import fr.florent.mjmaker.utils.AbstractLinearAdapter;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class CategoryAdapter extends AbstractLinearAdapter<Game> {

    private ThemeRepositoryService themeRepositoryService = ThemeRepositoryService.getInstance();

    public enum EnumAction {
        EDIT, DELETE;
    }

    public interface IEventAction {
        void action(EnumAction action, Game game);
    }

    private final IEventAction handler;

    public CategoryAdapter(Context context, List<Game> categories, IEventAction handler) {
        super(context, categories);
        this.handler = handler;
    }

    @Override
    public int getLayout(int viewType) {
        if (AbstractLinearAdapter.ViewHolder.HEADER == viewType) {
            return R.layout.list_game_header;
        } else {
            return R.layout.list_game_row;
        }
    }

    @Override
    public void onBindViewHolder(View view, int viewType, int position) {
        if (viewType == AbstractLinearAdapter.ViewHolder.ROW) {

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
            SubCategoryAdapter subCategoryAdapter;
            if (recyclerView.getAdapter() != null) {
                subCategoryAdapter = (SubCategoryAdapter) recyclerView.getAdapter();
                subCategoryAdapter.setItems( themeRepositoryService.findByIdGame(game.getId()));
            } else {
                subCategoryAdapter = new SubCategoryAdapter(
                        recyclerView.getContext(),
                        themeRepositoryService.findByIdGame(game.getId()),
                        (a, s) -> onActionSubCategory(recyclerView, a, s, game)
                );
                recyclerView.setLayoutManager(new LinearLayoutManager(context.getApplicationContext()));
                recyclerView.setAdapter(subCategoryAdapter);
            }

            cardView.setVisibility(View.VISIBLE);
            imageButton.setImageResource(R.drawable.material_less);
        } else {
            cardView.setVisibility(View.GONE);
            imageButton.setImageResource(R.drawable.material_more);
        }

    }

    private void onActionSubCategory(RecyclerView view, SubCategoryAdapter.EnumAction action, Theme theme, Game game) {
        SubCategoryAdapter subCategoryAdapter = (SubCategoryAdapter) view.getAdapter();

        switch (action) {

            case ADD:
                AndroidLayoutUtil.openModalAskText(view.getContext(),
                        "Set the name ?",
                        null, v -> this.onAdd(v, game, subCategoryAdapter));
                break;
            case EDIT:
                AndroidLayoutUtil.openModalAskText(view.getContext(),
                        "Set the name ?",
                        theme.getName(), v -> this.onEdit(v, game, theme, subCategoryAdapter));
                break;
            case DELETE:
                themeRepositoryService.delete(theme);
                subCategoryAdapter.removeItem(theme);
                break;
        }

    }

    private boolean onEdit(String value, Game game, Theme subcategory, SubCategoryAdapter adapter) {
        if (value == null || value.isEmpty()) {
            AndroidLayoutUtil.showToast(context.getApplicationContext(), "Sub category name can not be empty");
            return false;
        }

        Theme existCategory = themeRepositoryService.findByIdGameAndName(game.getId(), value);

        if (existCategory != null && !existCategory.getId().equals(subcategory.getId())) {
            AndroidLayoutUtil.showToast(context.getApplicationContext(),
                    "A sub category with name " + value + " for the category " + game.getName() + " exist");
            return false;
        }

        subcategory.setName(value);

        themeRepositoryService.update(subcategory);

        adapter.updateItem(subcategory);

        AndroidLayoutUtil.showToast(context.getApplicationContext(), "Sub category modified");

        return true;
    }

    private boolean onAdd(String value, Game game, SubCategoryAdapter adapter) {
        if (value == null || value.isEmpty()) {
            AndroidLayoutUtil.showToast(context.getApplicationContext(), "Sub category name can not be empty");
            return false;
        }

        Theme existCategory = themeRepositoryService.findByIdGameAndName(game.getId(), value);

        if (existCategory != null) {
            AndroidLayoutUtil.showToast(context.getApplicationContext(),
                    "A sub category with name " + value + " for the category " + game.getName() + " exist");
            return false;
        }

        Theme subcategory = new Theme();

        subcategory.setIdGame(game.getId());
        subcategory.setName(value);

        themeRepositoryService.save(subcategory);

        adapter.addItem(subcategory);

        AndroidLayoutUtil.showToast(context.getApplicationContext(), "Sub category created");

        return true;
    }
}
