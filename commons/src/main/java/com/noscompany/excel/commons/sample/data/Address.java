package com.noscompany.excel.commons.sample.data;

import lombok.Value;

@Value
public class Address {
    String streetName;
    int streetNumber;
    int houseNumber;
    City city;

    public Address(String streetName, int streetNumber, int houseNumber, String postalCode, String cityName) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.houseNumber = houseNumber;
        this.city = new City(postalCode, cityName);
    }
}
