package fr.florent.mjmaker.service.repository;

import android.util.Log;

import androidx.core.widget.TintableImageSourceView;

import com.j256.ormlite.stmt.query.In;

import java.sql.SQLException;

import fr.florent.mjmaker.service.common.AbstractRepositoryService;
import fr.florent.mjmaker.service.common.SQLRuntimeException;
import fr.florent.mjmaker.service.model.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class CategoryRepositoryService extends AbstractRepositoryService<Category, Integer> {

    private CategoryRepositoryService() {
        super();
    }

    private static CategoryRepositoryService instance;

    public static CategoryRepositoryService getInstance() {
        if (instance == null) {
            instance = new CategoryRepositoryService();
        }
        return instance;
    }

    public Category findByName(String name) {
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
    public Class<Category> getTableClass() {
        return Category.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return CategoryRepositoryService.class.getName();
    }
}
