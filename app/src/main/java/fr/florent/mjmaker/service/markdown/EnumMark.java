package fr.florent.mjmaker.service.markdown;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EnumMark {
    BOLD("**"), ITALIC("*"), STRICKETHROUGH("~~");

    @Getter
    private final String makdownTag;
}
