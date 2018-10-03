package io.dddbyexamples.demand.forecasting;

import lombok.Value;

import java.time.LocalDate;

@Value
public class DemandedLevelsChanged {
    LocalDate date;
    String refNo;
    long previous;
    long current;
}
