package fr.florent.mjmaker.service.repository;

import fr.florent.mjmaker.injection.annotation.Injectable;
import fr.florent.mjmaker.service.common.AbstractRepository;
import fr.florent.mjmaker.service.model.GameMapLegend;

/**
 * MapLegend service repository
 */
@Injectable
public class GameMapLegendService extends AbstractRepository<GameMapLegend, Integer> {

    private GameMapLegendService() {
        super();
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
