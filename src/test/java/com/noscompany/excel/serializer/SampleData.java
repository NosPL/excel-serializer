package com.noscompany.excel.serializer;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.List.of;
import static java.util.stream.Collectors.toList;

public class SampleData {

    private static final Random random = new Random();

    static List<Employee> samplePeople(int count) {
        return IntStream
                .rangeClosed(1, count)
                .mapToObj(i -> i % 10 == 0 ? null : samplePerson())
                .collect(toList());
    }

    public static Employee samplePerson() {
        return new Employee(
                SomeObject.sample(),
                SomeOtherObject.sample(),
                "Jan",
                "Kowalski",
                null,
                LocalDate.of(1990, 10, 5).toString(),
                30,
                "PL",
                sampleAddresses(),
                samplePhones(),
                sampleAliases());
    }

    private static List<String> sampleAliases() {
        List<String> aliases = new LinkedList<>(of(
                "1 alias", "2 alias", "3 alias", "4 alias", "5 alias",
                "6 alias", "7 alias", "8 alias", "9 alias", "10 alias",
                "11 alias", "12 alias", "13 alias", "14 alias", "15 alias",
                "1 alias", "2 alias", "3 alias", "4 alias", "5 alias",
                "6 alias", "7 alias", "8 alias", "9 alias", "10 alias",
                "11 alias", "12 alias", "13 alias", "14 alias", "15 alias"));
        aliases.add(5, null);
        long i = random.nextInt(aliases.size());
        if (i == aliases.size() - 1)
            return null;
        return aliases.stream().limit(i).collect(toList());
    }

    public static List<Phone> samplePhones() {
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
        phones.add(null);
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
        long i = random.nextInt(phones.size());
        if (i == phones.size() - 1)
            return null;
        return phones.stream().limit(i).collect(toList());
    }

    public static List<Address> sampleAddresses() {
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
        addresses.add(new Address("Kołobrzeska", 1, 2, "10-300", "Warszawa"));
        addresses.add(new Address("Koszalinska", 10, 10, "99-999", "Krakow"));
        addresses.add(new Address("Polczynska", 99, 0, "66-666", "Lobez"));
        addresses.add(new Address("Kolejowa", 66, 66, "11-111", "Szczecin"));
        addresses.add(new Address("Drawska", 49, 5, "78-300", "Swidwin"));
        addresses.add(new Address("Kołobrzeska", 1, 2, "10-300", "Warszawa"));
        addresses.add(null);
        addresses.add(new Address("Koszalinska", 10, 10, "99-999", "Krakow"));
        addresses.add(new Address("Polczynska", 99, 0, "66-666", "Lobez"));
        addresses.add(new Address("Kolejowa", 66, 66, "11-111", "Szczecin"));
        addresses.add(new Address("Drawska", 49, 5, "78-300", "Swidwin"));
        addresses.add(new Address("Kołobrzeska", 1, 2, "10-300", "Warszawa"));
        addresses.add(new Address("Koszalinska", 10, 10, "99-999", "Krakow"));
        addresses.add(new Address("Polczynska", 99, 0, "66-666", "Lobez"));
        addresses.add(new Address("Kolejowa", 66, 66, "11-111", "Szczecin"));
        long i = random.nextInt(addresses.size());
        if (i == addresses.size() - 1)
            return null;
        return addresses.stream().limit(i).collect(toList());
    }
}