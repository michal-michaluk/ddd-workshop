package io.dddbyexamples.delivery.planning.delivery.capacity;

public class CagesPlacesCalculation {

    public static long calculateCagesPlaces(long cagesAmount) {
        return cagesAmount / 2 + cagesAmount % 2;
    }

    public static long calculateCagesAmountToFreePlaces(long cagesAmount, long placesToFree) {
        return Math.min((placesToFree - cagesAmount % 2) * 2 + cagesAmount % 2, cagesAmount);
    }

    private CagesPlacesCalculation() {
    }
}
