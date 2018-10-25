package io.dddbyexamples.delivery.planning;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.LongBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Amounts {

    private static final Amounts EMPTY = new Amounts(Collections.emptyMap());

    private final Map<String, Long> amounts;

    public static Amounts empty() {
        return EMPTY;
    }

    public static Amounts of(String key, Long amount) {
        return new Amounts(Collections.singletonMap(key, amount));
    }

    public static Amounts calculate(Amounts left, Amounts right, LongBinaryOperator calculation) {
        return new Amounts(Stream.concat(left.amounts.keySet().stream(), right.amounts.keySet().stream())
                .distinct()
                .collect(Collectors.toMap(
                        Function.identity(),
                        refNo -> calculation.applyAsLong(left.get(refNo), right.get(refNo))
                )));
    }

    public Amounts(Map<String, Long> amounts) {
        this.amounts = Collections.unmodifiableMap(amounts.entrySet().stream()
                .filter(e -> e.getValue() != 0L)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ))
        );
    }

    public long get(String key) {
        return amounts.getOrDefault(key, 0L);
    }

    public Amounts with(String key, long amount) {
        HashMap<String, Long> newAmounts = new HashMap<>(amounts);
        newAmounts.put(key, amount);
        return new Amounts(newAmounts);
    }

    public boolean isEmpty() {
        return amounts.isEmpty();
    }

    public Amounts sum(Amounts other) {
        return calculate(this, other, (left, right) -> left + right);
    }

    public Amounts diff(Amounts other) {
        return calculate(this, other, (left, right) -> left - right);
    }

    public Amounts negative() {
        return empty().diff(this);
    }

    public Amounts filter(Set<String> keys) {
        return new Amounts(amounts.entrySet().stream()
                .filter(o -> keys.contains(o.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                )));
    }

    public Amounts filterOut(Set<String> exclude) {
        return new Amounts(amounts.entrySet().stream()
                .filter(o -> !exclude.contains(o.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                )));
    }

    public boolean allMatch(BiPredicate<String, Long> predicate) {
        return amounts.entrySet().stream()
                .allMatch(e -> predicate.test(e.getKey(), e.getValue()));
    }

    public long sum() {
        return amounts.values().stream().mapToLong(value -> value).sum();
    }

    @Override
    public String toString() {
        return "Amounts{" + amounts + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amounts amounts1 = (Amounts) o;
        return Objects.equals(amounts, amounts1.amounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amounts);
    }
}
