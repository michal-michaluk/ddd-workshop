package io.dddbyexamples.delivery.planning.delivery;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ScheduleDelivery {
    LocalDateTime time;
    TransportType type;
    Payload payload;

    public EditDelivery asEditFor(Object id) {
        return new EditDelivery(id, time, type, payload);
    }
}
