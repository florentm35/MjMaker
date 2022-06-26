package fr.florent.mjmaker.service.common;

import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import fr.florent.mjmaker.injection.annotation.Injectable;
import fr.florent.mjmaker.service.model.FieldSetElement;
import fr.florent.mjmaker.service.model.TextElement;

/**
 * Abstract service class with repository
 *
 * @param <T>  The entity class
 * @param <ID> The id class
 */
public abstract class AbstractRepository<T, ID> {

    /**
     * Datasource sqlite
     */
    protected DatabaseHelper databaseHelper;

    /**
     * Service repository
     */
    protected Dao<T, ID> repository;

    protected AbstractRepository() {
        databaseHelper = DatabaseHelper.getInstance();
        repository = databaseHelper.createDao(getTableClass(), getTableId());
    }

    /**
     * Get the entity class
     *
     * @return <T> the entity class
     */
    public abstract Class<T> getTableClass();

    /**
     * Get the id class
     *
     * @return The id class
     */
    protected abstract Class<ID> getTableId();

    /**
     * The service tag for log
     *
     * @return The service tag
     */
    protected abstract String getTag();

    /**
     * Get all entity from repository
     *
     * @return Return all entity of repository
     */
    public List<T> getAll() {
        try {
            return repository.queryForAll();
        } catch (SQLException exception) {
            throw new SQLRuntimeException(exception);
        }
    }

    /**
     * Find the entity by id
     *
     * @param id Entity id
     * @return The entity if found else null
     */
    public T findBydId(ID id) {

        try {
            return repository.queryForId(id);
        } catch (SQLException exception) {
            throw new SQLRuntimeException(exception);
        }
    }

    /**
     * Persist an entity
     *
     * @param entity The entity
     */
    public void save(T entity) {
        try {
            repository.create(entity);
        } catch (SQLException exception) {
            throw new SQLRuntimeException(exception);
        }
    }

    /**
     * Merge an entity
     *
     * @param entity The entity
     */
    public void update(T entity) {
        try {
            repository.update(entity);
        } catch (SQLException exception) {
            throw new SQLRuntimeException(exception);
        }
    }

    /**
     * Delete an entity
     *
     * @param entity The entity
     */
    public void delete(T entity) {
        try {
            repository.delete(entity);
        } catch (SQLException exception) {
            throw new SQLRuntimeException(exception);
        }
    }

    /**
     * Delete an entity by id
     *
     * @param id The entity id
     */
    public void deleteById(ID id) {
        try {
            repository.deleteById(id);
        } catch (SQLException exception) {
            throw new SQLRuntimeException(exception);
        }
    }

    public void refresh(T entity) {
        try {
            repository.refresh(entity);
        } catch (SQLException exception) {
            throw new SQLRuntimeException(exception);
        }
    }

    /**
     * Get the repository instance
     *
     * @return The repository
     */
    public Dao<T, ID> getRepository() {
        return repository;
    }

}
