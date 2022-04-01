package fr.florent.mjmaker.fragment.entity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.common.menu.EnumScreen;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.fragment.entity.adapter.TemplateAdapter;
import fr.florent.mjmaker.fragment.entity.modal.ParamTemplateModal;
import fr.florent.mjmaker.service.model.Template;
import fr.florent.mjmaker.service.repository.TemplateService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class ListTemplateFragment extends AbstractFragment {

    private final TemplateService templateService = TemplateService.getInstance();

    private TemplateAdapter templateAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.list_element);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        templateAdapter = new TemplateAdapter(getContext(), templateService.getAll(), this::onAction);
        recyclerView.setAdapter(templateAdapter);

        return view;
    }

    private void onAction(TemplateAdapter.EnumAction action, Template template) {
        switch (action) {

            case EDIT:
                this.redirectToDetailScenario(template);
                break;
            case DELETE:
                AndroidLayoutUtil.openModalQuestion(getContext(),
                        getString(R.string.msg_ask_delete_scenario),
                        (choice) -> {
                            if (choice) {
                                templateService.delete(template);
                                templateAdapter.removeItem(template);
                                AndroidLayoutUtil.showToast(getContext(), getString(R.string.msg_scenario_deleted));
                            }
                            return true;
                        });

                break;
        }
    }

    @Override
    public List<ToolBarItem> getToolbarItem() {
        return Collections.singletonList(
                ToolBarItem.builder()
                        .label(R.string.msg_new_template)
                        .handler(this::initTemplate)
                        .icone(R.drawable.material_add)
                        .build()
        );
    }

    private void redirectToDetailScenario(Template template) {
        redirect.apply(EnumScreen.EDIT_ENTITY_TEMPLATE, new Object[]{template});
    }

    private void initTemplate() {
        ParamTemplateModal dialog = new ParamTemplateModal();

        dialog.show(getContext(), null, this::saveTemplate);
    }

    private boolean saveTemplate(Template template) {
        templateService.save(template);
        AndroidLayoutUtil.showToast(getContext(), getString(R.string.msg_template_created));
        redirectToDetailScenario(template);
        return true;
    }

    @Override
    public boolean showBack() {
        return true;
    }
}
