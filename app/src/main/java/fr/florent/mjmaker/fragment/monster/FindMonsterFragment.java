package fr.florent.mjmaker.fragment.monster;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.common.menu.EnumScreen;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.service.monstrer.MonsterRepositoryService;

public class FindMonsterFragment extends AbstractFragment {

    MonsterRepositoryService monsterService = MonsterRepositoryService.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.find_monster, container, false);

        return view;
    }

    @Override
    public List<ToolBarItem> getToolbarItem() {
        return Arrays.asList(
                ToolBarItem.builder()
                        .label("Test2")
                        .handler(this::redirectToEditMonster)
                        .icone(R.drawable.material_add)
                        .build()
        );
    }

    public void redirectToEditMonster(BiFunction<EnumScreen, Object[], Void> redirect) {

        redirect.apply(EnumScreen.EDIT_MONSTER, null);
    }
}
