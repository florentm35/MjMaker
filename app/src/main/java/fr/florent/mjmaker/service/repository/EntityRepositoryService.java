package fr.florent.mjmaker.service.repository;

import fr.florent.mjmaker.service.common.AbstractRepositoryService;
import fr.florent.mjmaker.service.model.Entity;

/**
 * Monster service repository
 */
public class EntityRepositoryService extends AbstractRepositoryService<Entity, Integer> {

    private EntityRepositoryService() {
        super();
    }

    private static EntityRepositoryService instance;

    /**
     * Get service instance
     *
     * @return Service instance
     */
    public static EntityRepositoryService getInstance() {
        if (instance == null) {
            instance = new EntityRepositoryService();
        }

        return instance;
    }

    @Override
    public Class<Entity> getTableClass() {
        return Entity.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return EntityRepositoryService.class.getName();
    }
}
