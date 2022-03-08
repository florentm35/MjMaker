package fr.florent.mjmaker.utils;

import com.j256.ormlite.dao.ForeignCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class DataBaseUtil {

    public static <T> List<T> convertForeignCollectionToList(ForeignCollection<T> collection) {
        if (collection == null) {
            return Collections.emptyList();
        } else {
            return new ArrayList<>(collection);
        }
    }

}
