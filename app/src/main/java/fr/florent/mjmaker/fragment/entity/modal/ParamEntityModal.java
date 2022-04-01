package fr.florent.mjmaker.fragment.entity.modal;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import java.util.List;
import java.util.stream.Collectors;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.component.FilterComboBox;
import fr.florent.mjmaker.service.model.Entity;
import fr.florent.mjmaker.service.model.Game;
import fr.florent.mjmaker.service.model.Template;
import fr.florent.mjmaker.service.model.Theme;
import fr.florent.mjmaker.service.repository.GameService;
import fr.florent.mjmaker.service.repository.TemplateService;
import fr.florent.mjmaker.service.repository.ThemeService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;
import fr.florent.mjmaker.utils.DataBaseUtil;
import fr.florent.mjmaker.utils.ItemSelect;

public class ParamEntityModal extends DialogFragment {

    private final GameService gameService = GameService.getInstance();
    private final ThemeService themeService = ThemeService.getInstance();
    private final TemplateService templateService = TemplateService.getInstance();

    private Game gameSelection;

    private Theme themeSelection;

    private Template templateSelection;

    private Entity entity;

    private View view;

    public interface IActionOK {
        boolean action(Entity dialog);
    }

    public void show(Context context,
                     Entity value,
                     IActionOK onValidate) {
        entity = value;
        if (entity == null) {
            entity = new Entity();
        }
        templateSelection = entity.getTemplate();
        if (templateSelection != null) {
            gameSelection = templateSelection.getGame();
            themeSelection = templateSelection.getTheme();
        }

        view = initView(context);

        AndroidLayoutUtil.openSimpleDialog(view,
                (dialog -> onValidateDialog(dialog, onValidate)));
    }

    private View initView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.param_entity_modal, null);

        lodeGameList(view);

        loadThemeList(view);

        loadTemplateList(view);

        AndroidLayoutUtil.setTextViewText(view, R.id.et_name, entity.getName());
        AndroidLayoutUtil.setTextViewText(view, R.id.et_level, entity.getLevel());

        return view;
    }

    private void lodeGameList(View view) {
        // Init game combo box
        FilterComboBox<Game> fbcGame = view.findViewById(R.id.fbc_game);
        fbcGame.setText(gameSelection != null ? gameSelection.getName() : "");
        fbcGame.setItems(
                gameService.getAll().stream()
                        .map(g -> new ItemSelect<>(g, g.getName()))
                        .collect(Collectors.toList())
        );
        fbcGame.setOnItemClickListener(this::onGameSelectionChanged);
    }

    private void loadThemeList(View view) {
        // Init theme combo box
        FilterComboBox<Theme> fbcTheme = view.findViewById(R.id.fbc_theme);
        List<Theme> lstThemeAvailable;
        if (gameSelection != null) {
            lstThemeAvailable = DataBaseUtil.convertForeignCollectionToList(gameSelection.getLstTheme());
            if (!lstThemeAvailable.contains(themeSelection)) {
                themeSelection = null;
            }
        } else {
            lstThemeAvailable = themeService.getAll();
        }

        fbcTheme.setItems(
                lstThemeAvailable.stream()
                        .map(t -> new ItemSelect<>(t, t.getName()))
                        .collect(Collectors.toList())
        );

        fbcTheme.setText(themeSelection != null ? themeSelection.getName() : "");

        fbcTheme.setOnItemClickListener(this::onThemeSelectionChanged);
    }

    private void loadTemplateList(View view) {
        // Init theme combo box
        FilterComboBox<Template> fbcTemplate = view.findViewById(R.id.fbc_template);
        List<Template> lstTemplateAvailable = templateService.getAll().stream()
                .filter(t -> gameSelection == null || gameSelection.equals(t.getGame()))
                .filter(t -> themeSelection == null || themeSelection.equals(t.getTheme()))
                .collect(Collectors.toList());


        fbcTemplate.setItems(
                lstTemplateAvailable.stream()
                        .map(t -> new ItemSelect<>(t, t.getName()))
                        .collect(Collectors.toList())
        );

        fbcTemplate.setText(templateSelection != null ? templateSelection.getName() : "");

        fbcTemplate.setOnItemClickListener(template -> templateSelection = template);
    }

    private void onGameSelectionChanged(Game g) {
        gameSelection = g;
        loadThemeList(view);
        loadTemplateList(view);
    }

    private void onThemeSelectionChanged(Theme theme) {
        themeSelection = theme;
        loadTemplateList(view);
    }


    private void onValidateDialog(Dialog dialog, IActionOK onValidate) {
        entity.setName(AndroidLayoutUtil.getTextViewText(view, R.id.et_name));
        entity.setLevel(AndroidLayoutUtil.getTextViewText(view, R.id.et_level));
        entity.setTemplate(templateSelection);

        if (onValidate.action(entity)) {
            dialog.cancel();
        }
    }
}
