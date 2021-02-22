package com.noscompany.excel.serializer;

import com.noscompany.excel.serializer.annotations.ClassName;
import com.noscompany.excel.serializer.annotations.FieldName;
import com.noscompany.excel.serializer.annotations.Inline;
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