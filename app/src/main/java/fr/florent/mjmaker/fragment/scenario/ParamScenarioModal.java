package fr.florent.mjmaker.fragment.scenario;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import androidx.fragment.app.DialogFragment;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.component.InstantAutoComplete;
import fr.florent.mjmaker.fragment.scenario.adapter.GameArrayAdapter;
import fr.florent.mjmaker.service.model.Game;
import fr.florent.mjmaker.service.model.Scenario;
import fr.florent.mjmaker.service.repository.GameRepositoryService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;

public class ParamScenarioModal extends DialogFragment {

    private GameRepositoryService gameRepositoryService = GameRepositoryService.getInstance();

    private Game gameSelection;

    private Scenario scenario;


    public interface IActionOK {
        boolean action(Scenario dialog);
    }


    private View getView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.param_scenario_modal, null);

        GameArrayAdapter gameArrayAdapter = new GameArrayAdapter(context, R.layout.game_string_row, gameRepositoryService.getAll());

        InstantAutoComplete autoCompleteTextView = view.findViewById(R.id.ac_game);
        autoCompleteTextView.setText(scenario.getGame() != null ? scenario.getGame().getName() : "");
        autoCompleteTextView.setAdapter(gameArrayAdapter);
        autoCompleteTextView.setOnItemClickListener((adapterView, view1, i, l) -> onGameSelect(adapterView, i));

        AndroidLayoutUtil.setTextViewText(view, R.id.et_title, scenario.getTitle());
        AndroidLayoutUtil.setTextViewText(view, R.id.et_level, scenario.getLevel());

        return view;
    }

    private void onGameSelect(AdapterView<?> adapterView, int position) {
        gameSelection = (Game) adapterView.getItemAtPosition(position);
    }

    public void show(Context context,
                     Scenario value,
                     IActionOK onValidate) {
        scenario = value;
        if (scenario == null) {
            scenario = new Scenario();
        }
        gameSelection = scenario.getGame();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view = getView(context);
        builder.setView(view)
                .setPositiveButton("OK", (dialog, id) -> {
                    scenario.setTitle(AndroidLayoutUtil.getTextViewText(view, R.id.et_title));
                    scenario.setLevel(AndroidLayoutUtil.getTextViewInteger(view, R.id.et_level));
                    scenario.setGame(gameSelection);
                    if (onValidate.action(scenario)) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());

        builder.create()
                .show();
    }
}
