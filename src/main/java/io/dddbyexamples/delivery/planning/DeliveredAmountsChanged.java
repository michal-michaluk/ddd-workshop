package io.dddbyexamples.delivery.planning;

import io.dddbyexamples.delivery.planning.delivery.Transport;
import lombok.Value;

import java.time.LocalDate;

@Value
public class DeliveredAmountsChanged {
    Object id;
    Transport transport;
    Amounts diff;

    public LocalDate getDate() {
        return transport.getTime().toLocalDate();
    }
}
