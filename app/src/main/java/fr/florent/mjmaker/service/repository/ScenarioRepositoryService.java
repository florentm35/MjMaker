package fr.florent.mjmaker.service.repository;

import java.util.Collections;
import java.util.List;

import fr.florent.mjmaker.service.common.AbstractRepositoryService;
import fr.florent.mjmaker.service.model.Game;
import fr.florent.mjmaker.service.model.Scenario;

/**
 * Scenario service repository
 */
public class ScenarioRepositoryService extends AbstractRepositoryService<Scenario, Integer> {

    FieldSetScenarioRepositoryService fieldSetScenarioRepositoryService = FieldSetScenarioRepositoryService.getInstance();

    private ScenarioRepositoryService() {
        super();
    }

    private static ScenarioRepositoryService instance;

    @Override
    public void delete(Scenario entity) {
        if (entity.getLstFieldSet() != null) {
            entity.getLstFieldSet().forEach(fieldSetScenario -> fieldSetScenarioRepositoryService.delete(fieldSetScenario));
        }
        super.delete(entity);
    }

    @Override
    public void deleteById(Integer integer) {
        delete(findBydId(integer));
    }


    @Override
    public List<Scenario> getAll() {
        return Collections.singletonList(Scenario.builder()
                .id(1)
                .game(Game.builder()
                        .id(1)
                        .name("Anima")
                        .build())
                .level(1)
                .title("Mon premier scenario !")
                .build());
    }

    /**
     * Get service instance
     *
     * @return Service instance
     */
    public static ScenarioRepositoryService getInstance() {
        if (instance == null) {
            instance = new ScenarioRepositoryService();
        }

        return instance;
    }

    @Override
    public Class<Scenario> getTableClass() {
        return Scenario.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return ScenarioRepositoryService.class.getName();
    }
}
