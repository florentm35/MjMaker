package fr.florent.mjmaker.fragment.scenario;

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
import fr.florent.mjmaker.fragment.scenario.adapter.ScenarioAdapter;
import fr.florent.mjmaker.service.model.Scenario;
import fr.florent.mjmaker.service.repository.ScenarioRepositoryService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class ListScenarioFragment extends AbstractFragment {

    private final ScenarioRepositoryService scenarioRepositoryService = ScenarioRepositoryService.getInstance();

    private ScenarioAdapter scenarioAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_scenario, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.list_scenario);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        scenarioAdapter = new ScenarioAdapter(getContext(), scenarioRepositoryService.getAll(), this::onAction);
        recyclerView.setAdapter(scenarioAdapter);

        return view;
    }

    private void onAction(ScenarioAdapter.EnumAction action, Scenario scenario) {
        switch (action) {

            case EDIT:
                this.redirectToDetailScenario(scenario);
                break;
            case DELETE:
                scenarioRepositoryService.delete(scenario);
                scenarioAdapter.removeItem(scenario);
                AndroidLayoutUtil.showToast(getContext(), "Scenario deleted");
                break;
        }
    }

    @Override
    public List<ToolBarItem> getToolbarItem() {
        return Arrays.asList(
                ToolBarItem.builder()
                        .label("New scenario")
                        .handler(this::initScenario)
                        .icone(R.drawable.material_add)
                        .build()
        );
    }

    private void redirectToDetailScenario(Scenario scenario) {
        redirect.apply(EnumScreen.DETAIL_SCENARIO, new Object[]{scenario});
    }

    private void initScenario() {
        ParamScenarioModal dialog = new ParamScenarioModal();

        dialog.show(getContext(), null, this::saveScenario);
    }

    private boolean saveScenario(Scenario scenario) {
        scenarioRepositoryService.save(scenario);
        AndroidLayoutUtil.showToast(getContext(), "Scenario created");
        redirectToDetailScenario(scenario);
        return true;
    }
}
