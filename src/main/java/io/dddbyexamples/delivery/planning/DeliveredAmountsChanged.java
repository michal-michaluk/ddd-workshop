package io.dddbyexamples.delivery.planning;

import lombok.Value;

@Value
public class DeliveredAmountsChanged {
    Object id;
    Amounts diff;
}
