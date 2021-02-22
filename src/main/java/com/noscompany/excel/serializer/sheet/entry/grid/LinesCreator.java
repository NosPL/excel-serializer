package com.noscompany.excel.serializer.sheet.entry.grid;

import com.noscompany.excel.serializer.commons.Config;
import com.noscompany.excel.serializer.field.extractor.FieldExtractor;
import com.noscompany.excel.serializer.field.extractor.SimpleField;
import io.vavr.Tuple2;
import io.vavr.collection.Vector;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.noscompany.excel.serializer.commons.ExcelUtils.isSimple;
import static com.noscompany.excel.serializer.sheet.entry.grid.Line.line;
import static io.vavr.collection.Vector.ofAll;
import static java.util.stream.Collectors.toList;

class LinesCreator {
    private final Config config;
    private final FieldExtractor fieldExtractor;

    LinesCreator(Config config) {
        this.config = config;
        this.fieldExtractor = new FieldExtractor();
    }

    Lines create(Collection<?> collection) {
        if (collectionMembersAreSimple(collection)) {
            return fromSimple(collection);
        } else
            return fromComplex(collection);
    }

    private Lines fromSimple(Collection<?> collection) {
        List<String> values = collection
                .stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(toList());
        Line line = line(values, nameColor(), valuesColor());
        return Lines.of(line);
    }

    private Lines fromComplex(Collection<?> collection) {
        List<Line> lines = ofAll(collection)
                .flatMap(fieldExtractor::simpleFields)
                .groupBy(SimpleField::getName)
                .map(this::toLine)
                .toJavaList();
        return new Lines(lines);
    }

    private Line toLine(Tuple2<String, Vector<SimpleField>> tuple) {
        String name = tuple._1;
        List<String> values = tuple._2.map(SimpleField::getValue).toJavaList();
        return line(name, values, nameColor(), valuesColor());
    }

    public boolean collectionMembersAreSimple(Collection<?> collection) {
        if (collection.isEmpty())
            return false;
        Object next = collection.iterator().next();
        return isSimple(next.getClass());
    }

    private short nameColor() {
        return config.getRecordNamesColor();
    }

    private short valuesColor() {
        return config.getRecordValuesColor();
    }
}