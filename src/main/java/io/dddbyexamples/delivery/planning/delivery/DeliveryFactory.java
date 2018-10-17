package io.dddbyexamples.delivery.planning.delivery;

import io.dddbyexamples.delivery.planning.DeliveryEvents;
import io.dddbyexamples.delivery.planning.delivery.capacity.PayloadCapacityPolicy;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DeliveryFactory {

    private PayloadCapacityPolicy payloadCapacityPolicy;
    private DeliveryEvents events;

    public Delivery createBlankDelivery() {
        return new Delivery(UUID.randomUUID(), payloadCapacityPolicy, events);
    }
}
