package fr.florent.mjmaker.service.repository;

import fr.florent.mjmaker.service.common.AbstractRepositoryService;
import fr.florent.mjmaker.service.model.FieldSetScenario;
import fr.florent.mjmaker.service.model.Scenario;

/**
 * FieldSetScenario service repository
 */
public class FieldSetScenarioRepositoryService extends AbstractRepositoryService<FieldSetScenario, Integer> {

    private final FieldSetElementRepositoryService fieldSetElementRepositoryService = FieldSetElementRepositoryService.getInstance();

    private FieldSetScenarioRepositoryService() {
        super();
    }

    private static FieldSetScenarioRepositoryService instance;

    /**
     * Get service instance
     *
     * @return Service instance
     */
    public static FieldSetScenarioRepositoryService getInstance() {
        if (instance == null) {
            instance = new FieldSetScenarioRepositoryService();
        }

        return instance;
    }

    @Override
    public void delete(FieldSetScenario entity) {
        if (entity.getLstElement() != null) {
            entity.getLstElement().forEach(fieldSetElementRepositoryService::delete);
        }
        super.delete(entity);
    }

    @Override
    public void deleteById(Integer integer) {
        delete(findBydId(integer));
    }

    @Override
    public Class<FieldSetScenario> getTableClass() {
        return FieldSetScenario.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return FieldSetScenarioRepositoryService.class.getName();
    }
}
