package io.dddbyexamples.delivery.planning.plan;

import lombok.Value;

import java.util.Set;

@Value
public class ClosePlan {
    Set<String> reminderForRefNos;
    Set<String> adjustDemandForRefNos;

    public Set<String> decisionToDeliverDiffNextDay() {
        return reminderForRefNos;
    }

    public Set<String> decisionToAdjustDemands() {
        return adjustDemandForRefNos;
    }
}
