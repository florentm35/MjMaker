package fr.florent.mjmaker;

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

import java.sql.SQLException;
import java.util.HashMap;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import fr.florent.mjmaker.fragment.common.menu.EnumMenu;
import fr.florent.mjmaker.fragment.common.menu.MenuFragment;
import fr.florent.mjmaker.fragment.monster.FindMonsterFragment;
import fr.florent.mjmaker.service.monstrer.MonsterService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Inject
    MonsterService monsterService;

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

        switch (menu) {
            case FIND_MONSTER:
                loadFragment(R.id.body, new FindMonsterFragment());
                break;
            default:
                Log.e(TAG, "Actions not found for " + menu.name());
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
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (findViewById(R.id.menu).getVisibility() != View.VISIBLE) {
                    findViewById(R.id.menu).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.menu).setVisibility(View.GONE);
                }
                break;
            default:
                Log.w(TAG, "Action not found for "+item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }
}