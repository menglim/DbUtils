package com.github.menglim.dbutils;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import com.github.menglim.dbutils.annotations.DbDataSource;
import com.github.menglim.dbutils.annotations.DbField;
import com.github.menglim.dbutils.interfaces.BaseObjectModel;

@Builder(toBuilder = true)
@Data
@ToString
@DbDataSource(schema = "users", connectionIndex = 1)
public class User implements BaseObjectModel<User> {

    @DbField(value = "user_no", insertField = false, primaryKey = true)
    private Long userId;

    @DbField("user_name")
    private String username;

    @DbField("display_name")
    private String displayName;

    @Override
    public User newInstance() {
        return User.builder().build();
    }
}
