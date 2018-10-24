package io.dddbyexamples.delivery.planning.delivery;

import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class DeliveryPayload {
    private String product;
    private int storageUnits;
    private Integer pieces;

    public static Payload create(List<DeliveryPayload> payload, Map<String, StorageUnit> storageUnits) {
        return new Payload(Collections.unmodifiableList(payload.stream()
                .map(p -> new Payload.TransportedProduct(
                        p.getProduct(),
                        p.toPartialStorageUnitOrDefault(storageUnits.get(p.getProduct())),
                        p.getStorageUnits()
                )).collect(Collectors.toList())
        ));
    }

    private StorageUnit toPartialStorageUnitOrDefault(StorageUnit defaultUnit) {
        return pieces == null ? defaultUnit : new StorageUnit(defaultUnit.getType(), pieces);
    }
}
