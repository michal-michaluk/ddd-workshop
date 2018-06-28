package io.dddbyexamples.delivery.planning.plan;

public interface PlanRepository {

    DeliveryPlan get(String id);

    void save(DeliveryPlan model);
}
