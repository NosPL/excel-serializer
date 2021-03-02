package com.noscompany.excel.sheet.entry.utils;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.CellEntry;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.function.Supplier;

import static io.vavr.collection.Vector.ofAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AllArgsConstructor
public class TableAssertion {
    private CellAddress startingPosition;
    private List<Record> records;

    public void assertContains(List<CellEntry> cellEntries) {
        records.forEach(record -> {
            assertContains(record, startingPosition, cellEntries);
            startingPosition = startingPosition.moveDown(1);
        });
    }

    private void assertContains(Record record, CellAddress startingPosition, List<CellEntry> cellEntries) {
        for (String value : record.getValues()) {
            CellEntry found = find(cellEntries, startingPosition)
                    .getOrElseThrow(notFound(value, startingPosition));
            assertEquals(found.getCellValue(), value, "Wrong value for position: " + found.getCellAddress());
            startingPosition = startingPosition.moveToRight(1);
            cellEntries.remove(found);
        }
    }

    private Supplier<SheenEntryAssertionException> notFound(String value, CellAddress startingPosition) {
        return () -> new SheenEntryAssertionException(String.format("value '%s' not found, for position: %s", value, startingPosition.toString()));
    }

    private Option<CellEntry> find(List<CellEntry> cellEntries, CellAddress cellAddress) {
        return ofAll(cellEntries)
                .filter(cellEntry -> cellEntry.getCellAddress().equals(cellAddress))
                .headOption();
    }
}
