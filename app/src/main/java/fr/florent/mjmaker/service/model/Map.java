package fr.florent.mjmaker.service.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
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
@DatabaseTable(tableName = "map")
public class Map {

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "detail")
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
    private ForeignCollection<MapLegend> lstLegend;

}
