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

    public List<FieldSetScenario> findByIdScenario(Integer idScenario) {

        try {
            return repository.query(
                    repository.queryBuilder()
                            .where()
                            .eq("idScenario", idScenario)
                            .prepare()
            ).stream()
                    .map(e -> deserializer().action(e))
                    .collect(Collectors.toList());
        } catch (SQLException exception) {
            Log.e(getTag(), "An erreur occured", exception);
            throw new SQLRuntimeException(exception);
        }
    }

    @Override
    protected IMapperSerializer<FieldSetScenario> serializer() {
        return (e, delete) -> {
            if (e.getLstText() != null) {
                e.getLstText().forEach(fieldSetScenario -> textScenarioRepositoryService.delete(fieldSetScenario));
            }
        };
    }

    @Override
    protected IMapperDeserializer<FieldSetScenario> deserializer() {
        return e -> {
            e.setLstText(textScenarioRepositoryService.findByIdFieldSetScenario(e.getId()));
            return e;
        };
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
