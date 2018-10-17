package io.dddbyexamples.delivery.planning.delivery.capacity;

import io.dddbyexamples.delivery.planning.delivery.TransportType;

class PalettePlacesCapacityPolicy implements PayloadCapacityPolicy {

    public StorageUnitsAmount calculateExceeded(TransportType type, StorageUnitsAmount amounts) {
        StorageUnitsAmount.Builder exceededAmount = StorageUnitsAmount.builder()
                .addTrailers(amounts.getTrailers());

        long exceededPlaces = amounts.getPalette()
                + CagesPlacesCalculation.calculateCagesPlaces(amounts.getCages())
                - type.getCapacity();
        if (exceededPlaces > 0) {
            exceededAmount.addPalettes(exceededPlaces, amounts.getPalette());
            exceededAmount.addCages(CagesPlacesCalculation.calculateCagesAmountToFreePlaces(amounts.getCages(), exceededPlaces));
        }
        return exceededAmount.build();
    }
}
