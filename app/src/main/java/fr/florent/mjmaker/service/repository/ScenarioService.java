package fr.florent.mjmaker.service.repository;

import fr.florent.mjmaker.injection.annotation.Inject;
import fr.florent.mjmaker.injection.annotation.Injectable;
import fr.florent.mjmaker.service.common.AbstractRepository;
import fr.florent.mjmaker.service.model.Scenario;

/**
 * Scenario service repository
 */
@Injectable
public class ScenarioService extends AbstractRepository<Scenario, Integer> {

    @Inject
    private FieldSetScenarioService fieldSetScenarioService;

    private ScenarioService() {
        super();
    }

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
