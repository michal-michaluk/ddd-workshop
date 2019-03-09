package io.dddbyexamples.delivery.planning.plan;

import lombok.Value;

import java.util.Set;

@Value
public class ClosePlan {
    Object id;
    Set<String> remainderForRefNos;
    Set<String> adjustDemandForRefNos;

    public Set<String> decisionToDeliverDiffNextDay() {
        return remainderForRefNos;
    }

    public Set<String> decisionToAdjustDemands() {
        return adjustDemandForRefNos;
    }
}
