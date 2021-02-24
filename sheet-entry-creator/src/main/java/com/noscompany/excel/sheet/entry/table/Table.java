package com.noscompany.excel.sheet.entry.table;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntry;
import io.vavr.control.Option;

import java.util.LinkedList;
import java.util.List;

import static java.util.List.of;

public class Table {

    public enum Layout {HORIZONTAL, VERTICAL}

    private Option<Title> title = Option.none();
    private final Records records = Records.empty();

    public List<CellEntry> draw(CellAddress startingPosition, Layout layout) {
        List<CellEntry> result = new LinkedList<>();
        if (title.isDefined()) {
            result.add(new CellEntry(startingPosition, title.get().getValue(), title.get().getColor()));
            startingPosition = startingPosition.moveDown(1);
        }
        result.addAll(records.draw(startingPosition, layout));
        return result;
    }

    public static Creator creator() {
        return new Creator();
    }

    public static class Creator {
        private final Table table = new Table();

        public Creator title(short color, String title) {
            table.title = Option.of(new Title(title, color));
            return this;
        }

        public Creator recordLabels(short color, List<String>labels) {
            table.records.addLabels(labels, color);
            return this;
        }

        @SafeVarargs
        public final Creator records(short color, List<String>... records) {
            table.records.addRecords(color, new LinkedList<>(of(records)));
            return this;
        }

        public Creator records(short color, List<List<String>> records) {
            table.records.addRecords(color, records);
            return this;
        }

        public Table create() {
            return table;
        }
    }
}
