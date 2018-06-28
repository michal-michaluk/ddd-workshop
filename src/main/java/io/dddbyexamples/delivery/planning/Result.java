package io.dddbyexamples.delivery.planning;

import lombok.Value;

@Value
class Result {

    boolean capacityIsExceeded;
    boolean demandsAreFulfilled;
}
