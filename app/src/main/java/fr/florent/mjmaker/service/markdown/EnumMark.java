package fr.florent.mjmaker.service.markdown;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EnumMark {
    BOLD("**", false, true),
    ITALIC("*", false, true),
    STRIKETHROUGH("~~", false, true),
    /**
     * <ul>
     *      <li>%0 : Label</li>
     *      <li>%1 : Protocol</li>
     *      <li>%2 : URI</li>
     * </ul>
     */
    LINK("[%0](%1%2)", true, false),
    LINE_BREAK("$$$", false, false);


    @Getter
    private final String makdownTag;

    /**
     * If true then replace argument else add before end after the text the mark
     */
    @Getter
    private final boolean pattern;

    /**
     * If true the selected the mark was write before and after the selection, else write in remplacement
     */
    @Getter
    private final boolean group;
}
