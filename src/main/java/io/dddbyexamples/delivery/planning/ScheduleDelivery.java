package io.dddbyexamples.delivery.planning;

import lombok.Value;

import java.util.List;

@Value
public class ScheduleDelivery {
    String time;
    int transportCapacity;
    List<TransportedProduct> content;
}
