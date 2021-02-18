package com.noscompany.excel.converter;

import com.noscompany.excel.converter.annotations.ExcelField;
import com.noscompany.excel.converter.annotations.ExcelLabel;
import lombok.Value;

import java.util.List;

@Value
@ExcelLabel("PRACOWNIK")
public class Employee {
    @ExcelField("IMIĘ")
    String firstName;
    @ExcelField("NAZWISKO")
    String lastName;
    @ExcelField("PESEL")
    String PID;
    @ExcelField("WIEK")
    int age;
    @ExcelField("DATA URODZENIA")
    String BirthDate;
    @ExcelField("WZROST(W CM)")
    int heightInCm;
    @ExcelField("WAGA(W KG)")
    int weightInKilos;
    @ExcelField("STATUS")
    String status;
    @ExcelField("OBYWATELSTWO")
    String nationality;
    @ExcelField("ADRESY")
    List<Address> addresses;
    @ExcelField("NUMERY TEL")
    List<Phone> phones;
    @ExcelField("ALIASY")
    List<String> aliases;
}