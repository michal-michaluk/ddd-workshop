package io.dddbyexamples.delivery.planning.naiveimpl;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class DeliveryPlan {

    private List<Object> deliveries;

    // Preferences per product:
    // - storage units type
    // - parts amount per storage units

    public Result scheduleDelivery(ScheduleDelivery command) {
        // if Transport capacity is not exceeded
        boolean capacityExceeded = isCapacityExceeded();
        // then add new entry to plan
        // else do nothing

        return new Result(
                capacityExceeded,
                areDemandsFulfilled()
        );
    }

    private boolean isCapacityExceeded() {
        // Transport capacity cannot be exceeded:
        // euro palette - defined for transport type,
        // cages - size like palette, but 2 can be stocked,
        // trailers - 10 in standard 22 palette transport size.

        return false;
    }

    private boolean areDemandsFulfilled() {
        // Plan change recalculates plan completeness by taking into account:
        // - planed deliveries
        // - current demands in parts amount
        // - the remainder (not fulfilled demands for product from last plan)

        return false;
    }

    public List getDeliveries() {
        return deliveries;
    }
}
