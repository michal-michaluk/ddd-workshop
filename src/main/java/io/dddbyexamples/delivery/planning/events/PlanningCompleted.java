package io.dddbyexamples.delivery.planning.events;

import io.dddbyexamples.delivery.planning.Amounts;
import lombok.Value;

@Value
public class PlanningCompleted {
    Object id;
    Amounts reminder;
}
