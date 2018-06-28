package io.dddbyexamples.delivery.planning.delivery;

public interface DeliveryRepository {

    Delivery get(String id);

    void save(Delivery model);
}
