package fr.florent.mjmaker.service.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
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
@DatabaseTable(tableName = "fieldset_scenario")
public class FieldSetScenario {

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, foreign = true)
    private Scenario scenario;

    @DatabaseField(columnName = "title")
    private String title;

    @ForeignCollectionField
    private ForeignCollection<TextScenario> lstText;

}
