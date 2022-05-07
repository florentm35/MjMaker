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
import fr.florent.mjmaker.service.model.Map;
import fr.florent.mjmaker.service.repository.MapService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class ListMapFragment extends AbstractFragment {

    private final MapService mapService = MapService.getInstance();

    private MapAdapter mapAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.list_element);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mapAdapter = new MapAdapter(getContext(), mapService.getAll(), this::onAction);
        recyclerView.setAdapter(mapAdapter);

        return view;
    }

    private void onAction(MapAdapter.EnumAction action, Map map) {
        switch (action) {
            case EDIT:
                this.redirectToDetailMap(map);
                break;
            case DELETE:
                AndroidLayoutUtil.openModalQuestion(getContext(),
                        getString(R.string.msg_ask_delete_template),
                        choice -> {
                            if (choice) {
                                mapService.delete(map);
                                mapAdapter.removeItem(map);
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

    private void redirectToDetailMap(Map map) {
        redirect.apply(EnumScreen.EDIT_MAP, new Object[]{map});
    }

    private void initMap() {
        ParamMapModal dialog = new ParamMapModal();

        dialog.show(getContext(), null, this::saveMap);
    }

    private boolean saveMap(Map map) {
        mapService.save(map);
        AndroidLayoutUtil.showToast(getContext(), getString(R.string.msg_template_created));
        redirectToDetailMap(map);
        return true;
    }

}
