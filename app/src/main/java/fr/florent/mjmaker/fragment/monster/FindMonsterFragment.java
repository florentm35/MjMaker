package fr.florent.mjmaker.fragment.monster;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import fr.florent.mjmaker.R;
import fr.florent.mjmaker.fragment.common.AbstractFragment;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;

public class FindMonsterFragment extends AbstractFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
        return inflater.inflate(R.layout.find_monster, container, false);
    }

    @Override
    public List<ToolBarItem> getToolbarItem() {
        return Arrays.asList(
                ToolBarItem.builder()
                        .label("Test")
                        .handler(() -> Log.i("", "Test"))
                        .build(),
                ToolBarItem.builder()
                        .label("Test2")
                        .handler(() -> Log.i("", "Test 2"))
                        .icone(R.drawable.material_add)
                        .build()
        );
    }
}
