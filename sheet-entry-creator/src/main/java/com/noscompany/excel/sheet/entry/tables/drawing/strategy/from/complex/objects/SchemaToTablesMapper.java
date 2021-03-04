package com.noscompany.excel.sheet.entry.tables.drawing.strategy.from.complex.objects;

import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.schema.ComplexValue;
import com.noscompany.excel.commons.schema.Schema;
import com.noscompany.excel.commons.schema.ValueCollection;
import com.noscompany.excel.commons.table.Table;
import com.noscompany.excel.commons.table.TablesSequence;
import lombok.Value;

import java.awt.*;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Value
class SchemaToTablesMapper {
    Config config;

    Tables map(Schema schema) {
        return new Tables(
                tablesFromMainObjectAndComplexValues(schema),
                tablesFromValueCollections(schema)
        );
    }

    private TablesSequence tablesFromMainObjectAndComplexValues(Schema schema) {
        Table mainObjectTable = mainObjectTable(schema);
        List<Table> fromComplexValues = schema
                .getComplexValues()
                .stream()
                .map(this::toTable)
                .collect(toList());
        fromComplexValues.add(0, mainObjectTable);
        return TablesSequence.createFrom(fromComplexValues);
    }

    private Table mainObjectTable(Schema schema) {
        return Table.creator()
                .title(titleColor(), schema.getName())
                .recordLabels(recordLabelsColor(), schema.simpleFieldNames())
                .records(recordColor(), schema.simpleFieldValues())
                .create();
    }

    private TablesSequence tablesFromValueCollections(Schema schema) {
        List<Table> tables = schema
                .getValueCollections()
                .stream()
                .map(this::getTable)
                .collect(toList());
        return TablesSequence.createFrom(tables);
    }

    private Table toTable(ComplexValue complexValue) {
        return Table.creator()
                .title(titleColor(), complexValue.getName())
                .recordLabels(recordLabelsColor(), complexValue.fieldNames())
                .records(recordColor(), List.of(complexValue.fieldValues()))
                .create();
    }

    private Table getTable(ValueCollection valueCollection) {
        return Table.creator()
                .title(titleColor(), valueCollection.getName())
                .recordLabels(recordLabelsColor(), valueCollection.getFieldNames())
                .records(recordColor(), valueCollection.getFieldValues())
                .addRecordIndexes(indexColor())
                .create();
    }

    private Color indexColor() {
        return config.getRecordIndexColor();
    }

    private boolean addIndexes() {
        return config.isIndexedTableRecords();
    }

    private Color recordColor() {
        return config.getRecordValuesColor();
    }

    private Color recordLabelsColor() {
        return config.getRecordLabelsColor();
    }

    private Color titleColor() {
        return config.getTableTitleColor();
    }
}