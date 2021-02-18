package com.noscompany.excel.converter;

import lombok.Value;

@Value
public class Address {
    String streetName;
    int streetNumber;
    int houseNumber;
    String postalCode;
    String cityName;
}
