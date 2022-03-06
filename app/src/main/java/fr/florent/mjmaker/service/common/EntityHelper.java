package fr.florent.mjmaker.service.common;

import java.util.Arrays;
import java.util.List;

import fr.florent.mjmaker.service.model.Game;
import fr.florent.mjmaker.service.model.Monster;
import fr.florent.mjmaker.service.model.Theme;

public abstract class EntityHelper {
    public static final List<Class> ENTITY_LIST = Arrays.asList(
            Monster.class,
            Game.class,
            Theme.class
    );
}
