package io.dddbyexamples.delivery.planning;

import lombok.Value;

@Value
public class PlanningCompleted {
    Object id;
    Amounts reminder;
}
