package fr.florent.mjmaker.service.common;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import fr.florent.mjmaker.AppContext;

/**
 * Datasource for SQLite
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getName();

    /**
     * Database name
     */
    private static final String DB_NAME = "mj-maker.sqlite";
    /**
     * Database version
     */
    private static final int DB_VERSION = 9;

    /**
     * The instance
     */
    private static DatabaseHelper instance;

    /**
     * Get instance of {@link DatabaseHelper}
     *
     * @return The instance
     */
    public static DatabaseHelper getInstance() {
        if (instance == null) {
            instance = new DatabaseHelper();
        }

        return instance;
    }

    private DatabaseHelper() {
        super(AppContext.getContext(), DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, com.j256.ormlite.support.ConnectionSource connectionSource) {
        try {
            for (Pair<Class<?>, Integer> pairClassVersion : EntityHelper.ENTITY_LIST) {
                Class<?> entityClass = pairClassVersion.first;
                TableUtils.createTable(connectionSource, entityClass);
            }
        } catch (SQLException e) {
            Log.e(TAG, "Database can not be init", e);
            throw new SQLRuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, com.j256.ormlite.support.ConnectionSource connectionSource, int i, int i2) {

        try {
            for (Pair<Class<?>, Integer> pairClassVersion : EntityHelper.ENTITY_LIST) {
                Class<?> entityClass = pairClassVersion.first;
                if (DB_VERSION >= pairClassVersion.second) {
                    TableUtils.dropTable(connectionSource, entityClass, true);
                    TableUtils.createTable(connectionSource, entityClass);
                }
            }
        } catch (SQLException e) {
            Log.e(TAG, "Database can not be init", e);
            throw new SQLRuntimeException(e);
        }
    }

    /**
     * Create a repository instance
     *
     * @param object The entity class
     * @param id     The entity id class
     * @return The repository
     */
    public <T, ID> Dao<T, ID> createDao(Class<T> object, Class<ID> id) {
        try {
            return getDao(object);
        } catch (SQLException e) {
            Log.e(TAG, "Can not create " + object.getName() + " repository", e);
            throw new SQLRuntimeException(e);
        }
    }

}
