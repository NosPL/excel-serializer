package com.noscompany.excel.converter.entries.simple.fields;

import com.noscompany.excel.converter.CellAddress;
import com.noscompany.excel.converter.CellEntry;
import com.noscompany.excel.converter.Config;
import com.noscompany.excel.converter.ExcelUtils;
import lombok.SneakyThrows;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.noscompany.excel.converter.Config.SimpleFieldsOrientation.VERTICAL;

public class SimpleFieldsEntryCreator {
    private final Config config;

    public SimpleFieldsEntryCreator(Config config) {
        this.config = config;
    }

    public <T> Set<CellEntry> create(T object, CellAddress entryStartingPoint) {
        CellAddress currentCellAddress = entryStartingPoint.moveDown(config.mainObjectLabelOffset());
        Set<CellEntry> fieldNames = fieldNames(currentCellAddress, object);
        currentCellAddress = config.getSimpleFieldsOrientation() == VERTICAL ? currentCellAddress.moveToRight(1) : currentCellAddress.moveDown(1);
        Set<CellEntry> fieldValues = fieldValues(currentCellAddress, object);
        fieldNames.addAll(fieldValues);
        return fieldNames;
    }

    @SneakyThrows
    private <E> Set<CellEntry> fieldNames(CellAddress startingPoint, E object) {
        List<SimpleField<E>> fields = ExcelUtils.simpleFields(object);
        int column = startingPoint.getColumn();
        int row = startingPoint.getRow();
        Set<CellEntry> result = new HashSet<>();
        for (SimpleField<E> field : fields) {
            result.add(new CellEntry(row, column, field.getName()));
            if (config.getSimpleFieldsOrientation() == VERTICAL)
                row++;
            else
                column++;
        }
        return result;
    }

    @SneakyThrows
    private <E> Set<CellEntry> fieldValues(CellAddress startingPoint, E object) {
        List<SimpleField<E>> fields = ExcelUtils.simpleFields(object);
        int column = startingPoint.getColumn();
        int row = startingPoint.getRow();
        HashSet<CellEntry> result = new HashSet<>();
        for (SimpleField<E> f : fields) {
            result.add(new CellEntry(row, column, f.getValue()));
            if (config.getSimpleFieldsOrientation() == VERTICAL)
                row++;
            else
                column++;
        }
        return result;
    }
}
