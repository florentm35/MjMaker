package fr.florent.mjmaker.service.repository;

import java.sql.SQLException;
import java.util.List;

import fr.florent.mjmaker.service.common.AbstractRepositoryService;
import fr.florent.mjmaker.service.common.SQLRuntimeException;
import fr.florent.mjmaker.service.model.SubCategory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class SubCategoryRepositoryService extends AbstractRepositoryService<SubCategory, Integer> {

    private static SubCategoryRepositoryService instance;

    private SubCategoryRepositoryService() {
        super();
    }

    public static SubCategoryRepositoryService getInstance() {
        if (instance == null) {
            instance = new SubCategoryRepositoryService();
        }
        return instance;
    }

    /**
     * Return all SubCategory for a given category
     *
     * @param idCategory The parent category id
     * @return List of sub category
     */
    public List<SubCategory> findByIdCategory(Integer idCategory) {
        try {
            return repository.query(repository.queryBuilder()
                    .where()
                    .eq("idCategory", idCategory)
                    .prepare());
        } catch (SQLException exception) {
            throw new SQLRuntimeException(exception);
        }
    }

    @Override
    public Class<SubCategory> getTableClass() {
        return SubCategory.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return SubCategoryRepositoryService.class.getName();
    }
}
