package io.dddbyexamples.delivery.planning.notification;

import io.dddbyexamples.delivery.planning.PlanningCompleted;
import io.dddbyexamples.delivery.planning.plan.PlanCompleteness;
import io.dddbyexamples.delivery.planning.plan.PlanCompletenessProvider;
import io.dddbyexamples.demand.forecasting.DemandedLevelsChanged;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
public class LateDemandChangesNotification {

    // state
    private final Set<LocalDate> completed;

    // dependencies
    private final Notification notification;
    private final PlanCompletenessProvider completeness;

    public void apply(PlanningCompleted event) {
        completed.add(event.getDate());
    }

    public void apply(DemandedLevelsChanged event) {
        if (completed.contains(event.getDate())) {
            PlanCompleteness planCompleteness = this.completeness.get(event.getDate());
            if (planCompleteness.anyMissing()) {
                notification.sendNotificationAboutLateDemandChanges(
                        event,
                        planCompleteness.getDiff()
                );
            }
        }
    }
}
