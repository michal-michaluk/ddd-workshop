package io.dddbyexamples.delivery.planning.plan;

import io.dddbyexamples.delivery.planning.Amounts;

public interface CompletenessPolicy {

    boolean planMatchesDemand(Amounts diff, ClosePlan command);

}
