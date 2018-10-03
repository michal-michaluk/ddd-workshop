package io.dddbyexamples.delivery.planning.plan;

import io.dddbyexamples.delivery.planning.Amounts;

public interface CompletenessPolicy {

    CompletenessPolicy BASIC = new SimpleCompletenessPolicy();

    boolean fulfills(Amounts diff, CompletePlan command);

}
