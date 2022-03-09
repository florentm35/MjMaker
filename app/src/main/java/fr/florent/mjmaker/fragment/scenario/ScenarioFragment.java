package fr.florent.mjmaker.fragment.scenario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.fragment.scenario.recyclerview.FieldSetAdapter;
import fr.florent.mjmaker.service.model.FieldSetScenario;
import fr.florent.mjmaker.service.model.Scenario;
import fr.florent.mjmaker.service.repository.FieldSetScenarioRepositoryService;
import fr.florent.mjmaker.service.repository.ScenarioRepositoryService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;
import fr.florent.mjmaker.utils.DataBaseUtil;

public class ScenarioFragment extends AbstractFragment {

    private final ScenarioRepositoryService scenarioRepositoryService = ScenarioRepositoryService.getInstance();
    private final FieldSetScenarioRepositoryService fieldSetScenarioRepositoryService = FieldSetScenarioRepositoryService.getInstance();

    private Scenario scenario;

    FieldSetAdapter adapter;

    public ScenarioFragment(Object[] param) {
        super();
        if (param != null && param.length > 0 && param[0] != null) {
            scenario = (Scenario) param[0];
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_scenario, container, false);

        if(scenario == null) {
            // TODO init scenario
            scenario = new Scenario();
            scenarioRepositoryService.save(scenario);
        }

        initListFieldSet(view);

        return view;
    }

    private void initListFieldSet(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.list_fieldset);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new FieldSetAdapter(getContext(), DataBaseUtil.convertForeignCollectionToList(scenario.getLstFieldSet()), null);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public List<ToolBarItem> getToolbarItem() {

        List<ToolBarItem> items = new ArrayList<>();

        items.add(
                ToolBarItem.builder()
                        .label("New scenario")
                        .handler(() -> editFieldSet(null))
                        .icone(R.drawable.material_add)
                        .build()
        );

        return items;
    }

    private void editFieldSet(FieldSetScenario fieldSetScenario) {
        AndroidLayoutUtil.openModalAskText(getContext(), "Set the name",
                fieldSetScenario != null ? fieldSetScenario.getTitle() : null,
                (v) -> saveFieldSet(v, fieldSetScenario));
    }

    private boolean saveFieldSet(String title, FieldSetScenario fieldSetScenario) {
        boolean creation = fieldSetScenario == null;

        if (creation) {
            fieldSetScenario = new FieldSetScenario();
        }

        fieldSetScenario.setTitle(title);
        fieldSetScenario.setOrder(DataBaseUtil.convertForeignCollectionToList(scenario.getLstFieldSet()).size());
        fieldSetScenario.setScenario(scenario);
        // TODO update adapter

        if (creation) {
            fieldSetScenarioRepositoryService.save(fieldSetScenario);
            adapter.addItem(fieldSetScenario);
        } else {
            fieldSetScenarioRepositoryService.update(fieldSetScenario);
            adapter.updateItem(fieldSetScenario);
        }

        return true;
    }

}
