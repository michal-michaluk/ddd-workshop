package io.dddbyexamples.delivery.planning.delivery;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class EditDelivery {
    Object id;
    LocalDateTime time;
    TransportType type;
    Payload payload;
}
