package io.dddbyexamples.delivery.planning.naiveimpl;

import lombok.Value;

@Value
public class TransportedProduct {
    String product;
    int storageUnits;
}
