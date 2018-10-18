package io.dddbyexamples.delivery.planning.plan;

import io.dddbyexamples.delivery.planning.Amounts;

public class PlanNotMatchesDemand extends RuntimeException {
    private final Amounts demandsDiff;
    private final ClosePlan command;

    public PlanNotMatchesDemand(Amounts demandsDiff, ClosePlan command) {
        this.demandsDiff = demandsDiff;
        this.command = command;
    }
}
