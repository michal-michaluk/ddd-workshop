package io.dddbyexamples.delivery.planning.delivery;

import lombok.Value;

@Value
public class ScheduleDelivery {
    Transport transport;
    Payload payload;

    public EditDelivery asEditFor(Object id) {
        return new EditDelivery(id, transport, payload);
    }
}
