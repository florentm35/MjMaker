package fr.florent.mjmaker.service.repository;

import fr.florent.mjmaker.injection.annotation.Injectable;
import fr.florent.mjmaker.service.common.AbstractRepository;
import fr.florent.mjmaker.service.model.GameMap;

/**
 * Map service repository
 */
@Injectable
public class GameMapService extends AbstractRepository<GameMap, Integer> {

    private GameMapService() {
        super();
    }

    @Override
    public Class<GameMap> getTableClass() {
        return GameMap.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return GameMapService.class.getName();
    }
}
