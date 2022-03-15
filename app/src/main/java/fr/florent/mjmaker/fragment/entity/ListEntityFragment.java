package fr.florent.mjmaker.fragment.entity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.common.menu.EnumScreen;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.service.repository.EntityRepositoryService;

public class ListEntityFragment extends AbstractFragment {

    private final EntityRepositoryService monsterService = EntityRepositoryService.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_monster, container, false);

        return view;
    }

    @Override
    public List<ToolBarItem> getToolbarItem() {
        return Arrays.asList(
                ToolBarItem.builder()
                        .label("add_monster")
                        .handler(this::redirectToEditMonster)
                        .icone(R.drawable.material_add)
                        .build(),
                ToolBarItem.builder()
                        .label("Template")
                        .handler(this::redirectToListTemplate)
                        .build()
        );
    }

    public void redirectToEditMonster() {
        redirect.apply(EnumScreen.EDIT_MONSTER, null);
    }
    public void redirectToListTemplate() {
        redirect.apply(EnumScreen.LIST_ENTITY_TEMPLATE, null);
    }
}
