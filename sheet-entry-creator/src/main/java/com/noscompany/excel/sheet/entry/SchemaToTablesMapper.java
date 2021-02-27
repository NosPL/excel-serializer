package com.noscompany.excel.sheet.entry;

import com.noscompany.excel.commons.Config;
import com.noscompany.excel.sheet.entry.schema.ComplexType;
import com.noscompany.excel.sheet.entry.schema.Schema;
import com.noscompany.excel.sheet.entry.schema.TypeCollection;
import com.noscompany.excel.sheet.entry.table.Table;
import com.noscompany.excel.sheet.entry.table.Tables;
import lombok.Value;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Value
class SchemaToTablesMapper {
    Config config;

    Tables map(Schema schema) {
        return new Tables(
                mainObjectTable(schema),
                tablesFromComplexFields(schema),
                tablesFromCollectionFields(schema)
        );
    }

    private Table mainObjectTable(Schema schema) {
        return Table.creator()
                .title(titleColor(), schema.getName())
                .recordLabels(recordLabelsColor(), schema.simpleFieldNames())
                .records(recordColor(), schema.simpleFieldValues())
                .create();
    }

    private List<Table> tablesFromComplexFields(Schema schema) {
        return schema
                .getComplexTypes()
                .stream()
                .map(this::toTable)
                .collect(toList());
    }

    private List<Table> tablesFromCollectionFields(Schema schema) {
        return schema
                .getTypeCollections()
                .stream()
                .map(this::getTable)
                .collect(toList());
    }

    private Table toTable(ComplexType complexType) {
        return Table.creator()
                .title(titleColor(), complexType.getName())
                .recordLabels(recordLabelsColor(), complexType.fieldNames())
                .records(recordColor(), List.of(complexType.fieldValues()))
                .create();
    }

    private Table getTable(TypeCollection typeCollection) {
        return Table.creator()
                .title(titleColor(), typeCollection.getName())
                .recordLabels(recordLabelsColor(), typeCollection.getFieldNames())
                .records(recordColor(), typeCollection.getFieldValues())
                .addRecordIndexes(addIndexes())
                .create();
    }

    private boolean addIndexes() {
        return config.isIndexedTableRecords();
    }

    private short recordColor() {
        return config.getRecordValuesColor();
    }

    private short recordLabelsColor() {
        return config.getRecordLabelsColor();
    }

    private short titleColor() {
        return config.getTableTitleColor();
    }
}