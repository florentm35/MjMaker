package fr.florent.mjmaker.fragment.scenario;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import java.util.stream.Collectors;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.component.FilterComboBox;
import fr.florent.mjmaker.service.model.Game;
import fr.florent.mjmaker.service.model.Scenario;
import fr.florent.mjmaker.service.repository.GameService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;
import fr.florent.mjmaker.utils.ItemSelect;

public class ParamScenarioModal extends DialogFragment {

    private GameService gameService = GameService.getInstance();

    private Game gameSelection;

    private Scenario scenario;


    public interface IActionOK {
        boolean action(Scenario dialog);
    }


    private View getView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.param_scenario_modal, null);

        FilterComboBox<Game> filterComboBoxTextView = view.findViewById(R.id.fbc_game);
        filterComboBoxTextView.setText(scenario.getGame() != null ? scenario.getGame().getName() : "");
        filterComboBoxTextView.setItems(
                gameService.getAll().stream()
                        .map(g -> new ItemSelect<>(g, g.getName()))
                        .collect(Collectors.toList())
        );
        filterComboBoxTextView.setOnItemClickListener((g) -> gameSelection = g);

        AndroidLayoutUtil.setTextViewText(view, R.id.et_title, scenario.getTitle());
        AndroidLayoutUtil.setTextViewText(view, R.id.et_level, scenario.getLevel());

        return view;
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
