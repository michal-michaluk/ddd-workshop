package io.dddbyexamples.delivery.planning.plan;

import io.dddbyexamples.delivery.planning.Amounts;
import io.dddbyexamples.delivery.planning.DeliveredAmountsChanged;
import io.dddbyexamples.demand.forecasting.DemandedLevelsChanged;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class PlanCompleteness {
    private LocalDate date;
    private Amounts planned;
    private Amounts demanded;
    private Amounts yesterdaysReminder;

    public Amounts getDiff() {
        return planned.diff(demanded.sum(yesterdaysReminder));
    }

    public void apply(DeliveredAmountsChanged event) {
        planned = planned.sum(event.getDiff());
    }

    public void apply(DemandedLevelsChanged event) {
        if (!event.getDate().equals(date)) {
            return;
        }
        Amounts diff = Amounts.of(
                event.getRefNo(),
                event.getCurrent() - event.getPrevious()
        );
        demanded = demanded.sum(diff);
    }

}
