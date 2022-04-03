package fr.florent.mjmaker.service.markdown;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EnumMark {
    BOLD("**", false),
    ITALIC("*", false),
    STRICKETHROUGH("~~", false),
    LINK("[%0](%1%2)", true);

    @Getter
    private final String makdownTag;

    /**
     * If true then replace argument else add before end after the text the mark
     */
    @Getter
    private final boolean pattern;
}
