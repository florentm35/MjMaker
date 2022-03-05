package fr.florent.mjmaker.service.monstrer;

import javax.inject.Inject;

import fr.florent.mjmaker.service.common.AbstractService;

public class MonsterService extends AbstractService<Monster, Integer> {

    @Inject
    public MonsterService() {
        super();
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
