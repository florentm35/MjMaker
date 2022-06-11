package fr.florent.mjmaker.fragment.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.fragment.map.modal.ParamGameMapModal;
import fr.florent.mjmaker.service.model.GameMap;
import fr.florent.mjmaker.service.repository.GameMapService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class EditGameMapFragment extends AbstractFragment {

    private GameMap gameMap;

    private final GameMapService mapGameService = GameMapService.getInstance();

    public EditGameMapFragment(Object... params) {
        if (params != null && params.length > 0) {
            gameMap = (GameMap) params[0];
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_map, container, false);

        return view;
    }

    @Override
    public List<ToolBarItem> getToolbarItem() {
        return Collections.singletonList(
                ToolBarItem.builder()
                        .label(R.string.label_rename_template)
                        .handler(this::changeParamTemplate)
                        .icone(R.drawable.material_setting)
                        .build()

        );
    }

    private void changeParamTemplate() {
        ParamGameMapModal dialog = new ParamGameMapModal();
        dialog.show(getContext(), gameMap, this::saveMap);
    }

    private boolean saveMap(GameMap gameMap) {
        mapGameService.update(gameMap);
        AndroidLayoutUtil.showToast(getContext(), getString(R.string.msg_map_updated));
        return true;
    }

    @Override
    public boolean showBack() {
        return true;
    }
}
