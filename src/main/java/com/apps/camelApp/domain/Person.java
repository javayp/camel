package com.apps.camelApp.domain;

import lombok.Data;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@Data
@CsvRecord(separator = ",", skipFirstLine = true, generateHeaderColumns = true)
public class Person {

    @DataField(pos = 1, columnName = "userId")
    private String userId;

    @DataField(pos = 2, columnName = "userName")
    private String userName;
}
