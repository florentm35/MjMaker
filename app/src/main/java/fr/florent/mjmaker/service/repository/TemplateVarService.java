package fr.florent.mjmaker.service.repository;

import fr.florent.mjmaker.injection.annotation.Injectable;
import fr.florent.mjmaker.service.common.AbstractRepository;
import fr.florent.mjmaker.service.model.TemplateVar;

/**
 * TemplateVar service repository
 */
@Injectable
public class TemplateVarService extends AbstractRepository<TemplateVar, Integer> {

    private TemplateVarService() {
        super();
    }

    @Override
    public Class<TemplateVar> getTableClass() {
        return TemplateVar.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return TemplateVarService.class.getName();
    }
}
