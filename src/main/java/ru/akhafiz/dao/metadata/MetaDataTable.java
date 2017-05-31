package ru.akhafiz.dao.metadata;

import java.util.Set;

/**
 * <p></p>
 *
 * @author akhafiz
 */
public class MetaDataTable {

    private String tableName;

    private String alias;

    private String namePK;

    private Set<String> columns;

    public MetaDataTable(String tableName, String alias, String namePK, Set<String> columns) {
        this.tableName = tableName;
        this.alias = alias;
        this.namePK = namePK;
        this.columns = columns;
    }

    public String getTableName() {
        return tableName;
    }

    public String getAlias() {
        return alias;
    }

    public String getNamePK() {
        return namePK;
    }

    public Set<String> getColumns() {
        return columns;
    }
}
