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

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.category.recyclerview.GameAdapter;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.service.model.Game;
import fr.florent.mjmaker.service.repository.GameService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class ListGameFragment extends AbstractFragment {

    private final GameService gameService = GameService.getInstance();

    private GameAdapter gameAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_game, container, false);

        RecyclerView listCategory = view.findViewById(R.id.list_category);

        listCategory.setLayoutManager(new LinearLayoutManager(getContext()));

        gameAdapter = new GameAdapter(getContext(), gameService.getAll(), this::onDataAction);

        listCategory.setAdapter(gameAdapter);

        return view;
    }

    private void onDataAction(GameAdapter.EnumAction action, Game game) {
        if (action == GameAdapter.EnumAction.EDIT) {
            AndroidLayoutUtil.openModalAskText(getContext(),
                    game.getName(), v -> this.onValidateModalAskText(v, game));
        } else {
            gameService.delete(game);
            gameAdapter.removeItem(game);
            AndroidLayoutUtil.showToast(getContext(), getString(R.string.msg_game_deleted));
        }

    }

    @Override
    public List<ToolBarItem> getToolbarItem() {
        return Arrays.asList(
                ToolBarItem.builder()
                        .label(R.string.label_add_category)
                        .handler(this::showPopup)
                        .icone(R.drawable.material_add)
                        .build()
        );
    }

    public void showPopup() {
        AndroidLayoutUtil.openModalAskText(getContext(),
                null, v -> this.onValidateModalAskText(v, null));
    }

    private boolean onValidateModalAskText(String value, Game game) {
        boolean isCreation = false;

        if (game == null) {
            game = new Game();
            isCreation = true;
        }

        if (value == null || value.isEmpty()) {
            AndroidLayoutUtil.showToast(getContext(), getString(R.string.err_game_name_empty));
            return false;
        }

        Game existGame = gameService.findByName(value);

        if (existGame != null && !existGame.getId().equals(game.getId())) {
            AndroidLayoutUtil.showToast(getContext(), getString(R.string.err_game_already_exists, value));
            return false;
        }

        game.setName(value);

        int message;
        if (isCreation) {
            gameService.save(game);
            gameAdapter.addItem(game);

            message = R.string.msg_game_created;
        } else {
            gameService.update(game);
            gameAdapter.updateItem(game);
            message = R.string.msg_game_modified;
        }

        AndroidLayoutUtil.showToast(getContext(), getString(message));

        return true;
    }


}
