package com.noscompany.excel.serializer.data;

import com.noscompany.excel.commons.annotations.ClassName;
import com.noscompany.excel.commons.annotations.FieldName;
import com.noscompany.excel.commons.annotations.Inline;
import lombok.Value;

import java.util.List;

@Value
@ClassName(value = "PRACOWNIK")
public class Employee {
    @Inline
    Name name;
    @FieldName(Value = "PESEL")
    String PID;
    @FieldName(Value = "DATA URODZENIA")
    String BirthDate;
    @FieldName(Value = "STANOWISKO")
    Department department;
    @FieldName(Value = "ADRESY")
    List<Address> addresses;
    @FieldName(Value = "NUMERY TEL")
    List<Phone> phones;
    @FieldName(Value = "ALIASY")
    List<String> aliases;
}