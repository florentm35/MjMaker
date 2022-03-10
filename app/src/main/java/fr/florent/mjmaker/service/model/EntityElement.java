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
@EqualsAndHashCode(of = "fieldSetElement")
@DatabaseTable(tableName = "entity_element")
public class EntityElement implements FieldSetElement.Element {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, foreign = true)
    private FieldSetElement fieldSetElement;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Entity entity;

    @Override
    public FieldSetElement.TypeElement getType() {
        return FieldSetElement.TypeElement.ENTITY;
    }
}
