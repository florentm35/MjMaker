package fr.florent.mjmaker.fragment.common.toolbar;

import java.util.function.BiFunction;

import fr.florent.mjmaker.fragment.common.menu.EnumScreen;
import lombok.Builder;
import lombok.Data;

/**
 * Toolbar item pojo
 */
@Data
@Builder
public class ToolBarItem {

    /**
     * Action handler from toolbar item with redirect function
     */
    public interface IToolBarItemEventRedirect {
        /**
         * Action call when clieck on item toolbar
         *
         */
        void action();
    }

    /**
     * The label toolbar
     */
    String label;

    /**
     * The drawable ressource id
     */
    Integer icone;

    /**
     * The event handler
     */
    IToolBarItemEventRedirect handler;

}
