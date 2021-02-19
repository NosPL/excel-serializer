package com.noscompany.excel.converter.entries;

import com.noscompany.excel.converter.CellAddress;
import com.noscompany.excel.converter.CellEntry;
import com.noscompany.excel.converter.Config;
import com.noscompany.excel.converter.entries.collection.fields.CollectionFieldsEntryCreator;
import com.noscompany.excel.converter.entries.main.label.MainObjectLabelEntryCreator;
import com.noscompany.excel.converter.entries.simple.fields.SimpleFieldsEntryCreator;
import io.vavr.collection.List;
import io.vavr.control.Option;

import java.util.Set;

public class CellEntryCreator {
    private SimpleFieldsEntryCreator simpleFieldsEntryCreator;
    private CollectionFieldsEntryCreator collectionFieldsEntryCreator;
    private MainObjectLabelEntryCreator mainObjectLabelEntryCreator;

    public CellEntryCreator(Config config) {
        simpleFieldsEntryCreator = new SimpleFieldsEntryCreator(config);
        collectionFieldsEntryCreator = new CollectionFieldsEntryCreator(config);
        mainObjectLabelEntryCreator = new MainObjectLabelEntryCreator(config);
    }


    public <T> Set<CellEntry> create(T object, CellAddress startingPoint) {
        Option<CellEntry> mainObjectLabelEntry = mainObjectLabelEntryCreator.create(object, startingPoint);
        Set<CellEntry> simpleFieldsEntries = simpleFieldsEntryCreator.create(object, startingPoint);
        Set<CellEntry> collectionFieldsEntries = collectionFieldsEntryCreator.cellEntries(object, startingPoint);
        return List
                .ofAll(mainObjectLabelEntry)
                .appendAll(simpleFieldsEntries)
                .appendAll(collectionFieldsEntries)
                .toJavaSet();
    }
}