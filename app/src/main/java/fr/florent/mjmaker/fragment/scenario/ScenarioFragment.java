package fr.florent.mjmaker.fragment.scenario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.category.recyclerview.CategoryAdapter;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.common.menu.EnumScreen;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.service.model.Game;
import fr.florent.mjmaker.service.repository.GameRepositoryService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class ListScenarioFragment extends AbstractFragment {

    private final GameRepositoryService gameRepositoryService = GameRepositoryService.getInstance();

    private CategoryAdapter categoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_game, container, false);

        RecyclerView listCategory = view.findViewById(R.id.list_category);

        listCategory.setLayoutManager(new LinearLayoutManager(getContext()));

        categoryAdapter = new CategoryAdapter(getContext(), gameRepositoryService.getAll(), this::onDataAction);

        listCategory.setAdapter(categoryAdapter);

        return view;
    }

    private void onDataAction(CategoryAdapter.EnumAction action, Game game) {
        if (action == CategoryAdapter.EnumAction.EDIT) {
            AndroidLayoutUtil.openModalAskText(getContext(),
                    "Set the name ?",
                    game.getName(), v -> this.onEdit(v, game));
        } else {
            gameRepositoryService.delete(game);
            categoryAdapter.removeItem(game);
            AndroidLayoutUtil.showToast(getContext(), "Category removed");
        }

    }

    @Override
    public List<ToolBarItem> getToolbarItem() {
        return Arrays.asList(
                ToolBarItem.builder()
                        .label("Add category")
                        .handler(this::showPopup)
                        .icone(R.drawable.material_add)
                        .build()
        );
    }

    public void showPopup(BiFunction<EnumScreen, Object[], Void> redirect) {
        AndroidLayoutUtil.openModalAskText(getContext(),
                "Set the name ?",
                null, this::onCreate);
    }

    // FIXME : refactor with onCreate and move control to service
    private boolean onEdit(String value, Game game) {
        if (value == null || value.isEmpty()) {
            AndroidLayoutUtil.showToast(getContext(), "Category name can not be empty");
            return false;
        }

        Game existGame = gameRepositoryService.findByName(value);

        if (existGame != null && !existGame.getId().equals(game.getId())) {
            AndroidLayoutUtil.showToast(getContext(), "A category with name " + value + " exist");
            return false;
        }

        game.setName(value);

        gameRepositoryService.update(game);

        categoryAdapter.updateItem(game);

        AndroidLayoutUtil.showToast(getContext(), "Category modified");

        return true;
    }

    private boolean onCreate(String value) {

        if (value == null || value.isEmpty()) {
            AndroidLayoutUtil.showToast(getContext(), "Category name can not be empty");
            return false;
        }

        if (gameRepositoryService.findByName(value) != null) {
            AndroidLayoutUtil.showToast(getContext(), "A category with name " + value + " exist");
            return false;
        }

        Game game = Game.builder()
                .name(value)
                .build();

        gameRepositoryService.save(game);

        categoryAdapter.addItem(game);

        AndroidLayoutUtil.showToast(getContext(), "Category created");

        return true;
    }
}
