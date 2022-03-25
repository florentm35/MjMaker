package fr.florent.mjmaker.fragment.scenario;

import android.os.Bundle;
import android.util.Log;
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
import fr.florent.mjmaker.service.model.FieldSetElement;
import fr.florent.mjmaker.service.model.FieldSetScenario;
import fr.florent.mjmaker.service.model.Scenario;
import fr.florent.mjmaker.service.repository.FieldSetElementRepositoryService;
import fr.florent.mjmaker.service.repository.FieldSetScenarioRepositoryService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;
import fr.florent.mjmaker.utils.DataBaseUtil;

// TODO : comment class
public class ScenarioFragment extends AbstractFragment {

    private final static String TAG = ScenarioFragment.class.getName();

    public enum EnumState {
        VIEW, EDIT;
    }

    private final FieldSetScenarioRepositoryService fieldSetScenarioRepositoryService = FieldSetScenarioRepositoryService.getInstance();
    private final FieldSetElementRepositoryService fieldSetElementRepositoryService = FieldSetElementRepositoryService.getInstance();

    private Scenario scenario;

    private FieldSetAdapter adapter;

    private EnumState state;

    private ItemTouchHelper itemTouchHelper;

    public ScenarioFragment(Object[] param) {
        super();
        if (param != null) {
            if (param.length > 0 && param[0] != null) {
                scenario = (Scenario) param[0];
            }
            if (param.length > 1 && param[1] != null) {
                state = (EnumState) param[1];
            } else {
                state = EnumState.VIEW;
            }
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

        adapter = new FieldSetAdapter(getContext(),
                DataBaseUtil.convertForeignCollectionToList(scenario.getLstFieldSet()),
                state,
                this::onFieldSetAction,
                this::requestDrag);
        recyclerView.setAdapter(adapter);

         itemTouchHelper = getItemTouchHelper();
         itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void requestDrag(RecyclerView.ViewHolder viewHolder) {
        if(itemTouchHelper!= null && state == EnumState.EDIT) {
            itemTouchHelper.startDrag(viewHolder);
        }
    }

    private void onFieldSetAction(FieldSetAdapter.EnumAction action, FieldSetScenario fieldSetScenario, FieldSetElement element) {
        switch (action) {
            case UPDATE:
                adapter.updateItem(fieldSetScenario);
                break;
            case EDIT:
                editFieldSet(fieldSetScenario);
                break;
            case DELETE:
                AndroidLayoutUtil.openModalQuestion(getContext(),
                        getString(R.string.msg_ask_delete_fieldset),
                        (choice) -> {
                            if (choice) {
                                fieldSetScenarioRepositoryService.delete(fieldSetScenario);
                                adapter.removeItem(fieldSetScenario);
                                AndroidLayoutUtil.showToast(getContext(), getString(R.string.msg_fieldset_deleted));
                            }
                            return true;
                        });
                break;
            case ADD_ELEMENT:
                fieldSetElementRepositoryService.save(element);
                fieldSetScenarioRepositoryService.refresh(fieldSetScenario);
                adapter.updateItem(fieldSetScenario);
                break;
            case UPDATE_ELEMENT:
                Log.d(TAG, String.format("Update element id :%d, ordre: %d", element.getId(), element.getOrder()));
                fieldSetElementRepositoryService.update(element);
                fieldSetScenarioRepositoryService.refresh(fieldSetScenario);
                break;
            case DELETE_ELEMENT:
                AndroidLayoutUtil.openModalQuestion(getContext(),
                        getString(R.string.msg_ask_delete_element),
                        (choice) -> {
                            if (choice) {
                                fieldSetElementRepositoryService.delete(element);
                                fieldSetScenarioRepositoryService.refresh(fieldSetScenario);
                                adapter.updateItem(fieldSetScenario);
                                AndroidLayoutUtil.showToast(getContext(), getString(R.string.msg_element_deleted));
                            }
                            return true;
                        });
                break;
        }
    }

    @Override
    public List<ToolBarItem> getToolbarItem() {
        List<ToolBarItem> lstItem = new ArrayList<>();

        if (state == EnumState.EDIT) {
            lstItem.add(ToolBarItem.builder()
                    .label(R.string.label_fieldset_new)
                    .handler(() -> editFieldSet(null))
                    .icone(R.drawable.material_add)
                    .build()
            );

            lstItem.add(ToolBarItem.builder()
                    .label(R.string.label_view_mode)
                    .handler(this::switchMode)
                    .build());
        } else {
            lstItem.add(ToolBarItem.builder()
                    .label(R.string.label_edit_mode)
                    .handler(this::switchMode)
                    .build());
        }
        return lstItem;
    }

    private void switchMode() {
        state = state == EnumState.EDIT ? EnumState.VIEW : EnumState.EDIT;
        // Update adapter
        adapter.setState(state);
        adapter.notifyDataSetChanged();
        // Update toolbar
        updateToolBarHandler.updateToolBar();
    }

    private void editFieldSet(FieldSetScenario fieldSetScenario) {
        AndroidLayoutUtil.openModalAskText(getContext(), getString(R.string.msg_set_the_name),
                fieldSetScenario != null ? fieldSetScenario.getTitle() : null,
                (v) -> saveFieldSet(v, fieldSetScenario));
    }

    private boolean saveFieldSet(String title, FieldSetScenario fieldSetScenario) {
        boolean creation = fieldSetScenario == null;

        if (creation) {
            fieldSetScenario = new FieldSetScenario();
        }

        fieldSetScenario.setTitle(title);

        if (creation) {
            fieldSetScenario.setScenario(scenario);
            fieldSetScenario.setOrder(scenario.getNextFieldSetScenarioOrder());
            fieldSetScenarioRepositoryService.save(fieldSetScenario);
            adapter.addItem(fieldSetScenario);
            AndroidLayoutUtil.showToast(getContext(), getString(R.string.msg_fieldset_created));
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
            public boolean isLongPressDragEnabled() {
                return false;
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return false;
            }

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

    @Override
    public boolean showBack() {
        return true;
    }
}
