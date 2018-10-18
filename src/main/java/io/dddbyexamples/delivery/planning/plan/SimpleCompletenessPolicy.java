package io.dddbyexamples.delivery.planning.plan;

import io.dddbyexamples.delivery.planning.Amounts;

public class SimpleCompletenessPolicy implements CompletenessPolicy {
    @Override
    public boolean planMatchesDemand(Amounts diff, ClosePlan command) {
        return diff.filterOut(command.decisionToAdjustDemands())
                .filterOut(command.decisionToDeliverDiffNextDay())
                .allMatch((refNo, amount) -> amount > 0);
    }
}
