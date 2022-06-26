package fr.florent.mjmaker.fragment.entity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.common.menu.EnumScreen;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.fragment.entity.adapter.EntityAdapter;
import fr.florent.mjmaker.fragment.entity.modal.ParamEntityModal;
import fr.florent.mjmaker.injection.annotation.Inject;
import fr.florent.mjmaker.service.model.Entity;
import fr.florent.mjmaker.service.repository.EntityService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class ListEntityFragment extends AbstractFragment {

    @Inject
    private EntityService entityService;

    private EntityAdapter entityAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.list_element);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        entityAdapter = new EntityAdapter(getContext(),
                entityService.getAll(),
                this::onAction);
        recyclerView.setAdapter(entityAdapter);

        return view;
    }

    private void onAction(EntityAdapter.EnumAction action, Entity entity) {
        switch (action) {

            case EDIT:
                this.redirectToEditEntity(entity);
                break;
            case DELETE:
                AndroidLayoutUtil.openModalQuestion(getContext(),
                        getString(R.string.msg_ask_delete_entity),
                        (choice) -> {
                            if (choice) {
                                entityService.delete(entity);
                                entityAdapter.removeItem(entity);
                                AndroidLayoutUtil.showToast(getContext(), getString(R.string.msg_entity_deleted));
                            }
                            return true;
                        });

                break;
        }
    }

    @Override
    public List<ToolBarItem> getToolbarItem() {
        return Arrays.asList(
                ToolBarItem.builder()
                        .label(R.string.label_add_entity)
                        .handler(this::initTemplate)
                        .icone(R.drawable.material_add)
                        .build(),
                ToolBarItem.builder()
                        .label(R.string.label_template)
                        .handler(this::redirectToListTemplate)
                        .build()
        );
    }

    private void initTemplate() {
        ParamEntityModal dialog = new ParamEntityModal();

        dialog.show(getContext(), null, this::saveEntity);
    }

    private boolean saveEntity(Entity entity) {

        if (entity.getName() == null || entity.getName().isEmpty()) {
            AndroidLayoutUtil.showToast(getContext(), getString(R.string.err_entity_name_empty));
            return false;
        }

        if (entity.getTemplate() == null) {
            AndroidLayoutUtil.showToast(getContext(), getString(R.string.err_entity_template_empty));
            return false;
        }

        entityService.save(entity);
        AndroidLayoutUtil.showToast(getContext(), getString(R.string.msg_entity_created));
        redirectToEditEntity(entity);
        return true;
    }

    public void redirectToEditEntity(Entity entity) {
        redirect.apply(EnumScreen.EDIT_ENTITY, new Object[]{entity});
    }

    public void redirectToListTemplate() {
        redirect.apply(EnumScreen.LIST_ENTITY_TEMPLATE, null);
    }
}
