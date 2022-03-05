package fr.florent.mjmaker.service.common;

import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public abstract class AbstractService<T, ID> {

    DatabaseHelper databaseHelper;

    protected Dao<T, ID> repository;

    protected AbstractService() {
        databaseHelper = DatabaseHelper.getInstance();
        try {
            repository = databaseHelper.createDao(getTableClass(), getTableId());
        } catch (SQLException ex) {
            Log.e(getTag(), "Can not be init repository", ex);
        }
    }

    public abstract Class<T> getTableClass();

    protected abstract Class<ID> getTableId();

    protected abstract String getTag();

    public List<T> getAll() throws SQLException {
        return repository.queryForAll();
    }
    
    public T findBydId(ID id) throws SQLException {
        return repository.queryForId(id);
    }

    public void save(T entity) throws SQLException {
        repository.create(entity);
    }

    public void update(T entity) throws SQLException {
        repository.update(entity);
    }

    public void delete(T entity) throws SQLException {
        repository.delete(entity);
    }

    public void deleteById(ID id) throws SQLException {
        repository.deleteById(id);
    }

}
