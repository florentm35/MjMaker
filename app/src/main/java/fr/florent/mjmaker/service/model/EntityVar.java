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
@DatabaseTable(tableName = "entity_var")
public class EntityVar {
    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(columnName = "value")
    private String value;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private TemplateVar templateVar;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Entity entity;
}
