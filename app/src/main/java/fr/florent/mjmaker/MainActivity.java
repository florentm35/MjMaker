package fr.florent.mjmaker;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.common.menu.EnumMenu;
import fr.florent.mjmaker.fragment.common.menu.MenuFragment;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.fragment.monster.EditMonsterFragment;
import fr.florent.mjmaker.fragment.monster.FindMonsterFragment;
import fr.florent.mjmaker.service.monstrer.MonsterService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Inject
    MonsterService monsterService;

    private static final int TEST = View.generateViewId();

    private Map<Integer, ToolBarItem> menuItem = Collections.EMPTY_MAP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(R.id.menu, new MenuFragment(this::onMenuSelect));
        loadToolBar();
        try {
            monsterService.getAll();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


    private void loadFragment(int id, Fragment fragment) {
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    /************************
     *          Menu        *
     ************************/

    private void onMenuSelect(EnumMenu menu) {
        Log.d(TAG, "Call menu : " + menu.name());

        AbstractFragment fragment = null;

        switch (menu) {
            case FIND_MONSTER:
                fragment = new FindMonsterFragment();
                break;
            case TEST :
                fragment= new EditMonsterFragment();
                break;
            default:
                Log.e(TAG, "Actions not found for " + menu.name());
                throw new RuntimeException("Not implemented");
        }

        // Add toolbar item from fragment
        menuItem = fragment.getToolbarItem()
                .stream()
                .collect(Collectors.toMap(t -> View.generateViewId(), Function.identity()));
        invalidateOptionsMenu();

        loadFragment(R.id.body, fragment);

        changeMenuVisibility();
    }

    private void changeMenuVisibility() {
        if (findViewById(R.id.menu).getVisibility() != View.VISIBLE) {
            findViewById(R.id.menu).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.menu).setVisibility(View.GONE);
        }
    }

    /************************
     *        Toolbar       *
     ************************/

    private void loadToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.material_menu);
            getSupportActionBar().setTitle("");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Map fragment item toolbar
        if (menuItem != null) {
            menuItem.forEach((k, v) -> {
                MenuItem item = menu.add(0, k, Menu.NONE, v.getLabel());
                if(v.getIcone() != null) {
                    item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    item.setIcon(v.getIcone());
                }
            });
        }

        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                changeMenuVisibility();
                break;
            default:
                // Execute toolbar item handler from fragment
                if (menuItem.containsKey(item.getItemId())) {
                    ToolBarItem.IToolBarItemEvent handler = menuItem.get(item.getItemId())
                            .getHandler();
                    if(handler != null) {
                        handler.action();
                    }
                } else {
                    Log.w(TAG, "Action not found for " + item.getItemId());
                    throw new RuntimeException("Not implemented");
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }


}