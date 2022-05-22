package fr.florent.mjmaker.service.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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
@DatabaseTable(tableName = "map_cell")
public class GameMapCell {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(columnName = "x")
    private Integer x;
    @DatabaseField(columnName = "y")
    private Integer y;

    @DatabaseField(columnName = "r")
    private Integer r;
    @DatabaseField(columnName = "g")
    private Integer g;
    @DatabaseField(columnName = "b")
    private Integer b;

    @DatabaseField(canBeNull = false, foreign = true)
    private GameMap gameMap;

}
