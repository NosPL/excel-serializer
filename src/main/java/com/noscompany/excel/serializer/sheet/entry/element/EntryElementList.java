package com.noscompany.excel.serializer.sheet.entry.element;

import lombok.Value;

import java.util.LinkedList;
import java.util.List;

@Value
public class EntryElementList {
    EntryElement mainObjectEntry;
    List<EntryElement> fromComplexFields;
    List<EntryElement> fromCollectionFields;
}
