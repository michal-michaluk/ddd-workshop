package io.dddbyexamples.delivery.planning;

import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
public class DeliveredAmountsChanged {
    Object id;
    LocalDateTime time;
    Amounts diff;

    public LocalDate getDate() {
        return time.toLocalDate();
    }
}
