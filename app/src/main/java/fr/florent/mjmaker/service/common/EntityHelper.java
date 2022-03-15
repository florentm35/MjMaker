package fr.florent.mjmaker.service.common;

import java.util.Arrays;
import java.util.List;

import fr.florent.mjmaker.service.model.EntityElement;
import fr.florent.mjmaker.service.model.FieldSetElement;
import fr.florent.mjmaker.service.model.FieldSetScenario;
import fr.florent.mjmaker.service.model.Game;
import fr.florent.mjmaker.service.model.Entity;
import fr.florent.mjmaker.service.model.Scenario;
import fr.florent.mjmaker.service.model.Template;
import fr.florent.mjmaker.service.model.TemplateVar;
import fr.florent.mjmaker.service.model.TextElement;
import fr.florent.mjmaker.service.model.Theme;

/**
 * Helper class for reference all entity
 */
public abstract class EntityHelper {
    /**
     * List of all entity
     */
    public static final List<Class<?>> ENTITY_LIST = Arrays.asList(
            Entity.class,
            Game.class,
            Theme.class,
            Scenario.class,
            FieldSetScenario.class,
            FieldSetElement.class,
            EntityElement.class,
            TextElement.class,
            Template.class,
            TemplateVar.class
    );
}
