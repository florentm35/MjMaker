package fr.florent.mjmaker.fragment.common;

import androidx.fragment.app.Fragment;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

import fr.florent.mjmaker.fragment.common.menu.EnumScreen;
import fr.florent.mjmaker.fragment.common.toolbar.ToolBarItem;
import lombok.Setter;

/**
 * Abstract fragment app
 */
public abstract class AbstractFragment extends Fragment {

    public interface IBackHandler {
        void back();
    }

    public interface IUpdateToolBarHandler {
        void updateToolBar();
    }

    @Setter
    protected IBackHandler backHandler;

    @Setter
    protected IUpdateToolBarHandler updateToolBarHandler;
    
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

}
