package com.noscompany.excel.serializer.sample.data;

import com.noscompany.excel.commons.JessyObject;
import com.noscompany.excel.commons.annotations.ClassName;
import com.noscompany.excel.commons.annotations.FieldName;
import lombok.Value;

import java.util.List;

@Value
@ClassName(value = "PRACOWNIK")
public class Employee implements JessyObject {
    @FieldName(value = "IMIE")
    String firstName;
    @FieldName(value = "NAZWISKO")
    String lastName;
    @FieldName(value = "PESEL")
    String PID;
    @FieldName(value = "DATA URODZENIA")
    String BirthDate;
    @FieldName(value = "STANOWISKO")
    Department department;
    @FieldName(value = "ADRESY")
    List<Address> addresses;
    @FieldName(value = "NUMERY TEL")
    List<Phone> phones;
    @FieldName(value = "ALIASY")
    List<String> aliases;

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