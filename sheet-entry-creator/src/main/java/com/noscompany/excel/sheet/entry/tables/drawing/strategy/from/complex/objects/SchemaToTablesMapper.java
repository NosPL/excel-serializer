package com.noscompany.excel.sheet.entry.tables.drawing.strategy.from.complex.objects;

import com.noscompany.excel.commons.Config;
import com.noscompany.excel.sheet.entry.schema.ComplexValue;
import com.noscompany.excel.sheet.entry.schema.Schema;
import com.noscompany.excel.sheet.entry.schema.ValueCollection;
import com.noscompany.excel.sheet.entry.table.Table;
import lombok.Value;

import java.awt.*;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Value
class SchemaToTablesMapper {
    Config config;

    Tables map(Schema schema) {
        return new Tables(
                mainObjectTable(schema),
                tablesFromComplexValues(schema),
                tablesFromValueCollections(schema)
        );
    }

    private Table mainObjectTable(Schema schema) {
        return Table.creator()
                .title(titleColor(), schema.getName())
                .recordLabels(recordLabelsColor(), schema.simpleFieldNames())
                .records(recordColor(), schema.simpleFieldValues())
                .create();
    }

    private List<Table> tablesFromComplexValues(Schema schema) {
        return schema
                .getComplexValues()
                .stream()
                .map(this::toTable)
                .collect(toList());
    }

    private List<Table> tablesFromValueCollections(Schema schema) {
        return schema
                .getValueCollections()
                .stream()
                .map(this::getTable)
                .collect(toList());
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