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
@DatabaseTable(tableName = "template_var")
public class TemplateVar {

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(columnName = "label")
    private String label;
    @DatabaseField(columnName = "type")
    private String type;
    @DatabaseField(foreign = true)
    private Template template;
}
