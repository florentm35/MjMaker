package fr.florent.mjmaker.fragment.common;

import androidx.fragment.app.Fragment;

import java.util.Collections;
import java.util.List;

import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;

/**
 * Abstract fragment app
 */
public abstract class AbstractFragment extends Fragment {

    /**
     * Get the screen toolbar item
     *
     * @return A list of toolbar item
     */
    public List<ToolBarItem> getToolbarItem() {
        return Collections.EMPTY_LIST;
    }

}
