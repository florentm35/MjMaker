package fr.florent.mjmaker.service.repository;

import android.util.Log;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import fr.florent.mjmaker.service.common.AbstractRepositoryService;
import fr.florent.mjmaker.service.common.SQLRuntimeException;
import fr.florent.mjmaker.service.model.FieldSetScenario;

public class FieldSetScenarioRepositoryService extends AbstractRepositoryService<FieldSetScenario, Integer> {

    private TextScenarioRepositoryService textScenarioRepositoryService = TextScenarioRepositoryService.getInstance();

    private FieldSetScenarioRepositoryService() {
        super();
    }

    private static FieldSetScenarioRepositoryService instance;

    public static FieldSetScenarioRepositoryService getInstance() {
        if (instance == null) {
            instance = new FieldSetScenarioRepositoryService();
        }

        return instance;
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
