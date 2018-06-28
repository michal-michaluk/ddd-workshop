package io.dddbyexamples.delivery.planning.delivery;

import io.dddbyexamples.delivery.planning.Amounts;
import io.dddbyexamples.delivery.planning.commands.EditDelivery;
import io.dddbyexamples.delivery.planning.events.DeliveryChanged;
import io.dddbyexamples.delivery.planning.events.DeliveryEvents;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

public class Delivery {
    private String id;
    private LocalDateTime time;
    private TransportType type;
    private Payload payload;

    private CapacityPolicy capacityPolicy;
    private DeliveryEvents events;

    public Delivery(String id, CapacityPolicy capacityPolicy, DeliveryEvents events) {
        this.id = id;
        this.capacityPolicy = capacityPolicy;
        this.events = events;
        time = LocalDateTime.MIN;
        type = TransportType.unspecified();
        payload = Payload.empty();
    }

    public Amounts editDelivery(EditDelivery command) {
        Amounts exceeded = capacityPolicy.calculateExceeded(
                command.getType(),
                command.getPayload().amountOfUnitTypes()
        );
        if (capacity(exceeded)) {
            return exceeded;
        }

        Amounts diff = command.getPayload().amountOfProducts()
                .diff(this.payload.amountOfProducts());

        this.time = command.getTime();
        this.type = command.getType();
        this.payload = command.getPayload();

        if (!diff.isEmpty()) {
            events.emit(new DeliveryChanged(id, diff));
        }
        return Amounts.empty();
    }

    public void cancelDelivery() {
        Amounts diff = this.payload.amountOfProducts().negative();
        this.type = TransportType.unspecified();
        this.payload = Payload.empty();
        events.emit(new DeliveryChanged(id, diff));
    }

    private boolean capacity(Amounts exceeded) {
        return !exceeded.isEmpty();
    }
}
