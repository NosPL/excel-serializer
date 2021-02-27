package com.noscompany.excel.sheet.entry.table;

import io.vavr.Tuple2;
import io.vavr.control.Option;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

import static io.vavr.collection.Vector.ofAll;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PACKAGE;

@NoArgsConstructor(access = PACKAGE)
public class TableCreator {
    private Option<Title> title = Option.none();
    private Option<RecordLabels> recordLabels = Option.none();
    private List<Record> records = new LinkedList<>();
    private boolean addRecordIndexes = false;

    public TableCreator title(short color, String title) {
        this.title = Option.of(new Title(title, color));
        return this;
    }

    public TableCreator recordLabels(short color, List<String> labels) {
        recordLabels = Option.of(new SimpleRecordLabels(labels, color));
        return this;
    }

    @SafeVarargs
    public final TableCreator records(short color, List<String>... records) {
        this.records = stream(records).map(l -> new SimpleRecord(l, color)).collect(toList());
        return this;
    }

    public TableCreator records(short color, List<List<String>> records) {
        this.records = records.stream().map(l -> new SimpleRecord(l, color)).collect(toList());
        return this;
    }

    public TableCreator addRecordIndexes(boolean value) {
        this.addRecordIndexes = value;
        return this;
    }

    public Table create() {
        if (addRecordIndexes) {
            recordLabels = indexed(recordLabels);
            records = indexed(records);
        }
        Records records = new Records(recordLabels, this.records);
        return new Table(title, records);
    }

    private List<Record> indexed(List<Record> records) {
        return ofAll(records)
                .zipWithIndex()
                .map(this::indexRecord)
                .toJavaList();
    }

    private Record indexRecord(Tuple2<Record, Integer> tuple) {
        Record record = tuple._1;
        int index = tuple._2 + 1;
        return new IndexedRecord(index, record);
    }

    private Option<RecordLabels> indexed(Option<RecordLabels> recordLabels) {
        return recordLabels
                .map(IndexedRecordLabels::new);
    }
}