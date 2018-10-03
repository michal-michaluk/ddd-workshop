package io.dddbyexamples.delivery.planning.delivery;

import io.dddbyexamples.delivery.planning.Amounts;

public class PayloadCapacityPolicyVer1 implements PayloadCapacityPolicy {

    @Override
    public Amounts calculateExceeded(TransportType type, Amounts amounts) {
        long cages = amounts.get("cages");
        long cagesPlaces = cages / 2 + cages % 2;
        long palettes = amounts.get("palette");
        long trailers = amounts.get("trailers");

        if (trailersDelivery(type, trailers)) {
            return trailersVariant(type, cages, palettes, trailers);
        } else {
            return normalVariant(type, cages, cagesPlaces, palettes, trailers);
        }
    }

    private boolean trailersDelivery(TransportType type, long trailers) {
        return type.getCapacity() == 22 && trailers > 0;
    }

    private Amounts normalVariant(TransportType type, long cages, long cagesPlaces, long palettes, long trailers) {
        Amounts exceededAmount = Amounts.empty();
        long exceededPlaces = palettes + cagesPlaces - type.getCapacity();
        if (exceededPlaces > 0) {
            exceededAmount = exceededAmount.sum(Amounts.of("palette", Math.min(exceededPlaces, palettes)));
            exceededAmount = exceededAmount.sum(Amounts.of("cages", Math.min((exceededPlaces - cages % 2) * 2 + cages % 2, cages)));
        }
        exceededAmount = exceededAmount.sum(Amounts.of("trailers", trailers));
        return exceededAmount;
    }

    private Amounts trailersVariant(TransportType type, long cages, long palettes, long trailers) {
        Amounts exceededAmount = Amounts.empty();
        long exceeded = trailers - 10;
        if (cages > 0 || palettes > 0) {
            exceededAmount = exceededAmount.sum(Amounts.of("trailers", trailers));
            exceededAmount = exceededAmount.sum(Amounts.of("palette", palettes));
            exceededAmount = exceededAmount.sum(Amounts.of("cages", cages));
        } else if (exceeded > 0) {
            exceededAmount = exceededAmount.sum(Amounts.of("trailers", Math.min(exceeded, trailers)));
        }
        return exceededAmount;
    }
}
