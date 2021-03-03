package com.noscompany.excel.commons.sample.data;

import com.noscompany.excel.commons.JessyObject;
import com.noscompany.excel.commons.annotations.ClassName;
import com.noscompany.excel.commons.annotations.FieldName;
import com.noscompany.excel.commons.annotations.Inline;
import lombok.Value;

import java.util.List;

@Value
@ClassName(value = "PRACOWNIK")
public class Employee implements JessyObject {
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

    public String getFirstName() {
        return name.getFirstName();
    }

    public String getLastName() {
        return name.getLastName();
    }

    public String getDepartmentName() {
        return department.getName();
    }

    public String getDepartmentId() {
        return department.getId();
    }

    public Address getAddress(int index) {
        return addresses.get(index);
    }

    public Phone getPhone(int index) {
        return phones.get(index);
    }

    public String getAlias(int index) {
        return aliases.get(index);
    }
}