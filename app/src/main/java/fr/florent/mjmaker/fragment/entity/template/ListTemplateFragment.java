package fr.florent.mjmaker.fragment.entity.template;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.common.menu.EnumScreen;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.fragment.scenario.ParamScenarioModal;
import fr.florent.mjmaker.fragment.scenario.ScenarioFragment;
import fr.florent.mjmaker.service.model.Template;
import fr.florent.mjmaker.service.repository.TemplateRepositoryService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class ListTemplateFragment extends AbstractFragment {

    private final TemplateRepositoryService templateRepositoryService = TemplateRepositoryService.getInstance();

    private TemplateAdapter templateAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.list_element);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        templateAdapter = new TemplateAdapter(getContext(), templateRepositoryService.getAll(), this::onAction);
        recyclerView.setAdapter(templateAdapter);

        return view;
    }

    private void onAction(TemplateAdapter.EnumAction action, Template template) {
        switch (action) {

            case VIEW:
                this.redirectToDetailScenario(template);
                break;
            case DELETE:
                AndroidLayoutUtil.openModalQuestion(getContext(),
                        "Did you want really delete this scenario ?",
                        (choice) -> {
                            if (choice) {
                                templateRepositoryService.delete(template);
                                templateAdapter.removeItem(template);
                                AndroidLayoutUtil.showToast(getContext(), "Scenario deleted");
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
                        .label("New template")
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
        templateRepositoryService.save(template);
        AndroidLayoutUtil.showToast(getContext(), "Template created");
        redirectToDetailScenario(template);
        return true;
    }
}