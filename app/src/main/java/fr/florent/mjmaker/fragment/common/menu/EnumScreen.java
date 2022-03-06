package fr.florent.mjmaker.fragment.common.menu;

import java.util.Arrays;

import fr.florent.mjmaker.R;

/**
 * List of all screen available
 */
public enum EnumScreen {
    // Screen menu
    FIND_MONSTER(R.id.menu_monster),
    // Sub screen
    EDIT_MONSTER();

    private final Integer id;

    EnumScreen(int id) {
        this.id = id;
    }
    EnumScreen() {
        this.id = null;
    }

    /**
     * Get screen value by id
     *
     * @param id The id
     * @return the value
     */
    public static EnumScreen valueOf(int id) {
        return Arrays.stream(EnumScreen.values())
                .filter(m -> m.id == id)
                .findFirst()
                .orElse(null);
    }
}
