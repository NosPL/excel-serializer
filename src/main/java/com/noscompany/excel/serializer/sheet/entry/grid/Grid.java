package com.noscompany.excel.serializer.sheet.entry.grid;

import com.noscompany.excel.serializer.commons.CellAddress;
import com.noscompany.excel.serializer.commons.CellEntry;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@Getter
public class Grid {
    private final Option<Label> label;
    private final Lines lines;

    public List<CellEntry> draw(CellAddress position, GridLayout gridLayout) {
        List<CellEntry> result = new LinkedList<>();
        if (label.isDefined()) {
            result.add(new CellEntry(position, label.get().getValue(), label.get().getColor()));
            position = position.moveDown(1);
        }
        result.addAll(lines.draw(position, gridLayout));
        return result;
    }

    @Value
    public static class Label {
        String value;
        Short color;
    }
}