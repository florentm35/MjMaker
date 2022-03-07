package fr.florent.mjmaker.service.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

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
@DatabaseTable(tableName = "scenario")
public class Scenario {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(columnName = "title")
    private String title;

    @DatabaseField(columnName = "idGame")
    private Integer idGame;

    private transient List<FieldSetScenario> lstFieldSet;

}
