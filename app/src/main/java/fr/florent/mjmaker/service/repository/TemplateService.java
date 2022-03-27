package fr.florent.mjmaker.service.repository;

import fr.florent.mjmaker.service.common.AbstractRepository;
import fr.florent.mjmaker.service.model.Template;

/**
 * Template service repository
 */
public class TemplateService extends AbstractRepository<Template, Integer> {

    private TemplateService() {
        super();
    }

    private static TemplateService instance;

    /**
     * Get service instance
     *
     * @return Service instance
     */
    public static TemplateService getInstance() {
        if (instance == null) {
            instance = new TemplateService();
        }
        return instance;
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
