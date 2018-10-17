package io.dddbyexamples.delivery.planning.plan;

import io.dddbyexamples.delivery.planning.Amounts;

public interface CompletenessPolicy {

    boolean fulfills(Amounts diff, ClosePlan command);

}
