package fr.florent.mjmaker.service.repository;

import fr.florent.mjmaker.service.common.AbstractRepository;
import fr.florent.mjmaker.service.model.Scenario;

/**
 * Scenario service repository
 */
public class ScenarioService extends AbstractRepository<Scenario, Integer> {

    FieldSetScenarioService fieldSetScenarioService = FieldSetScenarioService.getInstance();

    private ScenarioService() {
        super();
    }

    private static ScenarioService instance;

    @Override
    public void delete(Scenario entity) {
        if (entity.getLstFieldSet() != null) {
            entity.getLstFieldSet().forEach(fieldSetScenario -> fieldSetScenarioService.delete(fieldSetScenario));
        }
        super.delete(entity);
    }

    @Override
    public void deleteById(Integer integer) {
        delete(findBydId(integer));
    }


    /**
     * Get service instance
     *
     * @return Service instance
     */
    public static ScenarioService getInstance() {
        if (instance == null) {
            instance = new ScenarioService();
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
        return ScenarioService.class.getName();
    }
}
