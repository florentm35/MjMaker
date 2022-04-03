package fr.florent.mjmaker.fragment.common.markdown;

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
import fr.florent.mjmaker.service.repository.EntityService;
import fr.florent.mjmaker.service.repository.GameService;
import fr.florent.mjmaker.service.repository.TemplateService;
import fr.florent.mjmaker.service.repository.ThemeService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;
import fr.florent.mjmaker.utils.DataBaseUtil;
import fr.florent.mjmaker.utils.ItemSelect;

public class ParamSearchEntityModal extends DialogFragment {

    private final GameService gameService = GameService.getInstance();
    private final ThemeService themeService = ThemeService.getInstance();
    private final TemplateService templateService = TemplateService.getInstance();
    private final EntityService entityService = EntityService.getInstance();

    private Game gameSelection;

    private Theme themeSelection;

    private Template templateSelection;

    private Entity entitySelection;

    private View view;

    public interface IActionOK {
        boolean action(Entity dialog);
    }

    public void show(Context context,
                     IActionOK onValidate) {

        view = initView(context);

        AndroidLayoutUtil.openSimpleDialog(view,
                (dialog -> onValidateDialog(dialog, onValidate)));
    }

    private View initView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.param_search_entity_modal, null);

        lodeGameList(view);

        loadThemeList(view);

        loadTemplateList(view);

        loadEntityList(view);

        return view;
    }

    private void lodeGameList(View view) {
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
        FilterComboBox<Template> fbcTemplate = view.findViewById(R.id.fbc_template);
        List<Template> lstTemplateAvailable = templateService.getAll().stream()
                .filter(t -> gameSelection == null || gameSelection.equals(t.getGame()))
                .filter(t -> themeSelection == null || themeSelection.equals(t.getTheme()))
                .collect(Collectors.toList());

        if(!lstTemplateAvailable.contains(templateSelection)) {
            templateSelection = null;
        }

        fbcTemplate.setItems(
                lstTemplateAvailable.stream()
                        .map(t -> new ItemSelect<>(t, t.getName()))
                        .collect(Collectors.toList())
        );

        fbcTemplate.setText(templateSelection != null ? templateSelection.getName() : "");

        fbcTemplate.setOnItemClickListener(this::onTemplateSelectionChanged);
    }

    private void loadEntityList(View view) {
        FilterComboBox<Entity> fbcEntity = view.findViewById(R.id.fbc_entity);
        List<Entity> lstEntityAvailable = entityService.getAll().stream()
                .filter(e -> templateSelection == null || templateSelection.equals(e.getTemplate()))
                .filter(e -> gameSelection == null || gameSelection.equals(e.getTemplate().getGame()))
                .filter(e -> themeSelection == null || themeSelection.equals(e.getTemplate().getTheme()))
                .collect(Collectors.toList());

        if(!lstEntityAvailable.contains(entitySelection)) {
            entitySelection = null;
        }

        fbcEntity.setItems(
                lstEntityAvailable.stream()
                        .map(t -> new ItemSelect<>(t, t.getName()))
                        .collect(Collectors.toList())
        );

        fbcEntity.setText(entitySelection != null ? entitySelection.getName() : "");

        fbcEntity.setOnItemClickListener(entity -> entitySelection = entity);
    }


    private void onGameSelectionChanged(Game g) {
        gameSelection = g;
        loadThemeList(view);
        loadTemplateList(view);
        loadEntityList(view);
    }

    private void onThemeSelectionChanged(Theme theme) {
        themeSelection = theme;
        loadTemplateList(view);
        loadEntityList(view);
    }

    private void onTemplateSelectionChanged(Template template) {
        templateSelection = template;
        loadEntityList(view);
    }


    private void onValidateDialog(Dialog dialog, IActionOK onValidate) {
        if (onValidate.action(entitySelection)) {
            dialog.cancel();
        }
    }
}
