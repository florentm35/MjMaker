package fr.florent.mjmaker.service.repository;

import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import fr.florent.mjmaker.injection.annotation.Injectable;
import fr.florent.mjmaker.service.common.AbstractRepository;
import fr.florent.mjmaker.service.model.EntityElement;
import fr.florent.mjmaker.service.model.FieldSetElement;
import fr.florent.mjmaker.service.model.TextElement;

/**
 * FieldSetElement service repository
 */
@Injectable
public class FieldSetElementService extends AbstractRepository<FieldSetElement, Integer> {

    private static final String TAG = FieldSetElementService.class.getName();

    private final Dao<TextElement, FieldSetElement> textElementRepository = databaseHelper.createDao(TextElement.class, FieldSetElement.class);
    private final Dao<EntityElement, FieldSetElement> entityElementRepository = databaseHelper.createDao(EntityElement.class, FieldSetElement.class);


    private FieldSetElementService() {
        super();
    }

    @Override
    public void save(FieldSetElement entity) {
        try {
            switch (entity.getDType()) {
                case TEXT:
                    textElementRepository.create((TextElement) entity.getElement());
                    break;
                case ENTITY:
                    entityElementRepository.create((EntityElement) entity.getElement());
                    break;
                case MAP:
                case BATTLE:
                    throw new RuntimeException("Not implemented");
            }
            super.save(entity);
        } catch (SQLException exception) {
            Log.e(TAG, "Can not save element : " + entity.getElement().toString(), exception);
        }

    }

    @Override
    public void update(FieldSetElement entity) {
        try {
            switch (entity.getDType()) {
                case TEXT:
                    textElementRepository.update((TextElement) entity.getElement());
                    break;
                case ENTITY:
                    entityElementRepository.update((EntityElement) entity.getElement());
                    break;
                case MAP:
                case BATTLE:
                    throw new RuntimeException("Not implemented");
            }
        } catch (SQLException exception) {
            Log.e(TAG, "Can not save element : " + entity.getElement().toString(), exception);
        }
        super.update(entity);
    }

    @Override
    public Class<FieldSetElement> getTableClass() {
        return FieldSetElement.class;
    }

    @Override
    protected Class<Integer> getTableId() {
        return Integer.class;
    }

    @Override
    protected String getTag() {
        return FieldSetElementService.class.getName();
    }
}
