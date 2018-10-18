package io.dddbyexamples.delivery.planning.plan;

public interface PlanRepository {

    DeliveryPlan get(Object id);

    void save(DeliveryPlan model);
}
