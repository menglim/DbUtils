package com.github.menglim.dbutils;

import com.github.menglim.mutils.AppUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DbUtils extends AppUtils {

    private static DbUtils instance;

    public static DbUtils getInstance() {
        if (instance == null) instance = new DbUtils();
        return instance;
    }


}
