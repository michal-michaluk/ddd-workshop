package io.dddbyexamples.delivery.planning.delivery;

import io.dddbyexamples.delivery.planning.Amounts;
import io.dddbyexamples.delivery.planning.delivery.capacity.StorageUnitsAmount;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Payload {
    private final List<TransportedProduct> payload;

    public static Payload empty() {
        return new Payload(Collections.emptyList());
    }

    public Amounts amountOfProducts() {
        Map<String, Long> amountOfProducts = payload.stream()
                .collect(Collectors.groupingBy(
                        TransportedProduct::getRefNo,
                        Collectors.summingLong(TransportedProduct::getProductAmount)
                ));
        return new Amounts(amountOfProducts);
    }

    public StorageUnitsAmount amountOfUnitTypes() {
        Map<String, Long> amountOfUnits = payload.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getUnit().getType(),
                        Collectors.summingLong(TransportedProduct::getUnitAmount)
                ));
        return new StorageUnitsAmount(amountOfUnits);
    }

    @Value
    public static class TransportedProduct {
        String refNo;
        StorageUnit unit;
        int unitAmount;

        long getProductAmount() {
            return unitAmount * unit.getAmount();
        }
    }
}
