package fr.florent.mjmaker.service.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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
@DatabaseTable(tableName = "fieldSet_element")
public class FieldSetElement {

    public interface Element {

        TypeElement getType();
    }

    public enum TypeElement {
        TEXT, MAP, ENTITY, BATTLE;
    }

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, foreign = true)
    private FieldSetScenario fieldSetScenario;

    @DatabaseField(canBeNull = false, columnName = "order")
    private Integer order;

    @DatabaseField(canBeNull = false, columnName = "dtype")
    private TypeElement dType;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private TextElement textElement;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private EntityElement entityElement;

    public Element getElement() {
        switch (dType) {
            case TEXT:
                return getTextElement();
            case ENTITY:
                return getEntityElement();
            default:
                throw new RuntimeException("Not implemented");
        }
    }

    public void setElement(Element element) {
        switch (dType) {
            case TEXT:
                setTextElement((TextElement) element);
                break;
            case ENTITY:
                setEntityElement((EntityElement) element);
                break;
            default:
                throw new RuntimeException("Not implemented");
        }
    }
}
