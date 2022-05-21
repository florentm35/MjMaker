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
import fr.florent.mjmaker.fragment.map.adapter.MapAdapter;
import fr.florent.mjmaker.fragment.map.modal.ParamMapModal;
import fr.florent.mjmaker.service.model.MapGame;
import fr.florent.mjmaker.service.repository.MapGameService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class ListMapFragment extends AbstractFragment {

    private final MapGameService mapGameService = MapGameService.getInstance();

    private MapAdapter mapAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.list_element);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mapAdapter = new MapAdapter(getContext(), mapGameService.getAll(), this::onAction);
        recyclerView.setAdapter(mapAdapter);

        return view;
    }

    private void onAction(MapAdapter.EnumAction action, MapGame mapGame) {
        switch (action) {
            case EDIT:
                this.redirectToDetailMap(mapGame);
                break;
            case DELETE:
                AndroidLayoutUtil.openModalQuestion(getContext(),
                        getString(R.string.msg_ask_delete_template),
                        choice -> {
                            if (choice) {
                                mapGameService.delete(mapGame);
                                mapAdapter.removeItem(mapGame);
                                AndroidLayoutUtil.showToast(getContext(), getString(R.string.msg_template_deleted));
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

    private void redirectToDetailMap(MapGame mapGame) {
        redirect.apply(EnumScreen.EDIT_MAP, new Object[]{mapGame});
    }

    private void initMap() {
        ParamMapModal dialog = new ParamMapModal();

        dialog.show(getContext(), null, this::saveMap);
    }

    private boolean saveMap(MapGame mapGame) {
        mapGameService.save(mapGame);
        AndroidLayoutUtil.showToast(getContext(), getString(R.string.msg_template_created));
        redirectToDetailMap(mapGame);
        return true;
    }

}
