package com.noscompany.excel.serializer.data;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.List.of;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;

public class SampleData {

    public static final Random random = new Random();

    public static List<Employee> randomEmployees(int count) {
        return IntStream
                .rangeClosed(1, count)
                .mapToObj(i -> randomEmployee())
                .collect(toList());
    }

    public static Employee randomEmployee() {
        return new Employee(
                randomName(),
                randomPID(),
                randomBirthDate(),
                randomDepartment(),
                randomNumberOf(randomAddresses()),
                randomNumberOf(randomPhones()),
                randomNumberOf(randomAliases())
        );
    }

    public static Department randomDepartment() {
        return new Department(
                shuffle(new LinkedList<>(of("księgowość", "ochrona", "produkcja"))).get(0),
                randomUUID().toString().substring(0, 5));
    }

    public static Name randomName() {
        return new Name(randomFirstName(), randomLastName());
    }

    public static String randomFirstName() {
        return shuffle(new LinkedList<>(of("Jan", "Krzysztof", "Pawel"))).get(0);
    }

    public static String randomLastName() {
        return shuffle(new LinkedList<>(of("Marecki", "Kowalski", "Nowak"))).get(0);
    }

    public static String randomPID() {
        return shuffle(new LinkedList<>(of("11111111111", "22222222222", "33333333333"))).get(0);
    }

    public static String randomBirthDate() {
        return shuffle(new LinkedList<>(of(
                LocalDate.of(1980, 1, 1).toString(),
                LocalDate.of(1990, 5, 20).toString(),
                LocalDate.of(2000, 10, 30).toString()
        ))).get(0);
    }

    public static List<String> randomAliases() {
        return of(
                "1 alias", "2 alias", "3 alias", "4 alias", "5 alias",
                "6 alias", "7 alias", "8 alias", "9 alias", "10 alias",
                "11 alias", "12 alias", "13 alias", "14 alias", "15 alias",
                "1 alias", "2 alias", "3 alias", "4 alias", "5 alias",
                "6 alias", "7 alias", "8 alias", "9 alias", "10 alias");
    }

    public static List<Phone> randomPhones() {
        List<Phone> phones = new LinkedList<>();
        phones.add(new Phone("0049", "789 111 111"));
        phones.add(new Phone("0048", "699 222 222"));
        phones.add(new Phone("0048", "888 888 888"));
        phones.add(new Phone("0048", "999 999 999"));
        phones.add(new Phone("0049", "789 111 111"));
        phones.add(new Phone("0048", "699 222 222"));
        phones.add(new Phone("0048", "888 888 888"));
        phones.add(new Phone("0048", "999 999 999"));
        phones.add(new Phone("0049", "789 111 111"));
        phones.add(new Phone("0048", "699 222 222"));
        phones.add(new Phone("0048", "888 888 888"));
        phones.add(new Phone("0048", "999 999 999"));
        phones.add(new Phone("0049", "789 111 111"));
        phones.add(new Phone("0048", "699 222 222"));
        phones.add(new Phone("0048", "888 888 888"));
        phones.add(new Phone("0048", "999 999 999"));
        phones.add(new Phone("0049", "789 111 111"));
        return phones;
    }

    public static List<Address> randomAddresses() {
        List<Address> addresses = new LinkedList<>();
        addresses.add(new Address("Drawska", 49, 5, "78-300", "Swidwin"));
        addresses.add(new Address("Kołobrzeska", 1, 2, "10-300", "Warszawa"));
        addresses.add(new Address("Koszalinska", 10, 10, "99-999", "Krakow"));
        addresses.add(new Address("Polczynska", 99, 0, "66-666", "Lobez"));
        addresses.add(new Address("Kolejowa", 66, 66, "11-111", "Szczecin"));
        addresses.add(new Address("Drawska", 49, 5, "78-300", "Swidwin"));
        addresses.add(new Address("Kołobrzeska", 1, 2, "10-300", "Warszawa"));
        addresses.add(new Address("Koszalinska", 10, 10, "99-999", "Krakow"));
        addresses.add(new Address("Polczynska", 99, 0, "66-666", "Lobez"));
        addresses.add(new Address("Kolejowa", 66, 66, "11-111", "Szczecin"));
        addresses.add(new Address("Drawska", 49, 5, "78-300", "Swidwin"));
        addresses.add(new Address("Kołobrzeska", 1, 2, "10-300", "Warszawa"));
        addresses.add(new Address("Koszalinska", 10, 10, "99-999", "Krakow"));
        addresses.add(new Address("Polczynska", 99, 0, "66-666", "Lobez"));
        addresses.add(new Address("Kolejowa", 66, 66, "11-111", "Szczecin"));
        addresses.add(new Address("Drawska", 49, 5, "78-300", "Swidwin"));
        return addresses;
    }

    public static <T> List<T> numberOf(List<T> list, int count) {
        while (list.size() < count)
            list.addAll(list);
        return list.stream().limit(count).collect(toList());
    }

    public static <T> List<T> randomNumberOf(List<T> list) {
        long i = random.nextInt(12);
        return list.stream().limit(Math.max(2, i)).collect(toList());
    }

    public static <T> List<T> shuffle(List<T> list) {
        Collections.shuffle(list);
        return list;
    }
}