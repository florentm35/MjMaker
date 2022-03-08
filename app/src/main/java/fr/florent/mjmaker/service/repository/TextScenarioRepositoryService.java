package fr.florent.mjmaker.service.repository;

import fr.florent.mjmaker.service.common.AbstractRepositoryService;
import fr.florent.mjmaker.service.model.TextScenario;

/**
 * TextScenario service repository
 */
public class TextScenarioRepositoryService extends AbstractRepositoryService<TextScenario, Integer> {

    private TextScenarioRepositoryService() {
        super();
    }

    private static TextScenarioRepositoryService instance;

    /**
     * Get service instance
     *
     * @return Service instance
     */
    public static TextScenarioRepositoryService getInstance() {
        if (instance == null) {
            instance = new TextScenarioRepositoryService();
        }

        return instance;
    }

    @Override
    public Class<TextScenario> getTableClass() {
        return TextScenario.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return TextScenarioRepositoryService.class.getName();
    }
}
