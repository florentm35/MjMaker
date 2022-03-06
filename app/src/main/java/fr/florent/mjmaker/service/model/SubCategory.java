package fr.florent.mjmaker.service.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DatabaseTable(tableName = "subcategory")
public class SubCategory {

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "idCategory")
    private Integer idCategory;
}