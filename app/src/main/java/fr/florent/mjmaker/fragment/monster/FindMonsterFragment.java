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
import fr.florent.mjmaker.fragment.common.menu.EnumMenu;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.service.monstrer.MonsterService;

public class FindMonsterFragment extends AbstractFragment {

    MonsterService monsterService = MonsterService.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.find_monster, container, false);

        try {
            monsterService.getAll();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

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

    public void redirectToEditMonster(BiFunction<EnumMenu, Object[], Void> redirect) {

        redirect.apply(EnumMenu.EDIT_MONSTER, null);
    }
}
