package fr.florent.mjmaker.fragment.common.menu;

import java.util.Arrays;

import fr.florent.mjmaker.R;

public enum EnumMenu {
    FIND_MONSTER(R.id.menu_monster), EDIT_MONSTER(R.id.menu_test);

    private final int id;

    EnumMenu(int id) {
        this.id = id;
    }

    public static EnumMenu valueOf(int id) {
        return Arrays.stream(EnumMenu.values())
                .filter(m -> m.id == id)
                .findFirst()
                .orElse(null);
    }
}
