package com.noscompany.excel.serializer;

import com.noscompany.excel.serializer.annotations.ExcelObject;
import com.noscompany.excel.serializer.annotations.ExcelSimpleField;
import lombok.Value;

import java.util.List;

@Value
@ExcelObject(label = "PRACOWNIK")
public class Employee {
    @ExcelSimpleField(label = "JAKIŚ OBIEKT")
    SomeObject someObject;
    @ExcelSimpleField(label = "JAKIŚ OBIEKT")
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
    @ExcelSimpleField(label = "ADRESY")
    List<Address> addresses;
    @ExcelSimpleField(label = "NUMERY TEL")
    List<Phone> phones;
    @ExcelSimpleField(label = "ALIASY")
    List<String> aliases;
}