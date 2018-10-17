package io.dddbyexamples.delivery.planning.plan;

import io.dddbyexamples.delivery.planning.Amounts;
import io.dddbyexamples.delivery.planning.DeliveryEvents;
import io.dddbyexamples.delivery.planning.PlanningCompleted;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class DeliveryPlan {
    private Object id;
    private LocalDate forDate;

    private PlanCompleteness planCompleteness;
    private DemandForecasting forecasting;
    private CompletenessPolicy policy;
    private DeliveryEvents events;

    void close(ClosePlan command) {
        Amounts demandsDiff = planCompleteness.getDiff();
        if (!policy.fulfills(demandsDiff, command)) {
            return;
        }
        Amounts targetDemand = planCompleteness
                .getPlanned()
                .filter(command.decisionToAdjustDemands());
        if (!targetDemand.isEmpty()) {
            forecasting.adjustDemands(forDate, targetDemand);
        }

        Amounts reminder = demandsDiff
                .filter(command.decisionToDeliverDiffNextDay())
                .negative();
        events.emit(new PlanningCompleted(id, forDate, reminder));
    }
}
