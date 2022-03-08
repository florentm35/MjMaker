package fr.florent.mjmaker.service.common;

import java.util.Arrays;
import java.util.List;

import fr.florent.mjmaker.service.model.FieldSetScenario;
import fr.florent.mjmaker.service.model.Game;
import fr.florent.mjmaker.service.model.Monster;
import fr.florent.mjmaker.service.model.Scenario;
import fr.florent.mjmaker.service.model.TextScenario;
import fr.florent.mjmaker.service.model.Theme;

/**
 * Helper class for reference all entity
 */
public abstract class EntityHelper {
    /**
     * List of all entity
     */
    public static final List<Class<?>> ENTITY_LIST = Arrays.asList(
            Monster.class,
            Game.class,
            Theme.class,
            Scenario.class,
            FieldSetScenario.class,
            TextScenario.class
    );
}
