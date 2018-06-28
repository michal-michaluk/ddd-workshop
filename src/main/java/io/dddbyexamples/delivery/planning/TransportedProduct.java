package io.dddbyexamples.delivery.planning;

import lombok.Value;

@Value
public class TransportedProduct {
    String product;
    int storageUnits;
}
