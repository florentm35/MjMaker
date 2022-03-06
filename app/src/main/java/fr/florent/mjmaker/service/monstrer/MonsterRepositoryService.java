package fr.florent.mjmaker.service.monstrer;

import fr.florent.mjmaker.service.common.AbstractRepositoryService;

public class MonsterRepositoryService extends AbstractRepositoryService<Monster, Integer> {

    private MonsterRepositoryService() {
        super();
    }

    private static MonsterRepositoryService instance;

    public static MonsterRepositoryService getInstance() {
        if (instance == null) {
            instance = new MonsterRepositoryService();
        }

        return instance;
    }

    @Override
    public Class<Monster> getTableClass() {
        return Monster.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return MonsterRepositoryService.class.getName();
    }
}
