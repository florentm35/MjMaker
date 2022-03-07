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
    protected IMapperSerializer<Scenario> serializer() {
        return (e, delete) -> {
            if(e.getLstFieldSet() != null) {
                e.getLstFieldSet().forEach(fieldSetScenario -> fieldSetScenarioRepositoryService.delete(fieldSetScenario));
            }
        };
    }

    @Override
    protected IMapperDeserializer<Scenario> deserializer() {
        return e -> {
            e.setLstFieldSet(fieldSetScenarioRepositoryService.findByIdScenario(e.getId()));
            return e;
        };
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
