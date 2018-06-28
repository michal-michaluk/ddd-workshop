package io.dddbyexamples.delivery.planning.delivery;

import io.dddbyexamples.delivery.planning.Amounts;
import lombok.Value;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Payload {
    private final List<TransportedProduct> payload;

    public static Payload empty() {
        return new Payload(Collections.emptyList());
    }

    public static Payload create(Map<String, Integer> productAmounts, Map<String, StorageUnit> storageUnits) {
        return new Payload(Collections.unmodifiableList(productAmounts.entrySet().stream()
                .map(e -> new TransportedProduct(
                        e.getKey(),
                        storageUnits.get(e.getKey()),
                        e.getValue()
                )).collect(Collectors.toList())
        ));
    }

    private Payload(List<TransportedProduct> payload) {
        this.payload = payload;
    }

    public Amounts amountOfProducts() {
        Map<String, Long> amountOfProducts = payload.stream()
                .collect(Collectors.toMap(
                        TransportedProduct::getRefNo,
                        TransportedProduct::getProductAmount
                ));
        return new Amounts(amountOfProducts);
    }

    public Amounts amountOfUnitTypes() {
        Map<String, Long> amountOfUnits = payload.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getUnit().getType(),
                        Collectors.summingLong(TransportedProduct::getUnitAmount)
                ));
        return new Amounts(amountOfUnits);
    }

    @Value
    static class TransportedProduct {
        String refNo;
        StorageUnit unit;
        int unitAmount;

        long getProductAmount() {
            return unitAmount * unit.getAmount();
        }
    }
}
