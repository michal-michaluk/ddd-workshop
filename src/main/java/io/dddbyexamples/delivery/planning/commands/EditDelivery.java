package io.dddbyexamples.delivery.planning.commands;

import io.dddbyexamples.delivery.planning.delivery.Payload;
import io.dddbyexamples.delivery.planning.delivery.TransportType;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class EditDelivery {
    Object id;
    LocalDateTime time;
    TransportType type;
    Payload payload;
}
