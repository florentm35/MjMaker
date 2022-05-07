package fr.florent.mjmaker.service.repository;

import fr.florent.mjmaker.service.common.AbstractRepository;
import fr.florent.mjmaker.service.model.Map;
import fr.florent.mjmaker.service.model.MapLegend;

/**
 * MapLegend service repository
 */
public class MapLegendService extends AbstractRepository<MapLegend, Integer> {

    private MapLegendService() {
        super();
    }

    private static MapLegendService instance;

    /**
     * Get service instance
     *
     * @return Service instance
     */
    public static MapLegendService getInstance() {
        if (instance == null) {
            instance = new MapLegendService();
        }

        return instance;
    }


    @Override
    public Class<MapLegend> getTableClass() {
        return MapLegend.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return MapLegendService.class.getName();
    }
}
