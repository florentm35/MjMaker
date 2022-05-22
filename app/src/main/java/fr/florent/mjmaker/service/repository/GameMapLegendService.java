package fr.florent.mjmaker.service.repository;

import fr.florent.mjmaker.service.common.AbstractRepository;
import fr.florent.mjmaker.service.model.GameMapLegend;

/**
 * MapLegend service repository
 */
public class GameMapLegendService extends AbstractRepository<GameMapLegend, Integer> {

    private GameMapLegendService() {
        super();
    }

    private static GameMapLegendService instance;

    /**
     * Get service instance
     *
     * @return Service instance
     */
    public static GameMapLegendService getInstance() {
        if (instance == null) {
            instance = new GameMapLegendService();
        }

        return instance;
    }


    @Override
    public Class<GameMapLegend> getTableClass() {
        return GameMapLegend.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return GameMapLegendService.class.getName();
    }
}
