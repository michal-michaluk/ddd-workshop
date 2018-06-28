package io.dddbyexamples.delivery.planning.events;

import io.dddbyexamples.delivery.planning.Amounts;
import lombok.Value;

@Value
public class DeliveryChanged {
    Object id;
    Amounts diff;
}
