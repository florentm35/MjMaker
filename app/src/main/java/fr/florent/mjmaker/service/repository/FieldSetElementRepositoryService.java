package fr.florent.mjmaker.service.repository;

import com.j256.ormlite.dao.Dao;

import fr.florent.mjmaker.service.common.AbstractRepositoryService;
import fr.florent.mjmaker.service.model.Entity;
import fr.florent.mjmaker.service.model.EntityElement;
import fr.florent.mjmaker.service.model.FieldSetElement;
import fr.florent.mjmaker.service.model.TextElement;

/**
 * TextScenario service repository
 */
public class FieldSetElementRepositoryService extends AbstractRepositoryService<FieldSetElement, Integer> {

    private final Dao<TextElement, FieldSetElement> textElementRepository = databaseHelper.createDao(TextElement.class, FieldSetElement.class);
    private final Dao<EntityElement, FieldSetElement> entityElementRepository = databaseHelper.createDao(EntityElement.class, FieldSetElement.class);


    private FieldSetElementRepositoryService() {
        super();
    }

    private static FieldSetElementRepositoryService instance;

    /**
     * Get service instance
     *
     * @return Service instance
     */
    public static FieldSetElementRepositoryService getInstance() {
        if (instance == null) {
            instance = new FieldSetElementRepositoryService();
        }

        return instance;
    }



    @Override
    public Class<FieldSetElement> getTableClass() {
        return FieldSetElement.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return FieldSetElementRepositoryService.class.getName();
    }
}
