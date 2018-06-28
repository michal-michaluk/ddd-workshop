package io.dddbyexamples.delivery.planning.delivery;

import io.dddbyexamples.delivery.planning.Amounts;
import io.dddbyexamples.delivery.planning.commands.EditDelivery;
import io.dddbyexamples.delivery.planning.events.DeliveryEvents;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class DeliveryFactory {

    private CapacityPolicy capacityPolicy;
    private DeliveryEvents events;

    public Optional<Delivery> scheduleDelivery(
            LocalDateTime time,
            TransportType type,
            Payload payload) {

        String id = UUID.randomUUID().toString();
        Delivery delivery = new Delivery(
                id,
                capacityPolicy, events
        );
        Amounts exceeded = delivery.editDelivery(new EditDelivery(id, time, type, payload));

        if (exceeded.isEmpty()) {
            return Optional.of(delivery);
        } else {
            return Optional.empty();
        }

    }
}
