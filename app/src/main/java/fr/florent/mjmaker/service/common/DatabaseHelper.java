package fr.florent.mjmaker.service.common;

import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import fr.florent.mjmaker.AppContext;
import fr.florent.mjmaker.service.monstrer.Monster;

/**
 * Datasource for SQLite
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    /**
     * Database name
     */
    private static final String DB_NAME = "mj-maker.sqlite";
    /**
     * Database version
     */
    private static final int DB_VERSION = 1;

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
            TableUtils.createTable(connectionSource, Monster.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, com.j256.ormlite.support.ConnectionSource connectionSource, int i, int i2) {

    }

    /**
     * Create a repository instance
     *
     * @param Object The entity class
     * @param id The entity id class
     * @return The repository
     */
    public <T, ID> Dao<T, ID> createDao(Class<T> Object, Class<ID> id) throws SQLException {
        return getDao(Object);
    }



}
