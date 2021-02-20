package com.noscompany.excel.serializer.sheet.entry.element.extractor;

import lombok.Value;

import java.util.Collection;

@Value
public class CollectionField {
    String name;
    Collection collection;
}