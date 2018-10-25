package io.dddbyexamples.delivery.planning.delivery;

import io.dddbyexamples.delivery.planning.Amounts;

public class PayloadCapacityExceeded extends RuntimeException {
    private Amounts exceeded;

    public PayloadCapacityExceeded(Amounts exceeded) {
        this.exceeded = exceeded;
    }

    public Amounts getExceeded() {
        return exceeded;
    }
}
