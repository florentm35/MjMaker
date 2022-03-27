package fr.florent.mjmaker.service.repository;

import fr.florent.mjmaker.service.common.AbstractRepository;
import fr.florent.mjmaker.service.model.TemplateVar;

/**
 * TemplateVar service repository
 */
public class TemplateVarService extends AbstractRepository<TemplateVar, Integer> {

    private TemplateVarService() {
        super();
    }

    private static TemplateVarService instance;

    /**
     * Get service instance
     *
     * @return Service instance
     */
    public static TemplateVarService getInstance() {
        if (instance == null) {
            instance = new TemplateVarService();
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
        return TemplateVarService.class.getName();
    }
}
