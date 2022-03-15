package fr.florent.mjmaker.service.repository;

import android.util.Log;

import java.sql.SQLException;

import fr.florent.mjmaker.service.common.AbstractRepositoryService;
import fr.florent.mjmaker.service.common.SQLRuntimeException;
import fr.florent.mjmaker.service.model.Game;
import fr.florent.mjmaker.service.model.Template;

/**
 * Game service repository
 */
public class TemplateRepositoryService extends AbstractRepositoryService<Template, Integer> {

    private TemplateRepositoryService() {
        super();
    }

    private static TemplateRepositoryService instance;

    /**
     * Get service instance
     *
     * @return Service instance
     */
    public static TemplateRepositoryService getInstance() {
        if (instance == null) {
            instance = new TemplateRepositoryService();
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
        return TemplateRepositoryService.class.getName();
    }
}
