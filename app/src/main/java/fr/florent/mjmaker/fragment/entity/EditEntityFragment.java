package fr.florent.mjmaker.fragment.entity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.service.model.Entity;
import fr.florent.mjmaker.service.repository.EntityService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class EditEntityFragment extends AbstractFragment {

    private static final String TAG = EditEntityFragment.class.getName();

    private final EntityService entityService = EntityService.getInstance();


    private Entity entity;

    public EditEntityFragment(Object... params) {
        if (params != null && params.length > 0) {
            entity = entityService.findBydId((Integer) params[0]);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_monster, container, false);

        ImageButton saveButton = view.findViewById(R.id.valide);

        if (entity == null) {
            saveButton.setImageResource(R.drawable.material_add);
        } else {
            saveButton.setImageResource(R.drawable.material_done);
        }

        saveButton.setOnClickListener(this::save);

        return view;
    }


    private void save(View view) {

        if (entity == null) {
            entity = new Entity();
        }

        entity.setName(AndroidLayoutUtil.getTextViewText(getView(), R.id.et_name));

        String message;

        if (entity.getId() == null) {
            entityService.save(entity);
            message = "Monster create";
        } else {
            entityService.update(entity);
            message = "Monster update";
        }

        AndroidLayoutUtil.showToast(view.getContext(), message);

        backHandler.back();
    }

    @Override
    public boolean showBack() {
        return true;
    }
}
