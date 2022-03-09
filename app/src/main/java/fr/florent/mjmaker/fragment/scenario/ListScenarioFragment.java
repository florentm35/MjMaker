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
import java.util.function.BiFunction;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.common.menu.EnumScreen;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.fragment.scenario.recyclerview.ScenarioAdapter;
import fr.florent.mjmaker.service.model.Scenario;
import fr.florent.mjmaker.service.repository.ScenarioRepositoryService;

public class ListScenarioFragment extends AbstractFragment {

    ScenarioRepositoryService scenarioRepositoryService = ScenarioRepositoryService.getInstance();

    ScenarioAdapter scenarioAdapter;

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

    public void onAction(ScenarioAdapter.EnumAction action, Scenario scenario)  {
        switch (action) {

            case EDIT:
                this.redirectToDetailScenario(scenario);
                break;
            case DELETE:
                scenarioRepositoryService.delete(scenario);
                scenarioAdapter.removeItem(scenario);
                break;
        }
    }

    @Override
    public List<ToolBarItem> getToolbarItem() {
        return Arrays.asList(
                ToolBarItem.builder()
                        .label("New scenario")
                        .handler(() -> this.redirectToDetailScenario(null))
                        .icone(R.drawable.material_add)
                        .build()
        );
    }

    public void redirectToDetailScenario(Scenario scenario) {
        redirect.apply(EnumScreen.DETAIL_SCENARIO, new Object[]{scenario});
    }

}
