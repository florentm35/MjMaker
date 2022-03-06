package fr.florent.mjmaker.service.common;

import java.util.Arrays;
import java.util.List;

import fr.florent.mjmaker.service.model.Category;
import fr.florent.mjmaker.service.model.Monster;
import fr.florent.mjmaker.service.model.SubCategory;

public abstract class EntityHelper {
    public static final List<Class> ENTITY_LIST = Arrays.asList(
            Monster.class,
            Category.class,
            SubCategory.class
    );
}
