package com.noscompany.excel.converter;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class SampleData {
    static List<Employee> samplePeople(int count) {
        return IntStream.rangeClosed(1, count)
                .mapToObj(i -> samplePerson())
                .collect(toList());
    }

    public static Employee samplePerson() {
        return new Employee(
                "Jan",
                "Kowalski",
                "111222333444",
                30,
                LocalDate.of(1987, 10, 5).toString(),
                180,
                85,
                "kawaler",
                "PL",
                sampleAddresses(),
                samplePhones(),
                sampleAliases());
    }

    private static List<String> sampleAliases() {
        List<String> aliases = List.of("1 alias", "2 alias", "3 alias", "4 alias");
        long i = new Random().nextInt(aliases.size());
        return aliases.stream().limit(i).collect(toList());
    }

    public static List<Phone> samplePhones() {
        List<Phone> phones = new LinkedList<Phone>();
        phones.add(new Phone("0049", "789 111 111"));
        phones.add(new Phone("0048", "699 222 222"));
        phones.add(new Phone("0048", "888 888 888"));
        phones.add(new Phone("0048", "999 999 999"));
        long i = new Random().nextInt(phones.size());
        return phones.stream().limit(i).collect(toList());
    }

    public static List<Address> sampleAddresses() {
        List<Address> addresses = new LinkedList<Address>();
        addresses.add(new Address("Drawska", 49, 5, "78-300", "Swidwin"));
        addresses.add(new Address("Kołobrzeska", 1, 2, "10-300", "Warszawa"));
        addresses.add(new Address("Koszalinska", 10, 10, "99-999", "Krakow"));
        addresses.add(new Address("Polczynska", 99, 0, "66-666", "Lobez"));
        addresses.add(new Address("Kolejowa", 66, 66, "11-111", "Szczecin"));
        long i = new Random().nextInt(addresses.size());
        return addresses.stream().limit(i).collect(toList());
    }
}