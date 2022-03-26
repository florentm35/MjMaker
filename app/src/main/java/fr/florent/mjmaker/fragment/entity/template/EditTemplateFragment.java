package fr.florent.mjmaker.fragment.entity.template;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.component.MarkdownEditor;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.service.model.EnumTemplateVarType;
import fr.florent.mjmaker.service.model.Template;
import fr.florent.mjmaker.service.model.TemplateVar;
import fr.florent.mjmaker.service.repository.TemplateRepositoryService;
import fr.florent.mjmaker.service.repository.TemplateVarRepositoryService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;
import fr.florent.mjmaker.utils.DataBaseUtil;

public class EditTemplateFragment extends AbstractFragment {

    private final TemplateRepositoryService templateRepositoryService = TemplateRepositoryService.getInstance();
    private final TemplateVarRepositoryService templateVarRepositoryService = TemplateVarRepositoryService.getInstance();

    private final Template template;

    private TemplateVariableAdapter templateVariableAdapter;

    public EditTemplateFragment(Object... params) {
        template = (Template) params[0];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_template, container, false);

        MarkdownEditor editor = view.findViewById(R.id.mde_text);
        editor.setText(template.getText());
        editor.setOnTextChanged(this::onTextChange);


        templateVariableAdapter = new TemplateVariableAdapter(getContext(),
                DataBaseUtil.convertForeignCollectionToList(template.getLstVar()),
                this::onVariableChanged);

        RecyclerView listVariable = view.findViewById(R.id.list_variable);
        listVariable.setLayoutManager(new LinearLayoutManager(getContext()));
        listVariable.setAdapter(templateVariableAdapter);

        ImageButton expendBtn = view.findViewById(R.id.expend);
        expendBtn.setOnClickListener(v -> onExpend(expendBtn, listVariable));

        ImageButton addBtn = view.findViewById(R.id.add);
        addBtn.setOnClickListener(v -> addVariable());

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

    private void addVariable() {
        TemplateVar templateVar = TemplateVar.builder()
                .template(template)
                .type(EnumTemplateVarType.TEXT)
                .build();
        templateVarRepositoryService.save(templateVar);
        templateVariableAdapter.addItem(templateVar);
        // FIXME : See why the recycler not update without update the item
        templateVariableAdapter.updateItem(templateVar);
    }

    private void changeParamTemplate() {
        ParamTemplateModal dialog = new ParamTemplateModal();
        dialog.show(getContext(), template, this::saveTemplate);
    }


    private boolean saveTemplate(Template template) {
        templateRepositoryService.update(template);
        AndroidLayoutUtil.showToast(getContext(), getString(R.string.msg_template_updated));
        return true;
    }

    private void onVariableChanged(TemplateVariableAdapter.EnumAction action, TemplateVar templateVar) {
        switch (action) {
            case UPDATE:
                templateVarRepositoryService.update(templateVar);
                break;
            case DELETE:
                templateVarRepositoryService.delete(templateVar);
                templateVariableAdapter.removeItem(templateVar);
                break;
            default:
                throw new RuntimeException("Not implemented : "+action);
        }
    }

    private void onExpend(ImageButton expendBtn, View view) {
        boolean isVisible = view.getVisibility() == View.VISIBLE;
        view.setVisibility(isVisible ? View.GONE : View.VISIBLE);
        expendBtn.setImageResource(isVisible ? R.drawable.material_more : R.drawable.material_less);
    }

    private void onTextChange(String text) {
        template.setText(text);
        templateRepositoryService.update(template);
    }

    @Override
    public boolean showBack() {
        return true;
    }
}
