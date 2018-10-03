package io.dddbyexamples.delivery.planning.delivery;

public interface DeliveryRepository {

    Delivery get(Object id);

    void save(Delivery model);
}
