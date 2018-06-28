package io.dddbyexamples.delivery.planning.events;

import lombok.Value;

import java.time.LocalDate;

@Value
public class DemandedLevelsChanged {
    LocalDate date;
    String refNo;
    long previous;
    long current;
}
