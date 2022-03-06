package fr.florent.mjmaker.fragment.common.toolbar;

import java.util.function.BiFunction;
import java.util.function.Function;

import fr.florent.mjmaker.fragment.common.menu.EnumMenu;
import lombok.Builder;
import lombok.Data;

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
         * @param functionRedirect The redirect fonction return Void
         *                         EnumMenu for location
         *                         Object[] for param location
         */
        void action(BiFunction< EnumMenu, Object[],Void> functionRedirect);
    }

    String label;

    Integer icone;

    IToolBarItemEventRedirect handler;

}
