package fr.florent.mjmaker.fragment.common.toolbar;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ToolBarItem {

    public interface IToolBarItemEvent {
        public void action();
    }

    String label;

    Integer icone;

    IToolBarItemEvent handler;

}
