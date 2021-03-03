package com.noscompany.excel.serializer;

import com.noscompany.excel.commons.JessyObject;

import java.io.File;

public interface SerializationStrategy {
    void serialize(Iterable<? extends JessyObject> objects, File file);

    boolean accepts(Iterable<? extends JessyObject> objects);
}
