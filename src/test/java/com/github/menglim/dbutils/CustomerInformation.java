package com.github.menglim.dbutils;

import com.github.menglim.dbutils.annotations.DbDateFormat;
import com.github.menglim.dbutils.annotations.DbField;
import com.github.menglim.dbutils.interfaces.BaseObjectModel;
import com.github.menglim.mutils.Constants;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class CustomerInformation implements BaseObjectModel<CustomerInformation> {

    @DbField("CIFKEY")
    private Long cifKey;

    @DbField("CIFCRTDT")
    @DbDateFormat(fromFormatDate = Constants.FormatDate.YYYYMMDD, toFormatDate = "dd/MM/yyyy")
    private String createdDate;

    @Override
    public CustomerInformation newInstance() {
        return new CustomerInformation();
    }
}
