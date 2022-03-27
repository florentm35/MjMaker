package fr.florent.mjmaker.fragment.scenario.adapter;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.scenario.ScenarioFragment;
import fr.florent.mjmaker.service.model.EntityElement;
import fr.florent.mjmaker.service.model.FieldSetElement;
import fr.florent.mjmaker.service.model.FieldSetScenario;
import fr.florent.mjmaker.service.model.TextElement;
import fr.florent.mjmaker.utils.AbstractLinearAdapter;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;
import fr.florent.mjmaker.utils.DataBaseUtil;
import lombok.Setter;

public class FieldSetAdapter extends AbstractLinearAdapter<FieldSetScenario> {

    private static final int ID_MENU_TEXT = View.generateViewId();
    private static final int ID_MENU_ENTITY = View.generateViewId();

    public enum EnumAction {
        UPDATE, EDIT, DELETE,
        ADD_ELEMENT, UPDATE_ELEMENT, DELETE_ELEMENT;
    }

    public interface IStartDragListener {
        void requestDrag(RecyclerView.ViewHolder viewHolder);
    }

    public interface IEventAction {
        void action(EnumAction action, FieldSetScenario fieldSetScenario, FieldSetElement element);
    }

    private final IEventAction handler;

    private final IStartDragListener dragEvent;

    @Setter
    private ScenarioFragment.EnumState state;

    public FieldSetAdapter(Context context,
                           List<FieldSetScenario> values,
                           ScenarioFragment.EnumState state,
                           IEventAction handler,
                           IStartDragListener dragEvent) {
        super(context, values);
        this.handler = handler;
        this.state = state;
        this.dragEvent = dragEvent;
    }

    @Override
    public int getLayout(int viewType) {
        return R.layout.fieldset_row;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        View view = holder.itemView;

        FieldSetScenario item = values.get(position);

        AndroidLayoutUtil.setTextViewText(view, R.id.tv_title, item.getTitle());

        ConstraintLayout headerLayout = view.findViewById(R.id.header);

        headerLayout.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                dragEvent.requestDrag(holder);
            }
            return false;
        });

        RecyclerView recyclerView = view.findViewById(R.id.list_element);
        FieldSetElementAdapter adapter;
        if (recyclerView.getAdapter() == null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
             adapter = new FieldSetElementAdapter(context,
                    DataBaseUtil.convertForeignCollectionToList(item.getLstElement()),
                    state,
                    (a, e) -> this.updateElement(a, item, e));
            recyclerView.setAdapter(adapter);
        } else {
            adapter = (FieldSetElementAdapter) recyclerView.getAdapter();
            adapter.setItems(DataBaseUtil.convertForeignCollectionToList(item.getLstElement()));
            adapter.setState(state);
            adapter.notifyDataSetChanged();
        }

        if (state == ScenarioFragment.EnumState.EDIT) {
            view.findViewById(R.id.add).setOnClickListener((v) -> openAddMenu(v, item));
            view.findViewById(R.id.edit).setOnClickListener(v -> handler.action(EnumAction.EDIT, item, null));
            view.findViewById(R.id.delete).setOnClickListener(v -> handler.action(EnumAction.DELETE, item, null));

            view.findViewById(R.id.add).setVisibility(View.VISIBLE);
            view.findViewById(R.id.edit).setVisibility(View.VISIBLE);
            view.findViewById(R.id.delete).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.add).setVisibility(View.GONE);
            view.findViewById(R.id.edit).setVisibility(View.GONE);
            view.findViewById(R.id.delete).setVisibility(View.GONE);
        }
        view.findViewById(R.id.more).setOnClickListener(v -> switchVisibilityListElement(view));

    }

    private void updateElement(FieldSetElementAdapter.EnumAction actionElement,FieldSetScenario fieldSetScenario,  FieldSetElement element) {
        EnumAction action;
        switch (actionElement) {
            case UPDATE:
                action = EnumAction.UPDATE_ELEMENT;
                break;
            case DELETE:
                action = EnumAction.DELETE_ELEMENT;
                break;
            default:
                throw new RuntimeException("Not implemented");
        }
        handler.action(action, fieldSetScenario, element);
    }

    private void openAddMenu(View view, FieldSetScenario fieldSetScenario) {
        PopupMenu popup = new PopupMenu(context, view);
        popup.setOnMenuItemClickListener((menuItem -> onActionAddMenu(menuItem, fieldSetScenario)));

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.empty_menu, popup.getMenu());

        popup.getMenu().add(0, ID_MENU_TEXT, Menu.NONE, "Text");
        popup.getMenu().add(0, ID_MENU_ENTITY, Menu.NONE, "PNJ/Monster");

        popup.show();
    }

    private boolean onActionAddMenu(MenuItem item, FieldSetScenario fieldSetScenario) {
        FieldSetElement fieldSetElement = new FieldSetElement();
        fieldSetElement.setFieldSetScenario(fieldSetScenario);
        fieldSetElement.setOrder(fieldSetScenario.getNextFieldSetElementOrder());
        FieldSetElement.Element element;
        if (item.getItemId() == ID_MENU_ENTITY) {
            fieldSetElement.setDType(FieldSetElement.TypeElement.ENTITY);
            element = new EntityElement();
        } else if (item.getItemId() == ID_MENU_TEXT) {
            fieldSetElement.setDType(FieldSetElement.TypeElement.TEXT);
            element = new TextElement();
        } else {
            throw new RuntimeException("Not Implement");
        }
        fieldSetElement.setElement(element);

        handler.action(EnumAction.ADD_ELEMENT, fieldSetScenario, fieldSetElement);
        return true;
    }

    private void switchVisibilityListElement(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.list_element);
        ImageButton btn = view.findViewById(R.id.more);

        if (recyclerView.getVisibility() == View.VISIBLE) {
            recyclerView.setVisibility(View.GONE);
            btn.setImageResource(R.drawable.material_more);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            btn.setImageResource(R.drawable.material_less);
        }
    }

}
