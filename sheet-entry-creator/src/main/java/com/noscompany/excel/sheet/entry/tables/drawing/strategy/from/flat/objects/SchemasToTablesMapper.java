package com.noscompany.excel.sheet.entry.tables.drawing.strategy.from.flat.objects;

import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.schema.ComplexValue;
import com.noscompany.excel.commons.schema.Schema;
import com.noscompany.excel.commons.schema.ValueCollection;
import com.noscompany.excel.commons.table.Table;
import com.noscompany.excel.commons.table.TableCreator;
import io.vavr.Tuple2;
import io.vavr.collection.Vector;
import lombok.AllArgsConstructor;

import java.awt.*;
import java.util.List;

@AllArgsConstructor
class SchemasToTablesMapper {
    private Config config;

    Vector<Table> toTables(Vector<Schema> schemas) {
        return Vector.of(mainTableFrom(schemas))
                .appendAll(complexValueTablesFrom(schemas))
                .appendAll(valueCollectionTablesFrom(schemas));
    }

    private Table mainTableFrom(Vector<Schema> schemas) {
        TableCreator tableCreator = Table
                .creator()
                .title(titleColor(), titleFrom(schemas))
                .records(recordsColor(), recordsFrom(schemas))
                .recordLabels(labelsColor(), recordLabelsFrom(schemas));
        if (config.isIndexedTableRecords())
            tableCreator = tableCreator.addRecordIndexes(indexColor());
        return tableCreator.create();
    }

    private List<Table> complexValueTablesFrom(Vector<Schema> schemas) {
        return schemas
                .flatMap(Schema::getComplexValues)
                .groupBy(ComplexValue::getName)
                .map(this::toTable)
                .toJavaList();
    }

    private Iterable<Table> valueCollectionTablesFrom(Vector<Schema> schemas) {
        return schemas
                .flatMap(Schema::getValueCollections)
                .groupBy(ValueCollection::getName)
                .map(this::toTables);
    }

    private Table toTables(Tuple2<String, Vector<ValueCollection>> tuple) {
        String name = tuple._1;
        Vector<ValueCollection> valueCollections = tuple._2;
        List<String> recordLabels = valueCollections.headOption().map(ValueCollection::getFieldNames).getOrElse(List.of());
        List<List<String>> records = valueCollections.flatMap(ValueCollection::getFieldValues).toJavaList();
        TableCreator tableCreator = Table
                .creator()
                .title(titleColor(), name)
                .recordLabels(labelsColor(), recordLabels)
                .records(recordsColor(), records);
        if (config.isIndexedTableRecords())
            tableCreator.addRecordIndexes(indexColor());
        return tableCreator.create();
    }

    private Table toTable(Tuple2<String, Vector<ComplexValue>> tuple) {
        String title = tuple._1;
        List<String> recordLabels = tuple._2.headOption().map(ComplexValue::fieldNames).getOrElse(List.of());
        List<List<String>> values = tuple._2.map(ComplexValue::fieldValues).toJavaList();
        return Table
                .creator()
                .title(titleColor(), title)
                .recordLabels(labelsColor(), recordLabels)
                .records(recordsColor(), values)
                .create();
    }

    private Color indexColor() {
        return config.getRecordIndexColor();
    }

    private Color labelsColor() {
        return config.getRecordLabelsColor();
    }

    private Color recordsColor() {
        return config.getRecordValuesColor();
    }

    private Color titleColor() {
        return config.getTableTitleColor();
    }

    private List<String> recordLabelsFrom(Vector<Schema> schemas) {
        return schemas
                .headOption()
                .map(Schema::simpleFieldNames)
                .getOrElse(List::of);
    }

    private List<List<String>> recordsFrom(Vector<Schema> schemas) {
        return schemas
                .map(Schema::simpleFieldValues)
                .toJavaList();
    }

    private String titleFrom(Vector<Schema> schemas) {
        return schemas
                .headOption()
                .map(Schema::getName)
                .getOrElse("");
    }
}
