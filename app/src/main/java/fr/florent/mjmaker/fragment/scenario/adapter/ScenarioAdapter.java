package fr.florent.mjmaker.fragment.scenario.adapter;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;

import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.service.model.Scenario;
import fr.florent.mjmaker.utils.AbstractLinearWithHeaderAdapter;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class ScenarioAdapter extends AbstractLinearWithHeaderAdapter<Scenario> {

    private static String TAG = ScenarioAdapter.class.getName();

    public enum EnumAction {
        EDIT, DELETE;
    }

    public interface IEventAction {
        void action(ScenarioAdapter.EnumAction action, Scenario scenario);
    }

    private final ScenarioAdapter.IEventAction handler;

    public ScenarioAdapter(Context context, List<Scenario> scenarios, ScenarioAdapter.IEventAction handler) {
        super(context, scenarios);
        this.handler = handler;
    }

    @Override
    public int getLayout(int viewType) {
        if (ViewHolder.HEADER == viewType) {
            return R.layout.list_scenario_header;
        } else {
            return R.layout.list_scenario_row;
        }
    }

    @Override
    public void onBindViewHolder(View view, int viewType, int position) {
        if (viewType == ViewHolder.ROW) {

            if (position % 2 == 1) {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_purple));
            } else {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.light_purple));
            }

            Scenario scenario = values.get(position - 1);

            AndroidLayoutUtil.setTextViewText(view, R.id.tv_game, scenario.getGame() != null ? scenario.getGame().getName() : "");
            AndroidLayoutUtil.setTextViewText(view, R.id.tv_title, scenario.getTitle());
            AndroidLayoutUtil.setTextViewText(view, R.id.tv_level, scenario.getLevel());


            view.findViewById(R.id.edit).setOnClickListener(v -> handler.action(ScenarioAdapter.EnumAction.EDIT, scenario));
            view.findViewById(R.id.delete).setOnClickListener(v -> handler.action(ScenarioAdapter.EnumAction.DELETE, scenario));
        }
    }

}
