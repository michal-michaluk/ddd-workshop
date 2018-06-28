package io.dddbyexamples.delivery.planning.plan;

import io.dddbyexamples.delivery.planning.Amounts;
import io.dddbyexamples.delivery.planning.commands.CompletePlan;

public class SimpleCompletenessPolicy implements CompletenessPolicy {
    @Override
    public boolean fulfills(Amounts diff, CompletePlan command) {
        return diff.allMatch((refNo, amount) -> amount > 0);
    }
}
