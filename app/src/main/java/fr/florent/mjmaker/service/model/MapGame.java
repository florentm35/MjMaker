package fr.florent.mjmaker.service.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.florent.mjmaker.service.pojo.Cell;
import fr.florent.mjmaker.service.pojo.Point;
import fr.florent.mjmaker.utils.StringUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@DatabaseTable(tableName = "map")
public class MapGame {


    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "detail")
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private String detail;

    @DatabaseField(columnName = "width")
    private Integer width;

    @DatabaseField(columnName = "height")
    private Integer height;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Game game;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Theme theme;

    @ForeignCollectionField(orderColumnName = "name")
    private ForeignCollection<MapGameLegend> lstLegend;

    public java.util.Map<Point, Cell> getCell() {

        if (StringUtils.isNotEmpty(detail)) {
            String[] tmp = detail.split(";");
            return Arrays.stream(tmp).map(Cell::parse)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(Cell::getPoint, Function.identity()));
        }
        // We return real HashMap and not an empty Map for the caller can modify it
        return new HashMap<>();
    }

    public void updateCell(java.util.Map<Point, Cell> cells) {
        if (cells == null) {
            throw new NullPointerException("Cells can not be null");
        }
        updateCell(cells.values());
    }

    public void updateCell(Collection<Cell> cells) {
        if (cells == null) {
            throw new NullPointerException("Cells can not be null");
        }
        detail = cells.stream().map(Cell::toString).collect(Collectors.joining(";"));
    }
}
