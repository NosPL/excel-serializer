package com.noscompany.excel.serializer.utils;

import com.noscompany.excel.commons.CellAddress;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExcelAssertions {
    private final List<Cell> cells;

    private ExcelAssertions(List<Cell> cells) {
        this.cells = cells;
    }

    @SneakyThrows
    public static ExcelAssertions assertThatExcelSheetFrom(File file) {
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        List<Cell> cells = getAllCellsFrom(sheet);
        return new ExcelAssertions(cells);
    }

    private static List<Cell> getAllCellsFrom(Sheet sheet) {
        List<Cell> cells = new LinkedList<>();
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getCellType() != BLANK)
                    cells.add(cell);
            }
        }
        return cells;
    }

    public ExcelAssertions andNothingMore() {
        assertTrue(cells.isEmpty(), "Some cell entries left after assertions: " + cells);
        return this;
    }

    public AtPosition at(CellAddress position) {
        return new AtPosition(position, this);
    }

    public static class AtPosition {
        private final CellAddress cellAddress;
        private final ExcelAssertions excelAssertions;

        private AtPosition(CellAddress cellAddress, ExcelAssertions excelAssertions) {
            this.cellAddress = cellAddress;
            this.excelAssertions = excelAssertions;
        }

        //        @SafeVarargs
        public final ExcelAssertions containsTable(Record... records) {
            new TableAssertion(cellAddress, asList(records)).assertContains(excelAssertions.cells);
            return excelAssertions;
        }
    }
}

