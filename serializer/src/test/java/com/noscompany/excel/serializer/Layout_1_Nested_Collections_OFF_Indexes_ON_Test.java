package com.noscompany.excel.serializer;

import com.noscompany.excel.commons.CellAddress;
import com.noscompany.excel.commons.Config;
import com.noscompany.excel.commons.SurfaceSize;
import com.noscompany.excel.serializer.sample.data.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Random;

import static com.noscompany.excel.serializer.sample.data.SampleData.*;
import static com.noscompany.excel.serializer.utils.ExcelAssertions.assertThatExcelSheetFrom;
import static com.noscompany.excel.serializer.utils.Record.r;
import static java.awt.Color.ORANGE;

class Layout_1_Nested_Collections_OFF_Indexes_ON_Test {
    private Config config;
    private Employee employee;
    private final Random random = new Random();
    private File file;
    private ExcelSerializer excelSerializer;

    @BeforeEach
    void init() {
        config = Config.builder()
                .setBackground(1, ORANGE)
                .startingPosition(1, 1)
                .indexedTableRecords(true)
                .build();
        excelSerializer = new ExcelSerializer(config);
        file = new File("C:\\folder\\Employees.xlsx");
    }

    @Test
    void test() {
//        given that there is random employee with 2 addresses, 2 phones and 3 aliases
        employee = employee(2, 2, 3);
//        when serializing an employee object
        excelSerializer.serialize(employee, file);
//        then after including offset on padding of sheet entry
        CellAddress position = paddingOffset(startingPosition());
        //@formatter:off
//        excel file sheet contains
        assertThatExcelSheetFrom(file)
                .at(position)
                .containsTable(
                        r("PRACOWNIK"),
                        r("IMIE",           firstName()),
                        r("NAZWISKO",       lastName()),
                        r("PESEL",          pid()),
                        r("DATA URODZENIA", birthDate()))
                .at(departmentOffset(position))
                .containsTable(
                        r("STANOWISKO"),
                        r("NAZWA",          departmentName()),
                        r("id",             departmentId()))
                .at(addressesOffset(position))
                .containsTable(
                        r("ADRESY"),
                        r("",              "streetName",   "streetNumber",   "houseNumber"),
                        r("1",              streetName(0),  streetNumber(0),  houseNumber(0)),
                        r("2",              streetName(1),  streetNumber(1),  houseNumber(1)))
                .at(cityOffset(position))
                .containsTable(
                        r("city"),
                        r("",              "postalCode",      "cityName"),
                        r("1",              postalCode(0),     cityName(0)),
                        r("2",              postalCode(1),     cityName(1)))
                .at(telNumbersOffset(position))
                .containsTable(
                        r("NUMERY TEL"),
                        r("",              "N. KIERUNKOWY",   "N. TELEFONU"),
                        r("1",              areaNum(0),        phoneNum(0)),
                        r("2",              areaNum(1),        phoneNum(1)))
                .at(aliasOffset(position))
                .containsTable(
                        r("ALIASY"),
                        r("1",              alias(0)),
                        r("2",              alias(1)),
                        r("3",              alias(2)))
                .andNothingMore();
        //@formatter:on
    }

    private CellAddress startingPosition() {
        return config.getStartingPosition();
    }

    private String departmentId() {
        return employee.getDepartmentId();
    }

    private String departmentName() {
        return employee.getDepartmentName();
    }

    private String birthDate() {
        return employee.getBirthDate();
    }

    private String pid() {
        return employee.getPID();
    }

    private String lastName() {
        return employee.getLastName();
    }

    private String firstName() {
        return employee.getFirstName();
    }

    private SurfaceSize expected() {
        return new SurfaceSize(
                expectedWidth(),
                expectedHeight()
        );
    }

    private int expectedHeight() {
        return config.getSheetEntryPadding() * 2 + config.getSpacesBetweenTables() + 8;
    }

    private int expectedWidth() {
        return config.getSheetEntryPadding() * 2 + config.getSpacesBetweenTables() * 4 + 14;
    }

    private CellAddress random() {
        return new CellAddress(random.nextInt(50), random.nextInt(50));
    }

    private String alias(int i) {
        return employee.getAlias(i);
    }

    private String phoneNum(int i) {
        return employee.getPhone(i).getNumber();
    }

    private String areaNum(int i) {
        return employee.getPhone(i).getAreaCode();
    }

    private String cityName(int i) {
        return employee.getAddress(i).getCity().getCityName();
    }

    private String postalCode(int i) {
        return employee.getAddress(i).getCity().getPostalCode();
    }

    private String streetNumber(int i) {
        return String.valueOf(employee.getAddress(i).getStreetNumber());
    }

    private String houseNumber(int i) {
        return String.valueOf(employee.getAddress(i).getHouseNumber());
    }

    private String streetName(int index) {
        return employee.getAddress(index).getStreetName();
    }

    private CellAddress aliasOffset(CellAddress cellAddress) {
        return telNumbersOffset(cellAddress).moveToRight(3 + config.getSpacesBetweenTables());
    }

    private CellAddress telNumbersOffset(CellAddress cellAddress) {
        return cityOffset(cellAddress).moveToRight(3 + config.getSpacesBetweenTables());
    }

    private CellAddress cityOffset(CellAddress cellAddress) {
        return (addressesOffset(cellAddress).moveToRight(4 + config.getSpacesBetweenTables()));
    }

    private CellAddress addressesOffset(CellAddress cellAddress) {
        return cellAddress.moveToRight(2 + config.getSpacesBetweenTables());
    }

    private CellAddress departmentOffset(CellAddress cellAddress) {
        return cellAddress.moveDown(5 + config.getSpacesBetweenTables());
    }

    private CellAddress paddingOffset(CellAddress startingPosition) {
        return startingPosition
                .moveDown(config.getSheetEntryPadding())
                .moveToRight(config.getSheetEntryPadding());
    }

    private Employee employee(int addresses, int phones, int aliases) {
        return new Employee(
                randomName(),
                randomPID(),
                randomBirthDate(),
                randomDepartment(),
                numberOf(randomAddresses(), addresses),
                numberOf(randomPhones(), phones),
                numberOf(randomAliases(), aliases));
    }
}