package com.noscompany.excel.serializer.sheet.entry.grid;

import lombok.Value;

import java.util.List;

@Value
public class GridList {
    Grid fromMainObject;
    List<Grid> fromComplexFields;
    List<Grid> fromCollectionFields;
}
