package io.dddbyexamples.delivery.planning.plan;

import io.dddbyexamples.delivery.planning.Amounts;

public interface DemandForecasting {
    void changeDemands(Amounts change);
}
