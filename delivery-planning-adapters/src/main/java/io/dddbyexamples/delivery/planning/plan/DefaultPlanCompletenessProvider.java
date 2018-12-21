package io.dddbyexamples.delivery.planning.plan;

import java.time.LocalDate;

import io.dddbyexamples.delivery.planning.Amounts;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class DefaultPlanCompletenessProvider implements PlanCompletenessProvider {

    private final InMemoryPlannedAmounts plannedAmounts;

    @Override
    public PlanCompleteness get(LocalDate date) {
        return new PlanCompleteness(date, plannedAmounts.get(date), Amounts.empty(), Amounts.empty());
    }
}
