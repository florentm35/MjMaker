package fr.florent.mjmaker.fragment.map.modal;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import java.util.List;
import java.util.stream.Collectors;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.component.FilterComboBox;
import fr.florent.mjmaker.service.model.Game;
import fr.florent.mjmaker.service.model.Map;
import fr.florent.mjmaker.service.model.Template;
import fr.florent.mjmaker.service.model.Theme;
import fr.florent.mjmaker.service.repository.GameService;
import fr.florent.mjmaker.service.repository.ThemeService;
import fr.florent.mjmaker.utils.AndroidLayoutUtil;
import fr.florent.mjmaker.utils.DataBaseUtil;
import fr.florent.mjmaker.utils.ItemSelect;

public class ParamMapModal extends DialogFragment {

    private final GameService gameService = GameService.getInstance();
    private final ThemeService themeService = ThemeService.getInstance();

    private Game gameSelection;

    private Theme themeSelection;

    private Map map;

    private View view;

    public interface IActionOK {
        boolean action(Map dialog);
    }


    private View initView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.param_map_modal, null);

        lodeGameList(view);

        loadThemeList(view);

        AndroidLayoutUtil.setTextViewText(view, R.id.et_name, map.getName());

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

        fbcTheme.setOnItemClickListener(theme -> themeSelection = theme);
    }

    private void onGameSelectionChanged(Game g) {
        gameSelection = g;
        loadThemeList(view);
    }

    public void show(Context context,
                     Map value,
                     IActionOK onValidate) {
        map = value;
        if (map == null) {
            map = new Map();
        }
        gameSelection = map.getGame();
        themeSelection = map.getTheme();

        initView(context);

        AndroidLayoutUtil.openSimpleDialog(view,
                (dialog -> onValidateDialog(dialog, onValidate)));

    }

    private void onValidateDialog(Dialog dialog, IActionOK onValidate) {
        map.setName(AndroidLayoutUtil.getTextViewText(view, R.id.et_name));
        map.setGame(gameSelection);
        map.setTheme(themeSelection);
        if (onValidate.action(map)) {
            dialog.cancel();
        }
    }
}
