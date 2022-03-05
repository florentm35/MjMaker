package fr.florent.mjmaker.fragment.common;

import androidx.fragment.app.Fragment;

import java.util.Collections;
import java.util.List;

import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;

public abstract class AbstractFragment extends Fragment {

    public List<ToolBarItem> getToolbarItem() {
        return Collections.EMPTY_LIST;
    }

}
