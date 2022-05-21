package fr.florent.mjmaker.service.pojo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Point {
    int x;
    int y;

    public static Point of(int x, int y) {
        return new Point(x, y);
    }
}
