package io.dddbyexamples.delivery.planning.delivery;

import lombok.Value;

@Value
public class EditDelivery {
    Object id;
    Transport transport;
    Payload payload;
}
