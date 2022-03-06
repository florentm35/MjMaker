package fr.florent.mjmaker.service.repository;

import android.util.Log;

import java.sql.SQLException;
import java.util.List;

import fr.florent.mjmaker.service.common.AbstractRepositoryService;
import fr.florent.mjmaker.service.common.SQLRuntimeException;
import fr.florent.mjmaker.service.model.Theme;

public class ThemeRepositoryService extends AbstractRepositoryService<Theme, Integer> {

    private static ThemeRepositoryService instance;

    private ThemeRepositoryService() {
        super();
    }

    public static ThemeRepositoryService getInstance() {
        if (instance == null) {
            instance = new ThemeRepositoryService();
        }
        return instance;
    }

    /**
     * Return all SubCategory for a given category
     *
     * @param idGame The parent game id
     * @return List of sub category
     */
    public List<Theme> findByIdGame(Integer idGame) {
        try {
            return repository.query(repository.queryBuilder()
                    .where()
                    .eq("idGame", idGame)
                    .prepare());
        } catch (SQLException exception) {
            throw new SQLRuntimeException(exception);
        }
    }

    public Theme findByIdGameAndName(Integer idGame, String name) {
        try {
            return repository.queryForFirst(
                    repository.queryBuilder()
                            .where()
                            .eq("idGame", idGame)
                            .and()
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
