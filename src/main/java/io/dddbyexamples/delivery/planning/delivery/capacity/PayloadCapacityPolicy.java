package io.dddbyexamples.delivery.planning.delivery.capacity;

import io.dddbyexamples.delivery.planning.delivery.TransportType;

public interface PayloadCapacityPolicy {

    StorageUnitsAmount calculateExceeded(TransportType type, StorageUnitsAmount amounts);

}
