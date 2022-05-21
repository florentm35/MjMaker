package fr.florent.mjmaker.service.pojo;

import java.util.Locale;

import fr.florent.mjmaker.utils.StringUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Cell {

    Point point;

    Color color;

    public static Cell of(int x, int y, int r, int g, int b) {
        return new Cell(Point.of(x, y), Color.of(r, g, b));
    }

    public static Cell of(int x, int y, Color color) {
        if (color == null) {
            throw new NullPointerException("Color can't not be null");
        }
        return new Cell(Point.of(x, y), color);
    }

    public static Cell of(Point point, Color color) {
        if (point == null) {
            throw new NullPointerException("Point can't not be null");
        }
        if (color == null) {
            throw new NullPointerException("Color can't not be null");
        }
        return new Cell(point, color);
    }

    /**
     * Pattern X,Y,RRRGGGBBB
     */
    public static Cell parse(String value) {

        if (StringUtils.isEmpty(value)) {
            return null;
        }

        String[] tmp = value.split(",");

        // We need 3 part en the last part need to be a triplet of 3 characters
        if (tmp.length != 3 || tmp[2].length() != 9) {
            return null;
        }

        int x = Integer.parseInt(tmp[0]);
        int y = Integer.parseInt(tmp[1]);

        int r = Integer.parseInt(tmp[2].substring(1, 3));
        int g = Integer.parseInt(tmp[2].substring(4, 6));
        int b = Integer.parseInt(tmp[2].substring(7, 9));

        return Cell.of(x, y, r, g, b);
    }

    @Override
    public String toString() {
        // point and color can't never be null
        return String.format(Locale.getDefault(), "%d,%d,%03d%03d%03d", point.getX(), point.getY(), color.getR(), color.getG(), color.getB());
    }

}
