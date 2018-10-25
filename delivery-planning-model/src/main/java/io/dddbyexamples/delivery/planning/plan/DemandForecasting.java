package io.dddbyexamples.delivery.planning.plan;

import io.dddbyexamples.delivery.planning.Amounts;

import java.time.LocalDate;

public interface DemandForecasting {
    void adjustDemands(LocalDate date, Amounts demands);
}
