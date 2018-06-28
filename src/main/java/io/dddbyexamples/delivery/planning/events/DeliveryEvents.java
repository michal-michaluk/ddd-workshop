package io.dddbyexamples.delivery.planning.events;

public interface DeliveryEvents {
    void emit(DeliveryChanged event);

    void emit(PlanningCompleted event);
}
