package com.noscompany.excel.serializer.utils;

import com.noscompany.excel.commons.CellAddress;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;

import java.util.List;
import java.util.function.Supplier;

import static io.vavr.collection.Vector.ofAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@AllArgsConstructor
public class TableAssertion {
    private CellAddress startingPosition;
    private List<Record> records;

    public void assertContains(List<Cell> cells) {
        records.forEach(record -> {
            assertContains(record, startingPosition, cells);
            startingPosition = startingPosition.moveDown(1);
        });
    }

    private void assertContains(Record record, CellAddress startingPosition, List<Cell> cells) {
        for (String value : record.getValues()) {
            Cell found = find(cells, startingPosition)
                    .getOrElseThrow(notFound(value, startingPosition));
            assertEquals(found.getStringCellValue(), value, "Wrong value for position: " + addressOf(found));
            startingPosition = startingPosition.moveToRight(1);
            cells.remove(found);
        }
    }

    private Supplier<ExcelAssertionException> notFound(String value, CellAddress startingPosition) {
        return () -> new ExcelAssertionException(String.format("value '%s' not found, for position: %s", value, startingPosition.toString()));
    }

    private Option<Cell> find(List<Cell> cellEntries, CellAddress cellAddress) {
        return ofAll(cellEntries)
                .filter(cell -> addressOf(cell).equals(cellAddress))
                .headOption();
    }

    private CellAddress addressOf(Cell cell) {
        return new CellAddress(cell.getRowIndex(), cell.getColumnIndex());
    }
}
