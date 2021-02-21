package com.noscompany.excel.serializer;

import com.noscompany.excel.serializer.annotations.ClassName;
import com.noscompany.excel.serializer.annotations.FieldName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Random;

@AllArgsConstructor
@NoArgsConstructor
@ClassName(value = "JAKIś inny OBIEKT")
public class SomeOtherObject {
    @FieldName(Value = "jakieś pole 1")
    Integer someProperty1 = 312983;
    Integer someProperty2 = 82392;
    Integer someProperty3 = 0;
    @FieldName(Value = "jakieś pole 4")
    Integer someProperty4 = 1;

    public static SomeOtherObject sample() {
        int i = new Random().nextInt(3);
        if (i == 0)
            return null;
        else if (i == 1)
            return new SomeOtherObject(null, null, null, null);
        else
            return new SomeOtherObject();
    }
}
