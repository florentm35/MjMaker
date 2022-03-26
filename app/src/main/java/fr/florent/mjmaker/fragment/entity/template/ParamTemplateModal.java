package fr.florent.mjmaker.fragment.entity.template;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import java.util.List;
import java.util.stream.Collectors;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.component.FilterComboBox;
import fr.florent.mjmaker.service.model.Game;
import fr.florent.mjmaker.service.model.Template;
import fr.florent.mjmaker.service.model.Theme;
import fr.florent.mjmaker.service.repository.GameRepositoryService;
import fr.florent.mjmaker.service.repository.ThemeRepositoryService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;
import fr.florent.mjmaker.utils.DataBaseUtil;
import fr.florent.mjmaker.utils.ItemSelect;

public class ParamTemplateModal extends DialogFragment {

    private final GameRepositoryService gameRepositoryService = GameRepositoryService.getInstance();
    private final ThemeRepositoryService themeRepositoryService = ThemeRepositoryService.getInstance();

    private Game gameSelection;

    private Theme themeSelection;

    private Template template;

    private View view;

    public interface IActionOK {
        boolean action(Template dialog);
    }


    private View initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.param_template_modal, null);

        // Init game combo box
        FilterComboBox<Game> fbcGame = view.findViewById(R.id.fbc_game);
        fbcGame.setText(template.getGame() != null ? template.getGame().getName() : "");
        fbcGame.setItems(
                gameRepositoryService.getAll().stream()
                        .map(g -> new ItemSelect<>(g, g.getName()))
                        .collect(Collectors.toList())
        );
        fbcGame.setOnItemClickListener(this::onGameSelectionChanged);

        // Init theme combo box
        FilterComboBox<Theme> fbcTheme = view.findViewById(R.id.fbc_theme);
        fbcTheme.setText(template.getTheme() != null ? template.getTheme().getName() : "");
        fbcTheme.setItems(
                themeRepositoryService.getAll().stream()
                        .map(t -> new ItemSelect<>(t, t.getName()))
                        .collect(Collectors.toList())
        );
        fbcTheme.setOnItemClickListener(t -> themeSelection = t);


        AndroidLayoutUtil.setTextViewText(view, R.id.et_name, template.getName());

        return view;
    }

    private void onGameSelectionChanged(Game g) {
        gameSelection = g;

        FilterComboBox<Theme> fbcTheme = view.findViewById(R.id.fbc_theme);
        if(gameSelection != null) {
            List<Theme> lstThemeAvailable = DataBaseUtil.convertForeignCollectionToList(gameSelection.getLstTheme());

            fbcTheme.setItems(
                    lstThemeAvailable.stream()
                            .map(t -> new ItemSelect<>(t, t.getName()))
                            .collect(Collectors.toList())
            );

            if(!lstThemeAvailable.contains(themeSelection)) {
                fbcTheme.setText("");
            }

        } else {
            fbcTheme.setItems(
                    themeRepositoryService.getAll().stream()
                            .map(t -> new ItemSelect<>(t, t.getName()))
                            .collect(Collectors.toList())
            );
        }


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
        view = initView(context);
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
