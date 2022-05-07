package fr.florent.mjmaker.fragment.common.menu;

import java.util.Arrays;

import fr.florent.mjmaker.R;

/**
 * List of all screen available
 */
public enum EnumScreen {
    // Screen menu
    LIST_ENTITY(R.id.menu_entity),
    LIST_CATEGORY(R.id.menu_category),
    LIST_SCENARIO(R.id.menu_scenario),
    LIST_MAP(R.id.menu_map),
    // Sub screen
    EDIT_ENTITY,
    EDIT_SCENARIO,
    EDIT_MAP,
    LIST_ENTITY_TEMPLATE,
    EDIT_ENTITY_TEMPLATE;


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
