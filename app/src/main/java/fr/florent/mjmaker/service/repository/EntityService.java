package fr.florent.mjmaker.service.repository;

import fr.florent.mjmaker.service.common.AbstractRepository;
import fr.florent.mjmaker.service.model.Entity;

/**
 * Entity service repository
 */
public class EntityService extends AbstractRepository<Entity, Integer> {

    private EntityService() {
        super();
    }

    private static EntityService instance;

    /**
     * Get service instance
     *
     * @return Service instance
     */
    public static EntityService getInstance() {
        if (instance == null) {
            instance = new EntityService();
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
        return EntityService.class.getName();
    }
}
