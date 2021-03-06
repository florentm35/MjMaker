package fr.florent.mjmaker.service.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Iterator;
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

    @DatabaseField(columnName = "level")
    private Integer level;

    @DatabaseField(foreign = true, foreignAutoRefresh=true)
    private Game game;

    @ForeignCollectionField(orderColumnName = "order")
    private ForeignCollection<FieldSetScenario> lstFieldSet;

    public int getNextFieldSetScenarioOrder() {
        if(lstFieldSet == null) {
            return 0;
        } else {
            final Iterator<FieldSetScenario> iterator = lstFieldSet.iterator();
            int ordre = 0;
            while (iterator.hasNext()) {
                ordre = iterator.next().getOrder();
            }
            return ordre +1;
        }

    }

}
