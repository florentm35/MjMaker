package fr.florent.mjmaker.service.repository;

import android.util.Log;

import java.sql.SQLException;

import fr.florent.mjmaker.service.common.AbstractRepositoryService;
import fr.florent.mjmaker.service.common.SQLRuntimeException;
import fr.florent.mjmaker.service.model.Game;

public class GameRepositoryService extends AbstractRepositoryService<Game, Integer> {

    private GameRepositoryService() {
        super();
    }

    private static GameRepositoryService instance;

    public static GameRepositoryService getInstance() {
        if (instance == null) {
            instance = new GameRepositoryService();
        }
        return instance;
    }

    public Game findByName(String name) {
        try {
            return repository.queryForFirst(
                    repository.queryBuilder()
                            .where()
                            .eq("name", name)
                            .prepare()
            );
        } catch (SQLException exception) {
            Log.e(getTag(), "An erreur occured", exception);
            throw new SQLRuntimeException(exception);
        }
    }

    @Override
    public Class<Game> getTableClass() {
        return Game.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return GameRepositoryService.class.getName();
    }
}
