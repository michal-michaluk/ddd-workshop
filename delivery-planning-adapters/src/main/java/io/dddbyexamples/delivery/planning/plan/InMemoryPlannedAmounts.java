package io.dddbyexamples.delivery.planning.plan;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.dddbyexamples.delivery.planning.Amounts;

class InMemoryPlannedAmounts {

    private final Map<LocalDate, Amounts> amounts = new ConcurrentHashMap<>();

    Amounts get(LocalDate date) {
        return amounts.computeIfAbsent(date, localDate -> Amounts.empty());
    }

    void update(LocalDate date, Amounts diff) {
        amounts.merge(date, diff, Amounts::sum);
    }
}
