package com.noscompany.excel.serializer.field.extractor;

import lombok.Value;

import java.util.Collection;

@Value
public class CollectionField {
    String name;
    Collection collection;
}