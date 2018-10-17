package io.dddbyexamples.delivery.planning.delivery;

import io.dddbyexamples.delivery.planning.Amounts;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeliveryService {

    private final DeliveryFactory factory;
    private final DeliveryRepository repository;

    public Amounts scheduleDelivery(ScheduleDelivery command) {
        Delivery model = factory.createBlankDelivery();
        Amounts exceeded = model.editDelivery(
                command.asEditFor(model.getId())
        );
        if (exceeded.isEmpty()) {
            repository.save(model);
        }
        return exceeded;
    }

    public Amounts editDelivery(EditDelivery command) {
        Delivery model = repository.get(command.getId());
        Amounts exceeded = model.editDelivery(command);
        repository.save(model);
        return exceeded;
    }

    public void cancelDelivery(CancelDelivery command) {
        Delivery model = repository.get(command.getId());
        model.cancelDelivery();
        repository.save(model);
    }
}
