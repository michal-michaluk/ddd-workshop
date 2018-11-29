package io.dddbyexamples.delivery.planning;

import java.util.UUID;

import io.dddbyexamples.delivery.planning.delivery.*;
import io.dddbyexamples.delivery.planning.plan.ClosePlan;
import io.dddbyexamples.delivery.planning.plan.DeliveryPlan;
import io.dddbyexamples.delivery.planning.plan.PlanRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeliveryPlanningService {

    private final DeliveryFactory factory;
    private final DeliveryRepository deliveries;
    private final PlanRepository plans;

    public UUID scheduleDelivery(ScheduleDelivery command) {
        Delivery model = factory.createBlankDelivery();
        model.editDelivery(
                command.asEditFor(model.getId())
        );
        deliveries.save(model);
        return model.getId();
    }

    public void editDelivery(EditDelivery command) {
        Delivery model = deliveries.get(command.getId());
        model.editDelivery(command);
        deliveries.save(model);
    }

    public void cancelDelivery(CancelDelivery command) {
        Delivery model = deliveries.get(command.getId());
        model.cancelDelivery();
        deliveries.save(model);
    }

    public void closePlan(ClosePlan command) {
        DeliveryPlan model = plans.get(command.getId());
        model.close(command);
        plans.save(model);
    }
}
