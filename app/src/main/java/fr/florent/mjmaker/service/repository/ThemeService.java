package fr.florent.mjmaker.service.repository;

import android.util.Log;

import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;

import fr.florent.mjmaker.service.common.AbstractRepository;
import fr.florent.mjmaker.service.common.SQLRuntimeException;
import fr.florent.mjmaker.service.model.Game;
import fr.florent.mjmaker.service.model.Theme;

/**
 * Theme service repository
 */
public class ThemeService extends AbstractRepository<Theme, Integer> {

    private static ThemeService instance;

    private final GameService gameService = GameService.getInstance();

    private ThemeService() {
        super();
    }

    /**
     * Get service instance
     *
     * @return Service instance
     */
    public static ThemeService getInstance() {
        if (instance == null) {
            instance = new ThemeService();
        }
        return instance;
    }


    /**
     * Search theme by game and name
     *
     * @param idGame The game id
     * @param name The theme name
     * @return The theme if found or else null
     */
    public Theme findByIdGameAndName(Integer idGame, String name) {
        try {

            QueryBuilder<Game, Integer> gameQueryBuilder = gameService.getRepository()
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
        return ThemeService.class.getName();
    }
}
