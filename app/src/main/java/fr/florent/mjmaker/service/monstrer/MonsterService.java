package fr.florent.mjmaker.service.monstrer;

import fr.florent.mjmaker.service.common.AbstractService;

public class MonsterService extends AbstractService<Monster, Integer> {

    private MonsterService() {
        super();
    }


    private static MonsterService instance;

    public static MonsterService getInstance() {
        if (instance == null) {
            instance = new MonsterService();
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
        return MonsterService.class.getName();
    }
}
