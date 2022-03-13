package fr.florent.mjmaker.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Map;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.category.ListGameFragment;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.common.menu.EnumScreen;
import fr.florent.mjmaker.fragment.common.menu.MenuFragment;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import fr.florent.mjmaker.fragment.monster.EditMonsterFragment;
import fr.florent.mjmaker.fragment.monster.ListMonsterFragment;
import fr.florent.mjmaker.fragment.scenario.ListScenarioFragment;
import fr.florent.mjmaker.fragment.scenario.ScenarioFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private Stack<AbstractFragment> callStack = new Stack<>();

    private Map<Integer, ToolBarItem> menuItem;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Load the side menu
        loadFragment(R.id.menu, new MenuFragment(m -> switchScreen(m, true)));
        // Load the default toolbar
        loadToolBar();
    }

    /**
     * Change to the other screen
     *
     * @param screen   The screen
     * @param fromMenu If action is from the side menu
     * @param param    Optional parameters
     */
    private void switchScreen(EnumScreen screen, boolean fromMenu, Object... param) {
        Log.d(TAG, "Call menu : " + screen.name());

        // init the Fragment
        AbstractFragment fragment = null;
        switch (screen) {
            case LIST_MONSTER:
                fragment = new ListMonsterFragment();
                break;
            case EDIT_MONSTER:
                fragment = new EditMonsterFragment(param);
                break;
            case LIST_CATEGORY:
                fragment = new ListGameFragment();
                break;
            case LIST_SCENARIO:
                fragment = new ListScenarioFragment();
                break;
            case DETAIL_SCENARIO:
                fragment = new ScenarioFragment(param);
                break;
            default:
                Log.e(TAG, "Actions not found for " + screen.name());
                throw new RuntimeException("Not implemented");
        }

        if (fromMenu) {
            // Hide side menu
            changeMenuVisibility();
            // Clear stack
            callStack.clear();
        }

        // load the fragment
        loadBodyFragment(fragment);

    }

    /**
     * Lod a fragment on body FrameLayout
     *
     * @param fragment The fragment to load
     */
    private void loadBodyFragment(AbstractFragment fragment) {
        // Add back handler action
        fragment.setBackHandler(this::back);
        fragment.setRedirect(this::redirect);
        fragment.setUpdateToolBarHandler(() -> this.updateToolBar(fragment));
        // Add toolbar item from fragment
        updateToolBar(fragment);

        // Add fragment to the stack
        callStack.push(fragment);

        // Load the fragment
        loadFragment(R.id.body, fragment);
    }

    private void updateToolBar(AbstractFragment fragment) {
        // Add toolbar item from fragment
        menuItem = fragment.getToolbarItem()
                .stream()
                .collect(Collectors.toMap(t -> View.generateViewId(), Function.identity()));
        // Update the toolbar item
        invalidateOptionsMenu();
    }

    /**
     * Call the previous fragment
     */
    private void back() {
        // Remove the current fragment
        callStack.pop();
        // Get the previous fragment
        AbstractFragment fragment = callStack.pop();
        loadBodyFragment(fragment);
    }

    /**
     * utils method to load fragment to the view by id
     *
     * @param id       The view id
     * @param fragment The fragment to load
     */
    private void loadFragment(int id, Fragment fragment) {
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    /************************
     *          Menu        *
     ************************/

    /**
     * If the side menu is visible so hide it, in other hand if he is hide set it visible
     */
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

    /**
     * Init the toolbar
     */
    private void loadToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.material_menu);
            getSupportActionBar().setTitle("");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Map fragment item toolbar
        if (menuItem != null) {
            menuItem.forEach((k, v) -> {
                MenuItem item = menu.add(0, k, Menu.NONE, v.getLabel());
                if (v.getIcone() != null) {
                    item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    item.setIcon(v.getIcone());
                }
            });
        }

        getMenuInflater().inflate(R.menu.empty_menu, menu);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                changeMenuVisibility();
                break;
            default:
                // Execute toolbar item handler from fragment
                if (menuItem.containsKey(item.getItemId())) {
                    ToolBarItem.IToolBarItemEventRedirect handler = menuItem.get(item.getItemId())
                            .getHandler();
                    if (handler != null) {
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

    /**
     * Redirect to the new screen
     *
     * @param screen The screen
     * @param params Optional screen parameters
     */
    private Void redirect(EnumScreen screen, Object[] params) {
        switchScreen(screen, false, params);
        return null;
    }

}