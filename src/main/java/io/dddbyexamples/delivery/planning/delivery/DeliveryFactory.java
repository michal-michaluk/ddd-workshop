package io.dddbyexamples.delivery.planning.delivery;

import io.dddbyexamples.delivery.planning.DeliveryEvents;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DeliveryFactory {

    private PayloadCapacityPolicy payloadCapacityPolicy;
    private DeliveryEvents events;

    public Delivery createBlankDelivery() {
        String id = UUID.randomUUID().toString();
        return new Delivery(id, payloadCapacityPolicy, events);
    }
}
