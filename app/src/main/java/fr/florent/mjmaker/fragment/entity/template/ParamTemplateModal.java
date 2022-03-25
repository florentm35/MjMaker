package fr.florent.mjmaker.fragment.entity.template;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import java.util.stream.Collectors;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.component.FilterComboBox;
import fr.florent.mjmaker.service.model.Game;
import fr.florent.mjmaker.service.model.Template;
import fr.florent.mjmaker.service.repository.GameRepositoryService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;
import fr.florent.mjmaker.utils.ItemSelect;

public class ParamTemplateModal extends DialogFragment {

    private GameRepositoryService gameRepositoryService = GameRepositoryService.getInstance();

    private Game gameSelection;

    private Template template;


    public interface IActionOK {
        boolean action(Template dialog);
    }


    private View getView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.param_template_modal, null);


        FilterComboBox<Game> filterComboBoxTextView = view.findViewById(R.id.ac_game);
        filterComboBoxTextView.setText(template.getGame() != null ? template.getGame().getName() : "");
        filterComboBoxTextView.setItems(
                gameRepositoryService.getAll().stream()
                        .map(g -> new ItemSelect<>(g, g.getName()))
                        .collect(Collectors.toList())
        );
        filterComboBoxTextView.setOnItemClickListener((g) -> gameSelection = g);

        AndroidLayoutUtil.setTextViewText(view, R.id.et_name, template.getName());

        return view;
    }

    public void show(Context context,
                     Template value,
                     IActionOK onValidate) {
        template = value;
        if (template == null) {
            template = new Template();
        }
        gameSelection = template.getGame();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view = getView(context);
        builder.setView(view)
                .setPositiveButton("OK", (dialog, id) -> {
                    template.setName(AndroidLayoutUtil.getTextViewText(view, R.id.et_name));
                    template.setGame(gameSelection);
                    if (onValidate.action(template)) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());

        builder.create()
                .show();
    }
}
