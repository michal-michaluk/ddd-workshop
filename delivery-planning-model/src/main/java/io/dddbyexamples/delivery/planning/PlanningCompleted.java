package io.dddbyexamples.delivery.planning;

import lombok.Value;

import java.time.LocalDate;

@Value
public class PlanningCompleted {
    Object id;
    LocalDate date;
    Amounts reminder;
}
