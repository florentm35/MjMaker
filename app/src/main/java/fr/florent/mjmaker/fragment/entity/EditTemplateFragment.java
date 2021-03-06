package fr.florent.mjmaker.fragment.entity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.component.MarkdownEditor;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.fragment.entity.adapter.TemplateVarAdapter;
import fr.florent.mjmaker.fragment.entity.modal.ParamTemplateModal;
import fr.florent.mjmaker.injection.annotation.Inject;
import fr.florent.mjmaker.service.model.EnumTemplateVarType;
import fr.florent.mjmaker.service.model.Template;
import fr.florent.mjmaker.service.model.TemplateVar;
import fr.florent.mjmaker.service.repository.TemplateService;
import fr.florent.mjmaker.service.repository.TemplateVarService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;
import fr.florent.mjmaker.utils.DataBaseUtil;

public class EditTemplateFragment extends AbstractFragment {

    @Inject
    private TemplateService templateService;
    @Inject
    private TemplateVarService templateVarService;

    private final Template template;

    private TemplateVarAdapter templateVarAdapter;

    public EditTemplateFragment(Object... params) {
        template = (Template) params[0];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_template, container, false);

        MarkdownEditor editor = view.findViewById(R.id.mde_text);
        editor.setText(template.getText());
        editor.setOnTextChanged(this::onTextChange);


        templateVarAdapter = new TemplateVarAdapter(getContext(),
                DataBaseUtil.convertForeignCollectionToList(template.getLstVar()),
                this::onVariableChanged);

        RecyclerView listVariable = view.findViewById(R.id.list_variable);
        listVariable.setLayoutManager(new LinearLayoutManager(getContext()));
        listVariable.setAdapter(templateVarAdapter);

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
        templateVarService.save(templateVar);
        templateVarAdapter.addItem(templateVar);
        // FIXME : See why the recycler not update without update the item
        templateVarAdapter.updateItem(templateVar);
    }

    private void changeParamTemplate() {
        ParamTemplateModal dialog = new ParamTemplateModal();
        dialog.show(getContext(), template, this::saveTemplate);
    }


    private boolean saveTemplate(Template template) {
        templateService.update(template);
        AndroidLayoutUtil.showToast(getContext(), getString(R.string.msg_template_updated));
        return true;
    }

    private void onVariableChanged(TemplateVarAdapter.EnumAction action, TemplateVar templateVar) {
        switch (action) {
            case UPDATE:
                templateVarService.update(templateVar);
                break;
            case DELETE:
                templateVarService.delete(templateVar);
                templateVarAdapter.removeItem(templateVar);
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
        templateService.update(template);
    }

    @Override
    public boolean showBack() {
        return true;
    }
}
