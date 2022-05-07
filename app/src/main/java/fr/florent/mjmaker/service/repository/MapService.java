package fr.florent.mjmaker.service.repository;

import java.util.stream.Collectors;

import fr.florent.mjmaker.service.common.AbstractRepository;
import fr.florent.mjmaker.service.markdown.MarkDownService;
import fr.florent.mjmaker.service.model.Entity;
import fr.florent.mjmaker.service.model.EntityVar;
import fr.florent.mjmaker.service.model.Map;
import fr.florent.mjmaker.utils.DataBaseUtil;

/**
 * Map service repository
 */
public class MapService extends AbstractRepository<Map, Integer> {

    private MapService() {
        super();
    }

    private static MapService instance;

    /**
     * Get service instance
     *
     * @return Service instance
     */
    public static MapService getInstance() {
        if (instance == null) {
            instance = new MapService();
        }

        return instance;
    }


    @Override
    public Class<Map> getTableClass() {
        return Map.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return MapService.class.getName();
    }
}
