package fr.florent.mjmaker.fragment.entity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collections;
import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.fragment.entity.modal.ParamEntityModal;
import fr.florent.mjmaker.fragment.entity.modal.ParamTemplateModal;
import fr.florent.mjmaker.service.model.Entity;
import fr.florent.mjmaker.service.model.Template;
import fr.florent.mjmaker.service.repository.EntityService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class EditEntityFragment extends AbstractFragment {

    private static final String TAG = EditEntityFragment.class.getName();

    private final EntityService entityService = EntityService.getInstance();


    private Entity entity;

    public EditEntityFragment(Object... params) {
        if (params != null && params.length > 0) {
            entity = (Entity) params[0];
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_entity, container, false);

        return view;
    }

    @Override
    public List<ToolBarItem> getToolbarItem() {
        return Collections.singletonList(
                ToolBarItem.builder()
                        .label(R.string.label_rename_template)
                        .handler(this::changeParamTemplate)
                        .icone(R.drawable.material_setting)
                        .build()
        );
    }

    private void changeParamTemplate() {
        ParamEntityModal dialog = new ParamEntityModal();
        dialog.show(getContext(), entity, this::saveTemplate);
    }


    private boolean saveTemplate(Entity entity) {
        if (entity.getName() == null || entity.getName().isEmpty()) {
            AndroidLayoutUtil.showToast(getContext(), getString(R.string.err_entity_name_empty));
            return false;
        }

        if (entity.getTemplate() == null) {
            AndroidLayoutUtil.showToast(getContext(), getString(R.string.err_entity_template_empty));
            return false;
        }

        entityService.update(entity);
        AndroidLayoutUtil.showToast(getContext(), getString(R.string.msg_entity_updated));
        return true;
    }


    @Override
    public boolean showBack() {
        return true;
    }
}
