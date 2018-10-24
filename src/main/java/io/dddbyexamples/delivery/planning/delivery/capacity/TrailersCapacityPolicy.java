package io.dddbyexamples.delivery.planning.delivery.capacity;

import io.dddbyexamples.delivery.planning.delivery.TransportType;

class TrailersCapacityPolicy implements PayloadCapacityPolicy {

    public boolean isTrailersDelivery(TransportType type, StorageUnitsAmount trailers) {
        return type.getCapacity() == 22 && trailers.getTrailers() > 0;
    }

    @Override
    public StorageUnitsAmount calculateExceeded(TransportType type, StorageUnitsAmount amounts) {
        long palettes = amounts.getPallets();
        long trailers = amounts.getTrailers();
        long cages = amounts.getCages();

        long exceeded = trailers - 10;
        if (cages > 0 || palettes > 0) {
            return amounts;
        } else if (exceeded > 0) {
            return StorageUnitsAmount.builder()
                    .addTrailers(exceeded, trailers)
                    .build();
        }
        return StorageUnitsAmount.empty();
    }
}
