package fr.florent.mjmaker.service.repository;

import android.util.Log;

import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;

import fr.florent.mjmaker.service.common.AbstractRepositoryService;
import fr.florent.mjmaker.service.common.SQLRuntimeException;
import fr.florent.mjmaker.service.model.Game;
import fr.florent.mjmaker.service.model.Theme;

/**
 * Theme service repository
 */
public class ThemeRepositoryService extends AbstractRepositoryService<Theme, Integer> {

    private static ThemeRepositoryService instance;

    private GameRepositoryService gameRepositoryService = GameRepositoryService.getInstance();

    private ThemeRepositoryService() {
        super();
    }

    /**
     * Get service instance
     * 
     * @return Service instance
     */
    public static ThemeRepositoryService getInstance() {
        if (instance == null) {
            instance = new ThemeRepositoryService();
        }
        return instance;
    }

    public Theme findByIdGameAndName(Integer idGame, String name) {
        try {

            QueryBuilder<Game, Integer> gameQueryBuilder = gameRepositoryService.getRepository()
                    .queryBuilder();
            gameQueryBuilder
                    .where()
                    .eq("id", idGame);

            return repository.queryForFirst(
                    repository.queryBuilder()
                            .join(gameQueryBuilder)
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
    public Class<Theme> getTableClass() {
        return Theme.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return ThemeRepositoryService.class.getName();
    }
}
