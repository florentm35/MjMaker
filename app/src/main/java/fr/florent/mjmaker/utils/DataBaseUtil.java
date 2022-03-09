package fr.florent.mjmaker.utils;

import com.j256.ormlite.dao.ForeignCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Database util
 */
public abstract class DataBaseUtil {

    /**
     * Convert a {@link ForeignCollection} to {@link List}
     *
     * @param collection The collection to convert
     * @return A copy of given collection to list
     */
    public static <T> List<T> convertForeignCollectionToList(ForeignCollection<T> collection) {
        if (collection == null) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(collection);
        }
    }

}
