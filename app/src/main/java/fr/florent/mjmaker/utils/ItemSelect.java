package fr.florent.mjmaker.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ItemSelect<T> {
    private final T value;
    private final String label;
}
