package fr.florent.mjmaker.fragment.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.common.menu.EnumScreen;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.fragment.map.adapter.GameMapAdapter;
import fr.florent.mjmaker.fragment.map.modal.ParamGameMapModal;
import fr.florent.mjmaker.service.model.GameMap;
import fr.florent.mjmaker.service.repository.GameMapService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class ListMapFragment extends AbstractFragment {

    private final GameMapService mapGameService = GameMapService.getInstance();

    private GameMapAdapter gameMapAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.list_element);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        gameMapAdapter = new GameMapAdapter(getContext(), mapGameService.getAll(), this::onAction);
        recyclerView.setAdapter(gameMapAdapter);

        return view;
    }

    private void onAction(GameMapAdapter.EnumAction action, GameMap gameMap) {
        switch (action) {
            case EDIT:
                this.redirectToDetailMap(gameMap);
                break;
            case DELETE:
                AndroidLayoutUtil.openModalQuestion(getContext(),
                        getString(R.string.msg_ask_delete_map),
                        choice -> {
                            if (choice) {
                                mapGameService.delete(gameMap);
                                gameMapAdapter.removeItem(gameMap);
                                AndroidLayoutUtil.showToast(getContext(), getString(R.string.msg_map_deleted));
                            }
                            return true;
                        });

                break;
        }
    }

    @Override
    public List<ToolBarItem> getToolbarItem() {
        return Collections.singletonList(
                ToolBarItem.builder()
                        .label(R.string.msg_new_map)
                        .handler(this::initMap)
                        .icone(R.drawable.material_add)
                        .build()
        );
    }

    private void redirectToDetailMap(GameMap gameMap) {
        redirect.apply(EnumScreen.EDIT_MAP, new Object[]{gameMap});
    }

    private void initMap() {
        ParamGameMapModal dialog = new ParamGameMapModal();

        dialog.show(getContext(), null, this::saveMap);
    }

    private boolean saveMap(GameMap gameMap) {
        mapGameService.save(gameMap);
        AndroidLayoutUtil.showToast(getContext(), getString(R.string.msg_map_created));
        redirectToDetailMap(gameMap);
        return true;
    }

}
