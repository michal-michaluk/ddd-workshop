package io.dddbyexamples.delivery.planning.delivery.capacity;

import io.dddbyexamples.delivery.planning.delivery.TransportType;

public class SimplePayloadCapacityPolicy implements PayloadCapacityPolicy {

    @Override
    public StorageUnitsAmount calculateExceeded(TransportType type, StorageUnitsAmount amounts) {
        long exceeded = Math.max(amounts.sum() - type.getCapacity(), 0);
        return StorageUnitsAmount.builder()
                .addPalettes(exceeded)
                .build();
    }
}
