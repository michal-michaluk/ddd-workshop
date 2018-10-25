package io.dddbyexamples.delivery.planning.delivery;

import io.dddbyexamples.delivery.planning.Amounts;
import io.dddbyexamples.delivery.planning.DeliveredAmountsChanged;
import io.dddbyexamples.delivery.planning.DeliveryEvents;
import io.dddbyexamples.delivery.planning.delivery.capacity.PayloadCapacityPolicy;
import io.dddbyexamples.delivery.planning.delivery.capacity.StorageUnitsAmount;

import java.util.UUID;

public class Delivery {
    private UUID id;
    private Transport transport;
    private Payload payload;

    private PayloadCapacityPolicy policy;
    private DeliveryEvents events;

    public Delivery(UUID id,
                    PayloadCapacityPolicy policy,
                    DeliveryEvents events) {
        this.id = id;
        this.policy = policy;
        this.events = events;
        transport = Transport.unspecified();
        payload = Payload.empty();
    }

    public void editDelivery(EditDelivery command) {
        StorageUnitsAmount exceeded = policy.calculateExceeded(
                command.getTransport().getType(),
                command.getPayload().amountOfUnitTypes()
        );
        payloadCapacityNotExceeded(exceeded);

        Amounts diff = command.getPayload().amountOfProducts()
                .diff(this.payload.amountOfProducts());

        this.transport = command.getTransport();
        this.payload = command.getPayload();

        if (!diff.isEmpty()) {
            events.emit(new DeliveredAmountsChanged(id, transport, diff));
        }
    }

    public void cancelDelivery() {
        Amounts diff = this.payload.amountOfProducts().negative();
        Transport transport = this.transport;
        this.transport = Transport.unspecified();
        this.payload = Payload.empty();
        events.emit(new DeliveredAmountsChanged(id, transport, diff));
    }

    public UUID getId() {
        return id;
    }

    private void payloadCapacityNotExceeded(Amounts exceeded) {
        if (!exceeded.isEmpty()) {
            throw new PayloadCapacityExceeded(exceeded);
        }
    }
}
