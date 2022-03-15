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
@DatabaseTable(tableName = "template")
public class Template {

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Game game;
    @DatabaseField(columnName = "text")
    private String text;

    @ForeignCollectionField
    private ForeignCollection<TemplateVar> lstVar;
}
