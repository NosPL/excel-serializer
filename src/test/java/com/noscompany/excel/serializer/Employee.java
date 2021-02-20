package com.noscompany.excel.serializer;

import com.noscompany.excel.serializer.annotations.ExcelCollection;
import com.noscompany.excel.serializer.annotations.ExcelEmbedded;
import com.noscompany.excel.serializer.annotations.ExcelSimpleField;
import com.noscompany.excel.serializer.annotations.ExcelObject;
import lombok.Value;

import java.util.List;

@Value
@ExcelObject(label = "PRACOWNIK")
public class Employee {
    @ExcelEmbedded(label = "JAKIŚ OBIEKT")
    SomeObject someObject;
    @ExcelEmbedded(label = "JAKIŚ OBIEKT")
    SomeOtherObject someOtherObject;
    @ExcelSimpleField(label = "IMIĘ")
    String firstName;
    @ExcelSimpleField(label = "NAZWISKO")
    String lastName;
    @ExcelSimpleField(label = "PESEL")
    String PID;
    @ExcelSimpleField(label = "DATA URODZENIA")
    String BirthDate;
    @ExcelSimpleField(label = "WIEK")
    int age;
    @ExcelSimpleField(label = "OBYWATELSTWO")
    String nationality;
    @ExcelCollection(label = "ADRESY")
    List<Address> addresses;
    @ExcelCollection(label = "NUMERY TEL")
    List<Phone> phones;
    @ExcelCollection(label = "ALIASY")
    List<String> aliases;
}