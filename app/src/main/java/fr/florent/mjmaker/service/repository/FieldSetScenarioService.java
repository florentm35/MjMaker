package fr.florent.mjmaker.service.repository;

import fr.florent.mjmaker.injection.annotation.Inject;
import fr.florent.mjmaker.injection.annotation.Injectable;
import fr.florent.mjmaker.service.common.AbstractRepository;
import fr.florent.mjmaker.service.model.FieldSetScenario;

/**
 * FieldSetScenario service repository
 */
@Injectable
public class FieldSetScenarioService extends AbstractRepository<FieldSetScenario, Integer> {

    @Inject
    private FieldSetElementService fieldSetElementService;

    private FieldSetScenarioService() {
        super();
    }

    @Override
    public void delete(FieldSetScenario entity) {
        if (entity.getLstElement() != null) {
            entity.getLstElement().forEach(fieldSetElementService::delete);
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
        return FieldSetScenarioService.class.getName();
    }
}
