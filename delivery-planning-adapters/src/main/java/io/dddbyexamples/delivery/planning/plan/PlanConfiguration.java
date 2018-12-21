package io.dddbyexamples.delivery.planning.plan;

import io.dddbyexamples.delivery.planning.DeliveryPlanningService;
import io.dddbyexamples.delivery.planning.delivery.DeliveryFactory;
import io.dddbyexamples.delivery.planning.delivery.capacity.SimplePayloadCapacityPolicy;

class PlanConfiguration {

    private final InMemoryPlannedAmounts amounts = new InMemoryPlannedAmounts();
    private final DeliveryFactory deliveryFactory = new DeliveryFactory(new SimplePayloadCapacityPolicy(), new SimpleDefaultEvents(amounts));

    DeliveryPlanningService deliveryPlanningService() {
        return new DeliveryPlanningService(deliveryFactory, new InMemoryDeliveryRepository(),

                new InMemoryPlanRepository());
    }

    PlanCompletenessProvider planCompletenessProvider() {
        return new DefaultPlanCompletenessProvider(amounts);
    }

}
