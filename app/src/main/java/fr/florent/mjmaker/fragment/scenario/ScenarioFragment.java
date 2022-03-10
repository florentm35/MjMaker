package fr.florent.mjmaker.fragment.scenario;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.fragment.scenario.adapter.FieldSetAdapter;
import fr.florent.mjmaker.service.model.FieldSetScenario;
import fr.florent.mjmaker.service.model.Scenario;
import fr.florent.mjmaker.service.repository.FieldSetScenarioRepositoryService;
import fr.florent.mjmaker.service.repository.ScenarioRepositoryService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;
import fr.florent.mjmaker.utils.DataBaseUtil;

// TODO : comment class
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

        initListFieldSet(view);

        return view;
    }

    private void initListFieldSet(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.list_fieldset);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new FieldSetAdapter(getContext(), DataBaseUtil.convertForeignCollectionToList(scenario.getLstFieldSet()), this::onFieldSetAction);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = getItemTouchHelper();
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void onFieldSetAction(FieldSetAdapter.EnumAction action, FieldSetScenario fieldSetScenario) {
        switch (action) {
            case EDIT:
                editFieldSet(fieldSetScenario);
                break;
            case DELETE:
                AndroidLayoutUtil.showToast(getContext(), "Fieldset deleted");
                fieldSetScenarioRepositoryService.delete(fieldSetScenario);
                adapter.removeItem(fieldSetScenario);
                break;
        }
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


        if (creation) {
            fieldSetScenario.setScenario(scenario);
            fieldSetScenario.setOrder(scenario.getLstFieldSet().size());
            fieldSetScenarioRepositoryService.save(fieldSetScenario);
            adapter.addItem(fieldSetScenario);
            AndroidLayoutUtil.showToast(getContext(), "Fieldset created");
        } else {
            fieldSetScenarioRepositoryService.update(fieldSetScenario);
            adapter.updateItem(fieldSetScenario);
        }

        return true;
    }


    private ItemTouchHelper getItemTouchHelper() {
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();

                FieldSetScenario fromFieldSet = adapter.getItem(fromPosition);

                FieldSetScenario toFieldSet = adapter.getItem(toPosition);

                int tmpOrder = toFieldSet.getOrder();
                toFieldSet.setOrder(fromFieldSet.getOrder());
                fromFieldSet.setOrder(tmpOrder);

                adapter.swapItem(fromPosition, toPosition);

                fieldSetScenarioRepositoryService.update(fromFieldSet);
                fieldSetScenarioRepositoryService.update(toFieldSet);

                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }
        });
    }
}
