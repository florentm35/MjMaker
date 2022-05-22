package fr.florent.mjmaker.fragment.common.menu;

import java.util.Arrays;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.category.ListGameFragment;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.entity.EditEntityFragment;
import fr.florent.mjmaker.fragment.entity.EditTemplateFragment;
import fr.florent.mjmaker.fragment.entity.ListEntityFragment;
import fr.florent.mjmaker.fragment.entity.ListTemplateFragment;
import fr.florent.mjmaker.fragment.map.EditGameMapFragment;
import fr.florent.mjmaker.fragment.map.ListMapFragment;
import fr.florent.mjmaker.fragment.scenario.ListScenarioFragment;
import fr.florent.mjmaker.fragment.scenario.ScenarioFragment;
import lombok.Getter;

/**
 * List of all screen available
 */
public enum EnumScreen {
    // Screen menu
    LIST_ENTITY(R.id.menu_entity, ListEntityFragment.class),
    LIST_CATEGORY(R.id.menu_category, ListGameFragment.class),
    LIST_SCENARIO(R.id.menu_scenario, ListScenarioFragment.class),
    LIST_MAP(R.id.menu_map, ListMapFragment.class),
    // Sub screen
    EDIT_ENTITY(EditEntityFragment.class),
    EDIT_SCENARIO(ScenarioFragment.class),
    EDIT_MAP(EditGameMapFragment.class),
    LIST_ENTITY_TEMPLATE(ListTemplateFragment.class),
    EDIT_ENTITY_TEMPLATE(EditTemplateFragment.class);


    private final Integer id;

    @Getter
    private final Class<? extends AbstractFragment> fragment;

    EnumScreen(int id, Class<? extends AbstractFragment> fragment) {
        this.id = id;
        this.fragment = fragment;
    }
    EnumScreen(Class<? extends AbstractFragment> fragment) {
        this.fragment = fragment;
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
