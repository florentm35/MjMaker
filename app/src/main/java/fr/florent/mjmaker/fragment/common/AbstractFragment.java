package fr.florent.mjmaker.fragment.common;

import androidx.fragment.app.Fragment;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

import fr.florent.mjmaker.fragment.common.menu.EnumScreen;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import lombok.Setter;

/**
 * Abstract app fragment
 */
public abstract class AbstractFragment extends Fragment {

    public interface IBackHandler {
        void back();
    }

    public interface IUpdateToolBarHandler {
        void updateToolBar();
    }

    /**
     * Return to the previous screen
     */
    @Setter
    protected IBackHandler backHandler;

    /**
     * Notify to update the toolbar
     */
    @Setter
    protected IUpdateToolBarHandler updateToolBarHandler;

    /**
     * Perform to redirect to the given screen
     */
    @Setter
    protected BiFunction<EnumScreen, Object[], Void> redirect;

    /**
     * Get the screen toolbar item
     *
     * @return A list of toolbar item
     */
    public List<ToolBarItem> getToolbarItem() {
        return Collections.EMPTY_LIST;
    }

    /**
     * If true replace the button menu, by back button in toolbar
     *
     * @return True show back action
     */
    public boolean showBack() {
        return false;
    }
}
