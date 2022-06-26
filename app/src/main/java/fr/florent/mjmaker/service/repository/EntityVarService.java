package fr.florent.mjmaker.service.repository;

import fr.florent.mjmaker.injection.annotation.Injectable;
import fr.florent.mjmaker.service.common.AbstractRepository;
import fr.florent.mjmaker.service.model.EntityVar;

/**
 * EntityVar service repository
 */
@Injectable
public class EntityVarService extends AbstractRepository<EntityVar, Integer> {

    private EntityVarService() {
        super();
    }

    @Override
    public Class<EntityVar> getTableClass() {
        return EntityVar.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return EntityVarService.class.getName();
    }
}
