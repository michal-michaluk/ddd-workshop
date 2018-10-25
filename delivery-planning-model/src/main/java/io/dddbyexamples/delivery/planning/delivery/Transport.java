package io.dddbyexamples.delivery.planning.delivery;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.Optional;

@Value
public class Transport {

    private static final Transport UNSPECIFIED = new Transport(
            LocalDateTime.MIN,
            TransportType.unspecified()
    );

    LocalDateTime time;
    TransportType type;

    public static Transport unspecified() {
        return UNSPECIFIED;
    }

    public boolean isUnspecified() {
        return this == UNSPECIFIED;
    }

    public boolean dateChanged(Transport transport) {
        return Optional.ofNullable(transport)
                .map(t -> time.toLocalDate().equals(
                        t.getTime().toLocalDate()))
                .orElse(true);
    }
}
