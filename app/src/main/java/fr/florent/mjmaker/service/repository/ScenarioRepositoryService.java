package fr.florent.mjmaker.service.repository;

import fr.florent.mjmaker.service.common.AbstractRepositoryService;
import fr.florent.mjmaker.service.model.Scenario;

public class ScenarioRepositoryService extends AbstractRepositoryService<Scenario, Integer> {

    FieldSetScenarioRepositoryService fieldSetScenarioRepositoryService = FieldSetScenarioRepositoryService.getInstance();

    private ScenarioRepositoryService() {
        super();
    }

    private static ScenarioRepositoryService instance;

    public static ScenarioRepositoryService getInstance() {
        if (instance == null) {
            instance = new ScenarioRepositoryService();
        }

        return instance;
    }

    @Override
    public Class<Scenario> getTableClass() {
        return Scenario.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return ScenarioRepositoryService.class.getName();
    }
}
