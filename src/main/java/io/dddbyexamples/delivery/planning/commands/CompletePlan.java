package io.dddbyexamples.delivery.planning.commands;

import lombok.Value;

import java.util.Set;

@Value
public class CompletePlan {
    Set<String> reminder;
    Set<String> reduction;

    public Set<String> decisionToDeliverDiffNextDay() {
        return reminder;
    }

    public Set<String> decisionToChangeDemands() {
        return reduction;
    }
}
