package fr.florent.mjmaker.service.repository;

import fr.florent.mjmaker.injection.annotation.Injectable;
import fr.florent.mjmaker.service.common.AbstractRepository;
import fr.florent.mjmaker.service.model.Template;

/**
 * Template service repository
 */
@Injectable
public class TemplateService extends AbstractRepository<Template, Integer> {

    private TemplateService() {
        super();
    }

    @Override
    public Class<Template> getTableClass() {
        return Template.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return TemplateService.class.getName();
    }
}
