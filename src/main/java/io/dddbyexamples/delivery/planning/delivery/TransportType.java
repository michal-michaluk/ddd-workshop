package io.dddbyexamples.delivery.planning.delivery;

import lombok.Value;

@Value
public class TransportType {
    String type;
    int capacity;

    public static TransportType unspecified() {
        return new TransportType("<unspecified>", 0);
    }
}
