package fr.florent.mjmaker.service.repository;

import fr.florent.mjmaker.service.common.AbstractRepositoryService;
import fr.florent.mjmaker.service.model.Template;
import fr.florent.mjmaker.service.model.TemplateVar;

/**
 * Game service repository
 */
public class TemplateVarRepositoryService extends AbstractRepositoryService<TemplateVar, Integer> {

    private TemplateVarRepositoryService() {
        super();
    }

    private static TemplateVarRepositoryService instance;

    /**
     * Get service instance
     *
     * @return Service instance
     */
    public static TemplateVarRepositoryService getInstance() {
        if (instance == null) {
            instance = new TemplateVarRepositoryService();
        }
        return instance;
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
        return TemplateVarRepositoryService.class.getName();
    }
}
