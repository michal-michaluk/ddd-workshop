package io.dddbyexamples.delivery.planning.naiveimpl;

import lombok.Value;

@Value
public class Result {

    boolean capacityIsExceeded;
    boolean demandsAreFulfilled;
}
