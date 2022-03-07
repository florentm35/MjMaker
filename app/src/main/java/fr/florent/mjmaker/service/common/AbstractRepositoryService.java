package fr.florent.mjmaker.service.common;

import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract service class with repository
 *
 * @param <T>  The entity class
 * @param <ID> The id class
 */
public abstract class AbstractRepositoryService<T, ID> {


    protected interface IMapperSerializer<T> {
        void action(T entity, boolean delete);
    }


    protected interface IMapperDeserializer<T> {
        T action(T entity);
    }

    /**
     * Datasource sqlite
     */
    DatabaseHelper databaseHelper;

    /**
     * Service repository
     */
    protected Dao<T, ID> repository;

    protected AbstractRepositoryService() {
        databaseHelper = DatabaseHelper.getInstance();
        try {
            repository = databaseHelper.createDao(getTableClass(), getTableId());
        } catch (SQLException ex) {
            Log.e(getTag(), "Can not be init repository", ex);
            throw new SQLRuntimeException(ex);
        }
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
            List<T> lstEntity = repository.queryForAll();
            return lstEntity == null
                    ? null
                    : lstEntity.stream()
                    .map(e -> deserializer().action(e))
                    .collect(Collectors.toList());

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
            T entity = repository.queryForId(id);
            return deserializer().action(entity);
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
            serializer().action(entity, false);
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
            serializer().action(entity, false);
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
            serializer().action(entity, true);
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

    /**
     * Method call after {@link AbstractRepositoryService#findBydId} {@link AbstractRepositoryService#getAll}
     *
     * @return Deserialize action
     */
    protected IMapperDeserializer<T> deserializer() {
        return entity1 -> entity1;

    }


    /**
     * Method call before {@link AbstractRepositoryService#save} {@link AbstractRepositoryService#update}
     * and after {@link AbstractRepositoryService#delete}
     *
     * @return Serialize action
     */
    protected IMapperSerializer<T> serializer() {
        return (entity1, delete) -> {
        };
    }

}
