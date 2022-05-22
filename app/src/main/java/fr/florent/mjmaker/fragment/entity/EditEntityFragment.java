package fr.florent.mjmaker.fragment.entity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.fragment.entity.adapter.EntityVarAdapter;
import fr.florent.mjmaker.fragment.entity.modal.ParamEntityModal;
import fr.florent.mjmaker.service.model.Entity;
import fr.florent.mjmaker.service.model.EntityVar;
import fr.florent.mjmaker.service.model.TemplateVar;
import fr.florent.mjmaker.service.repository.EntityService;
import fr.florent.mjmaker.service.repository.EntityVarService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;
import fr.florent.mjmaker.utils.DataBaseUtil;

public class EditEntityFragment extends AbstractFragment {

    private final EntityService entityService = EntityService.getInstance();
    private final EntityVarService entityVarService = EntityVarService.getInstance();

    private EntityVarAdapter entityVarAdapter;

    private Entity entity;

    public EditEntityFragment(Object... params) {
        if (params != null && params.length > 0) {
            entity = (Entity) params[0];
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_entity, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.list_variable);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        entityVarAdapter = new EntityVarAdapter(getContext(), getLstEntityVars(), this::onAction);
        recyclerView.setAdapter(entityVarAdapter);

        return view;
    }

    private void onAction(EntityVarAdapter.EnumAction action, EntityVar entityVar) {
        entityVarService.update(entityVar);
    }

    private List<EntityVar> getLstEntityVars() {
        List<EntityVar> entityVars = DataBaseUtil.convertForeignCollectionToList(entity.getLstVar());
        List<TemplateVar> templateVars = DataBaseUtil.convertForeignCollectionToList(entity.getTemplate().getLstVar());

        entityVars.addAll(
                templateVars.stream()
                        .filter(templateVar ->
                                entityVars.stream()
                                        .noneMatch(entityVar -> entityVar.getTemplateVar().equals(templateVar))
                        )
                        .map(this::createEntityVar)
                        .collect(Collectors.toList())
        );
        return entityVars;
    }

    private EntityVar createEntityVar(TemplateVar t) {
        EntityVar entityVar = EntityVar.builder()
                .entity(entity)
                .templateVar(t)
                .build();

        entityVarService.save(entityVar);

        return entityVar;
    }

    @Override
    public List<ToolBarItem> getToolbarItem() {
        return Arrays.asList(
                ToolBarItem.builder()
                        .label(R.string.label_rename_template)
                        .handler(this::changeParamTemplate)
                        .icone(R.drawable.material_setting)
                        .build(),
                ToolBarItem.builder()
                        .label(R.string.label_view)
                        .handler(this::showPreview)
                        .icone(R.drawable.material_view)
                        .build()
        );
    }

    private void showPreview() {
        // Reload the entity
        Entity entityRefresh = entityService.findBydId(entity.getId());
        AndroidLayoutUtil.openModalInfo(getContext(), entityService.renderEntity(entityRefresh));
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
