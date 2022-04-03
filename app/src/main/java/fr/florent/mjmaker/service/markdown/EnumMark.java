package fr.florent.mjmaker.service.markdown;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EnumMark {
    BOLD("**", false),
    ITALIC("*", false),
    STRIKETHROUGH("~~", false),
    /**
     * <ul>
     *      <li>%0 : Label</li>
     *      <li>%1 : Protocol</li>
     *      <li>%2 : URI</li>
     * </ul>
     */
    LINK("[%0](%1%2)", true);

    @Getter
    private final String makdownTag;

    /**
     * If true then replace argument else add before end after the text the mark
     */
    @Getter
    private final boolean pattern;
}
