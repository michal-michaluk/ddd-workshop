package io.dddbyexamples.delivery.planning.plan;

import io.dddbyexamples.delivery.planning.DeliveredAmountsChanged;
import io.dddbyexamples.delivery.planning.DeliveryEvents;
import io.dddbyexamples.delivery.planning.PlanningCompleted;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class SimpleDefaultEvents implements DeliveryEvents {

    private final InMemoryPlannedAmounts plannedAmounts;

    @Override
    public void emit(DeliveredAmountsChanged event) {
        plannedAmounts.update(event.getDate(), event.getDiff());
    }

    @Override
    public void emit(PlanningCompleted event) {
    }
}
