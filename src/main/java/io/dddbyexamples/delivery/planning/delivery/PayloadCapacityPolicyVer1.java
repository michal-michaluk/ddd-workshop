package io.dddbyexamples.delivery.planning.delivery;

import io.dddbyexamples.delivery.planning.Amounts;

public class PayloadCapacityPolicyVer1 implements PayloadCapacityPolicy {

    @Override
    public Amounts calculateExceeded(TransportType type, Amounts amounts) {
        long exceeded = amounts.get("palette") - type.getCapacity();
        if (exceeded < 0) {
            return Amounts.empty();
        } else {
            return Amounts.of("palette", exceeded);
        }
    }
}
