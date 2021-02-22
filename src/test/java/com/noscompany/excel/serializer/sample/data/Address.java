package com.noscompany.excel.serializer.sample.data;

import lombok.Value;

@Value
public class Address {
    String streetName;
    int streetNumber;
    int houseNumber;
    String postalCode;
    String cityName;
}
