package com.basejava.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainHomework12 {
    public static void main(String[] args) {
        MainHomework12 homework12 = new MainHomework12();
        int[] values1 = {3, 2, 3, 3, 2, 1};
        int[] values2 = {9, 8};
        int[] values3 = {7, 6};
        int[] values4 = {9, 6, 7, 5, 8, 1, 4, 3, 2};
        int[] values5 = {2};
        System.out.println(homework12.minValue(values1));
        System.out.println(homework12.minValue(values2));
        System.out.println(homework12.minValue(values3));
        System.out.println(homework12.minValue(values4));
        System.out.println(homework12.minValue(values5));
        List<Integer> list1 = new ArrayList<>(
                Arrays.asList(1, 2, 3, 4, 5)
        );
        List<Integer> list2 = new ArrayList<>(
                Arrays.asList(1, 2, 3, 4, 5, -1)
        );
        System.out.println(homework12.oddOrEven(list1));
        System.out.println(homework12.oddOrEven(list2));
    }

    public int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> a * 10 + b);
    }

    public List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream().reduce(0, Integer::sum) % 2 == 0 ?
                getCollect(integers, p -> p % 2 != 0) :
                getCollect(integers,p -> p % 2 == 0);
    }

    private List<Integer> getCollect(List<Integer> integers, Predicate <Integer> predicate) {
        return integers.stream().filter(predicate).collect(Collectors.toList());
    }
}
