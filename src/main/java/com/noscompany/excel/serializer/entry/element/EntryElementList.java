package com.noscompany.excel.serializer.entry.element;

import lombok.Value;

import java.util.LinkedList;
import java.util.List;

@Value
public class EntryElementList {
    EntryElement mainObjectEntry;
    List<EntryElement> singleValueEntryElements;
    List<EntryElement> collectionEntryElements;

    public EntryElementList addEntriesFromComplexFields(List<EntryElement> entryElements) {
        this.singleValueEntryElements.addAll(entryElements);
        return this;
    }

    public EntryElementList addEntriesFromCollectionFields(List<EntryElement> entryElements) {
        this.collectionEntryElements.addAll(entryElements);
        return this;
    }

    public static EntryElementList fromMainObject(EntryElement mainObjectEntry) {
        return new EntryElementList(mainObjectEntry, new LinkedList<>(), new LinkedList<>());
    }

}
