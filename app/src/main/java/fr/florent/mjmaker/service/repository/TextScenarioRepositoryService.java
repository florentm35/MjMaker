package fr.florent.mjmaker.service.repository;

import android.util.Log;

import java.sql.SQLException;
import java.util.List;

import fr.florent.mjmaker.service.common.AbstractRepositoryService;
import fr.florent.mjmaker.service.common.SQLRuntimeException;
import fr.florent.mjmaker.service.model.TextScenario;

public class TextScenarioRepositoryService extends AbstractRepositoryService<TextScenario, Integer> {

    private TextScenarioRepositoryService() {
        super();
    }

    private static TextScenarioRepositoryService instance;

    public static TextScenarioRepositoryService getInstance() {
        if (instance == null) {
            instance = new TextScenarioRepositoryService();
        }

        return instance;
    }

    public List<TextScenario> findByIdFieldSetScenario(Integer idFieldSetScenario) {

        try {
            return repository.query(
                    repository.queryBuilder()
                            .where()
                            .eq("idFieldSetScenario", idFieldSetScenario)
                            .prepare()
            );
        } catch (SQLException exception) {
            Log.e(getTag(), "An erreur occured", exception);
            throw new SQLRuntimeException(exception);
        }
    }

    @Override
    public Class<TextScenario> getTableClass() {
        return TextScenario.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return TextScenarioRepositoryService.class.getName();
    }
}
