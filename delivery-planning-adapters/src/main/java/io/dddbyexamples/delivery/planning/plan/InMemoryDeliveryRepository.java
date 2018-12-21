package io.dddbyexamples.delivery.planning.plan;

import java.util.HashMap;
import java.util.Map;

import io.dddbyexamples.delivery.planning.delivery.Delivery;
import io.dddbyexamples.delivery.planning.delivery.DeliveryRepository;

class InMemoryDeliveryRepository implements DeliveryRepository {

    Map<Object, Delivery> deliveries = new HashMap<>();

    @Override
    public Delivery get(Object id) {
        return deliveries.get(id);
    }

    @Override
    public void save(Delivery model) {
        deliveries.put(model.getId(), model);
    }
}
