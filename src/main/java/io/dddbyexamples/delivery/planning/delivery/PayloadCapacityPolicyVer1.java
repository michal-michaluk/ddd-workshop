package io.dddbyexamples.delivery.planning.delivery;

import io.dddbyexamples.delivery.planning.Amounts;

public class PayloadCapacityPolicyVer1 implements PayloadCapacityPolicy {

    @Override
    public Amounts calculateExceeded(TransportType type, Amounts amounts) {
        return Amounts.empty();
    }
}
