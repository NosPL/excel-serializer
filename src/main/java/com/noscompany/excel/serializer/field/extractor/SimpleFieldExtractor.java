package com.noscompany.excel.serializer.field.extractor;

import com.noscompany.excel.serializer.commons.ExcelUtils;
import io.vavr.collection.Vector;
import lombok.AllArgsConstructor;

import java.util.List;

import static com.noscompany.excel.serializer.commons.ExcelUtils.name;

@AllArgsConstructor
class SimpleFieldExtractor {

    List<SimpleField> extract(Object object) {
        return Vector
                .of(object.getClass().getDeclaredFields())
                .filter(field -> ExcelUtils.isSimple(field.getType()))
                .map(field -> new SimpleField(name(field), field))
                .toJavaList();
    }

}