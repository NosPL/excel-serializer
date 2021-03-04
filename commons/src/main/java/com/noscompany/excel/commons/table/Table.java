package com.noscompany.excel.commons.table;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntries;
import com.noscompany.excel.commons.CellEntry;
import io.vavr.control.Option;
import lombok.Value;

import java.util.LinkedList;
import java.util.List;

@Value
public class Table {

    public enum Layout {HORIZONTAL, VERTICAL}

    Option<Title> title;
    Records records;
    Layout layout;

    public static TableCreator creator() {
        return new TableCreator();
    }

    public CellEntries draw(CellAddress startingPosition) {
        return draw(startingPosition, layout);
    }


    public CellEntries draw(CellAddress startingPosition, Layout layout) {
        List<CellEntry> result = new LinkedList<>();
        if (title.isDefined()) {
            result.add(title.get().toCellEntry(startingPosition));
            startingPosition = startingPosition.moveDown(1);
        }
        result.addAll(records.draw(startingPosition, layout));
        return new CellEntries(result);
    }
}