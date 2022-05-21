package fr.florent.mjmaker.service.common;

import android.util.Pair;

import java.util.Arrays;
import java.util.List;

import fr.florent.mjmaker.service.model.EntityElement;
import fr.florent.mjmaker.service.model.EntityVar;
import fr.florent.mjmaker.service.model.FieldSetElement;
import fr.florent.mjmaker.service.model.FieldSetScenario;
import fr.florent.mjmaker.service.model.Game;
import fr.florent.mjmaker.service.model.Entity;
import fr.florent.mjmaker.service.model.MapGame;
import fr.florent.mjmaker.service.model.MapGameLegend;
import fr.florent.mjmaker.service.model.Scenario;
import fr.florent.mjmaker.service.model.Template;
import fr.florent.mjmaker.service.model.TemplateVar;
import fr.florent.mjmaker.service.model.TextElement;
import fr.florent.mjmaker.service.model.Theme;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Helper class for reference all entity
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class EntityHelper {

    /**
     * List of all entity
     */
    // TODO : create a real migrate process or use flyway like
    public static final List<Pair<Class<?>, Integer>> ENTITY_LIST = Arrays.asList(
            Pair.create(Entity.class, 0),
            Pair.create(EntityVar.class, 0),
            Pair.create(Game.class, 0),
            Pair.create(Theme.class, 0),
            Pair.create(Scenario.class, 0),
            Pair.create(FieldSetScenario.class, 0),
            Pair.create(FieldSetElement.class, 0),
            Pair.create(EntityElement.class, 0),
            Pair.create(TextElement.class, 0),
            Pair.create(Template.class, 0),
            Pair.create(TemplateVar.class, 0),
            Pair.create(MapGame.class, 9),
            Pair.create(MapGameLegend.class, 9)
    );
}
