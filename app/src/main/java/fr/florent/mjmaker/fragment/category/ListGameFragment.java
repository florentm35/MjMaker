package fr.florent.mjmaker.fragment.category;

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
import fr.florent.mjmaker.fragment.category.recyclerview.GameAdapter;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.common.menu.EnumScreen;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.service.model.Game;
import fr.florent.mjmaker.service.repository.GameRepositoryService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class ListGameFragment extends AbstractFragment {

    private final GameRepositoryService gameRepositoryService = GameRepositoryService.getInstance();

    private GameAdapter gameAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_game, container, false);

        RecyclerView listCategory = view.findViewById(R.id.list_category);

        listCategory.setLayoutManager(new LinearLayoutManager(getContext()));

        gameAdapter = new GameAdapter(getContext(), gameRepositoryService.getAll(), this::onDataAction);

        listCategory.setAdapter(gameAdapter);

        return view;
    }

    private void onDataAction(GameAdapter.EnumAction action, Game game) {
        if (action == GameAdapter.EnumAction.EDIT) {
            AndroidLayoutUtil.openModalAskText(getContext(),
                    "Set the name ?",
                    game.getName(), v -> this.onValidateModalAskText(v, game));
        } else {
            gameRepositoryService.delete(game);
            gameAdapter.removeItem(game);
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
                null, v -> this.onValidateModalAskText(v, null));
    }

    private boolean onValidateModalAskText(String value, Game game) {
        boolean isCreation = false;

        if(game == null) {
            game = new Game();
            isCreation = true;
        }

        if (value == null || value.isEmpty()) {
            AndroidLayoutUtil.showToast(getContext(), "Game name can not be empty");
            return false;
        }

        Game existGame = gameRepositoryService.findByName(value);

        if (existGame != null && !existGame.getId().equals(game.getId())) {
            AndroidLayoutUtil.showToast(getContext(), "A game named " + value + " exist");
            return false;
        }

        game.setName(value);

        String message ;
        if(isCreation) {
            gameRepositoryService.save(game);
            gameAdapter.addItem(game);

            message = "Game created";
        } else {
            gameRepositoryService.update(game);
            gameAdapter.updateItem(game);
            message = "Game modified";
        }

        AndroidLayoutUtil.showToast(getContext(), message);

        return true;
    }


}
