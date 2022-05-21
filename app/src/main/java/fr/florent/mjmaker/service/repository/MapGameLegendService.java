package fr.florent.mjmaker.service.repository;

import fr.florent.mjmaker.service.common.AbstractRepository;
import fr.florent.mjmaker.service.model.MapGameLegend;

/**
 * MapLegend service repository
 */
public class MapGameLegendService extends AbstractRepository<MapGameLegend, Integer> {

    private MapGameLegendService() {
        super();
    }

    private static MapGameLegendService instance;

    /**
     * Get service instance
     *
     * @return Service instance
     */
    public static MapGameLegendService getInstance() {
        if (instance == null) {
            instance = new MapGameLegendService();
        }

        return instance;
    }


    @Override
    public Class<MapGameLegend> getTableClass() {
        return MapGameLegend.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return MapGameLegendService.class.getName();
    }
}
