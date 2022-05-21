package fr.florent.mjmaker.service.repository;

import fr.florent.mjmaker.service.common.AbstractRepository;
import fr.florent.mjmaker.service.model.MapGame;

/**
 * Map service repository
 */
public class MapGameService extends AbstractRepository<MapGame, Integer> {

    private MapGameService() {
        super();
    }

    private static MapGameService instance;

    /**
     * Get service instance
     *
     * @return Service instance
     */
    public static MapGameService getInstance() {
        if (instance == null) {
            instance = new MapGameService();
        }

        return instance;
    }


    @Override
    public Class<MapGame> getTableClass() {
        return MapGame.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return MapGameService.class.getName();
    }
}
