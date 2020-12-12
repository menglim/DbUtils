package com.github.menglim.dbutils;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.github.menglim.dbutils.annotations.DbDataSource;
import com.github.menglim.dbutils.annotations.DbField;
import com.github.menglim.dbutils.interfaces.BaseObjectModel;

//@Builder(toBuilder = true)
@Data
@ToString
@DbDataSource(connectionIndex = 1, schema = "admin_navigation")
@NoArgsConstructor
public class LinkModel implements BaseObjectModel {
    @DbField( "navigation_name")
    private String navigationName;

    @DbField( "status")
    private int status;

    @Override
    public LinkModel newInstance() {
        return new LinkModel();
    }
}
