package fr.florent.mjmaker.service.pojo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Color {

    int r;
    int g;
    int b;

    public static Color of(int r, int g, int b) {
        return new Color(r, g, b);
    }

}
