package io.dddbyexamples.delivery.planning.delivery.capacity;

import io.dddbyexamples.delivery.planning.delivery.TransportType;

public class PayloadCapacityPolicyVer1 implements PayloadCapacityPolicy {

    private final TrailersCapacityPolicy trailersVariant = new TrailersCapacityPolicy();
    private final PalettePlacesCapacityPolicy palettePlacesVariant = new PalettePlacesCapacityPolicy();

    @Override
    public StorageUnitsAmount calculateExceeded(TransportType type, StorageUnitsAmount amounts) {
        if (trailersVariant.isTrailersDelivery(type, amounts)) {
            return trailersVariant.calculateExceeded(type, amounts);
        } else {
            return palettePlacesVariant.calculateExceeded(type, amounts);
        }
    }

}
