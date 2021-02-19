package com.noscompany.excel.converter.entries.collection.fields;

import com.noscompany.excel.converter.CellAddress;
import com.noscompany.excel.converter.CellEntry;
import com.noscompany.excel.converter.ExcelUtils;
import com.noscompany.excel.converter.entries.simple.fields.SimpleField;
import lombok.SneakyThrows;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.noscompany.excel.converter.ExcelUtils.isComplex;

public class ComplexCollectionEntryCreator {
    public Set<CellEntry> getEntries(CellAddress currentPoint, Collection<?> collection) {
        HashSet<CellEntry> result = new HashSet<>();
        if (!isComplex(collection))
            return result;
        result.addAll(getEntriesFromCollectionFieldNames(currentPoint, collection));
        result.addAll(getEntriesFromCollectionFieldValues(collection, currentPoint.moveToRight(1)));
        return result;
    }

    @SneakyThrows
    private Set<CellEntry> getEntriesFromCollectionFieldNames(CellAddress c, Collection collection) {
        Object o = collection.iterator().next();
        List<SimpleField<Object>> fields = ExcelUtils.simpleFields(o);
        HashSet<CellEntry> result = new HashSet<>();
        int column = c.getColumn();
        int row = c.getRow();
        for (SimpleField<Object> field : fields) {
            result.add(new CellEntry(row, column, field.getName()));
            row++;
        }
        return result;
    }

    private Set<CellEntry> getEntriesFromCollectionFieldValues(Collection<?> collection, CellAddress position) {
        HashSet<CellEntry> result = new HashSet<>();
        for (Object o : collection) {
            result.addAll(getEntriesFromCollectionFieldValues(position, o));
            position = position.moveToRight(1);
        }
        return result;
    }

    @SneakyThrows
    private <E> Set<CellEntry> getEntriesFromCollectionFieldValues(CellAddress c, E object) {
        List<SimpleField<E>> simpleFields = ExcelUtils.simpleFields(object);
        int column = c.getColumn();
        int row = c.getRow();
        HashSet<CellEntry> result = new HashSet<>();
        for (SimpleField<E> f : simpleFields) {
            result.add(new CellEntry(row, column, f.getValue()));
            row++;
        }
        return result;
    }
}
