package com.noscompany.excel.converter;

import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;

public class ExcelGenerator {
    private Workbook workbook = new HSSFWorkbook();
    private Sheet sheet = workbook.createSheet();

    public <T> void save(Collection<T> collection, File file) {
        if (collection.isEmpty())
            return;
        T parent = collection.iterator().next();
        int parentHeight = calculateParentHeight(parent);
        int rowOffset = 0;
        for (T object : collection) {
            setCell(rowOffset, 0, parent.getClass().getSimpleName());
            writeInFieldNames(rowOffset + 1, 0, getPrimitiveAndStringFields(object.getClass()));
            writeInPrimitivesAndStrings(rowOffset + 1, 1, getPrimitiveAndStringFields(object.getClass()), object);
            writeInCollections(rowOffset, 3, getCollectionFields(object.getClass()), object);
            rowOffset += parentHeight + 1;
        }

        writeToFile(file);
    }

    private void writeToFile(File file) {
        try (OutputStream out = new FileOutputStream(file)) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCell(int row, int column, String object) {
        if (sheet.getRow(row) == null)
            sheet.createRow(row).createCell(column).setCellValue(object);
        else
            sheet.getRow(row).createCell(column).setCellValue(object);
    }

    private void setCell(int row, int column, double object) {
        if (sheet.getRow(row) == null)
            sheet.createRow(row).createCell(column).setCellValue(object);
        else
            sheet.getRow(row).createCell(column).setCellValue(object);
    }

    private void setCell(int row, int column, int object) {
        if (sheet.getRow(row) == null)
            sheet.createRow(row).createCell(column).setCellValue(object);
        else
            sheet.getRow(row).createCell(column).setCellValue(object);
    }

    @SneakyThrows
    private <T, E> void writeInCollections(int rowOffset, int columnOffset, Field[] collectionFields, T parent) {
        int localRowOffset = rowOffset;
        int localColumnOffset = columnOffset;
        for (Field f : collectionFields) {
            Collection<?> collection = (Collection<?>) f.get(parent);
            if (collection.isEmpty())
                continue;
            setCell(rowOffset, columnOffset, f.getName());
            Iterator<?> iterator = collection.iterator();
            E next = (E) iterator.next();
            writeInFieldNames(rowOffset + 1, columnOffset, next.getClass().getDeclaredFields());
            iterator = collection.iterator();
            int columnPointer = columnOffset + 1;
            while (iterator.hasNext()) {
                E o = (E) iterator.next();
                writeInPrimitivesAndStrings(rowOffset + 1, columnPointer, next.getClass().getDeclaredFields(), o);
                columnPointer++;
            }
            rowOffset += 1 + next.getClass().getDeclaredFields().length + 1;
        }
    }

    @SneakyThrows
    private <E> void writeInFieldNames(int rowOffset, int columnOffset, Field[] fields) {
        for (Field f : fields) {
            f.setAccessible(true);
            String fieldName = f.getAnnotation(Excel.class).value();
            if (fieldName.equals(""))
                setCell(rowOffset, columnOffset, f.getName());
            else
                setCell(rowOffset, columnOffset, fieldName);
            rowOffset++;
        }
    }

    @SneakyThrows
    private <E> void writeInPrimitivesAndStrings(int rowOffset, int columnOffset, Field[] fields, E object) {
        for (Field f : fields) {
            f.setAccessible(true);
            Object o = f.get(object);
            if (f.getType().isPrimitive()) {
                try {
                    setCell(rowOffset, columnOffset, (double) o);
                } catch (Exception e) {
                }
                try {
                    setCell(rowOffset, columnOffset, (int) o);
                } catch (Exception e) {
                }
            } else
                setCell(rowOffset, columnOffset, o.toString());
            rowOffset++;
        }
    }

    @SneakyThrows
    private <T, E> int calculateParentHeight(T object) {
        int primitiveAndStringsAmount = 0;
        Class<?> aClass = object.getClass();
        Field[] primitiveAndStringFields = getPrimitiveAndStringFields(aClass);
        primitiveAndStringsAmount = primitiveAndStringFields.length;
        Field[] collectonFields = getCollectionFields(aClass);
        int collectionsFieldsAmount = 0;
        collectionsFieldsAmount = getCollectionsFieldsAmount(object, collectonFields);
        return primitiveAndStringsAmount > collectionsFieldsAmount ? primitiveAndStringsAmount : collectionsFieldsAmount;
    }

    private <T, E> int getCollectionsFieldsAmount(T object, Field[] collectonFields) throws IllegalAccessException {
        int collectionsFieldsAmount = 0;
        for (Field f : collectonFields) {
            Collection<?> collection = (Collection<?>) f.get(object);
            E next = (E) collection.iterator().next();
            collectionsFieldsAmount += next.getClass().getDeclaredFields().length;
            collectionsFieldsAmount += 2;
        }
        return collectionsFieldsAmount;
    }

    private Field[] getPrimitiveAndStringFields(Class<?> aClass) {
        Field[] declaredFields = aClass.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(f -> f.setAccessible(true));
        Field[] excelFields = Arrays.stream(declaredFields).filter(f -> f.isAnnotationPresent(Excel.class)).toArray(Field[]::new);
        Field[] primitiveAndStringFields = Arrays.stream(excelFields).filter(f -> f.getType().isPrimitive() || f.getType().isAssignableFrom(String.class)).toArray(Field[]::new);
        return primitiveAndStringFields;
    }

    private Field[] getCollectionFields(Class<?> aClass) {
        Field[] declaredFields = aClass.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(f -> f.setAccessible(true));
        Field[] excelFields = Arrays.stream(declaredFields).filter(f -> f.isAnnotationPresent(Excel.class)).toArray(Field[]::new);
        Field[] collectionFields = Arrays.stream(excelFields)
                .filter(f -> (f.getType().isAssignableFrom(Set.class) || f.getType().isAssignableFrom(List.class)))
                .toArray(Field[]::new);
        return collectionFields;
    }
}