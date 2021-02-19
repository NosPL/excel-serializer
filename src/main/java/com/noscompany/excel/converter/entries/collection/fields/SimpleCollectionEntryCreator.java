package com.noscompany.excel.converter.entries.collection.fields;

import com.noscompany.excel.converter.CellAddress;
import com.noscompany.excel.converter.CellEntry;
import com.noscompany.excel.converter.ExcelUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SimpleCollectionEntryCreator {
    public Set<CellEntry> getEntries(CellAddress currentPoint, Collection<?> collection) {
        HashSet<CellEntry> result = new HashSet<>();
        if (!ExcelUtils.isSimple(collection))
            return result;
        for (Object o : collection){
            result.add(new CellEntry(currentPoint, o.toString()));
            currentPoint = currentPoint.moveToRight(1);
        }
        return result;
    }
}
