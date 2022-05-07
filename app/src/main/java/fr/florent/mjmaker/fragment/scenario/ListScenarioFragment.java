package fr.florent.mjmaker.fragment.scenario;

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
import fr.florent.mjmaker.fragment.scenario.adapter.ScenarioAdapter;
import fr.florent.mjmaker.service.model.Scenario;
import fr.florent.mjmaker.service.repository.ScenarioService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class ListScenarioFragment extends AbstractFragment {

    private final ScenarioService scenarioService = ScenarioService.getInstance();

    private ScenarioAdapter scenarioAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.list_element);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        scenarioAdapter = new ScenarioAdapter(getContext(), scenarioService.getAll(), this::onAction);
        recyclerView.setAdapter(scenarioAdapter);

        return view;
    }

    private void onAction(ScenarioAdapter.EnumAction action, Scenario scenario) {
        switch (action) {

            case VIEW:
                this.redirectToDetailScenario(scenario, ScenarioFragment.EnumState.VIEW);
                break;
            case DELETE:
                AndroidLayoutUtil.openModalQuestion(getContext(),
                        getString(R.string.msg_ask_delete_scenario),
                        (choice) -> {
                            if (choice) {
                                scenarioService.delete(scenario);
                                scenarioAdapter.removeItem(scenario);
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
                        .label(R.string.label_new_scenario)
                        .handler(this::initScenario)
                        .icone(R.drawable.material_add)
                        .build()
        );
    }

    private void redirectToDetailScenario(Scenario scenario, ScenarioFragment.EnumState state) {
        redirect.apply(EnumScreen.EDIT_SCENARIO, new Object[]{scenario, state});
    }

    private void initScenario() {
        ParamScenarioModal dialog = new ParamScenarioModal();

        dialog.show(getContext(), null, this::saveScenario);
    }

    private boolean saveScenario(Scenario scenario) {
        scenarioService.save(scenario);
        AndroidLayoutUtil.showToast(getContext(), getString(R.string.msg_scenario_created));
        redirectToDetailScenario(scenario, ScenarioFragment.EnumState.EDIT);
        return true;
    }
}
