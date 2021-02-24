package com.noscompany.excel.sheet.entry.schema.creator;

import com.noscompany.excel.sheet.entry.schema.ComplexType;
import com.noscompany.excel.sheet.entry.schema.TypeCollection;
import lombok.Value;

import java.util.List;

import static io.vavr.collection.Vector.ofAll;

@Value
class ComplexTypeCollection implements TypeCollection {
    String name;
    List<ComplexType> complexTypes;

    public List<String> getFieldNames() {
        return ofAll(complexTypes)
                .headOption()
                .map(ComplexType::fieldNames)
                .getOrElse(List.of());
    }

    public List<List<String>> getFieldValues() {
        return ofAll(complexTypes)
                .map(ComplexType::fieldValues)
                .toJavaList();
    }

}
