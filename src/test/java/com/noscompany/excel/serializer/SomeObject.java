package com.noscompany.excel.serializer;

import com.noscompany.excel.serializer.annotations.ClassName;
import com.noscompany.excel.serializer.annotations.FieldName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Random;

@AllArgsConstructor
@NoArgsConstructor
@ClassName(value = "JAKIś OBIEKT")
public class SomeObject {
    @FieldName(Value = "jakieś pole 1")
    private String someProperty1 = "Some property 1";
    @FieldName(Value = "pole z nullem")
    private String someProperty2 = null;
    @FieldName(Value = "jakieś pole 2")
    private String someProperty3 = "Some property 3";

    public static SomeObject sample() {
        int i = new Random().nextInt(3);
        if (i == 0)
            return null;
        else if (i == 1)
            return new SomeObject(null, null, null);
        else
            return new SomeObject();
    }
}
