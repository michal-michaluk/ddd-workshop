package io.dddbyexamples.delivery.planning.plan

import io.dddbyexamples.delivery.planning.delivery.Payload
import io.dddbyexamples.delivery.planning.delivery.StorageUnit
import io.dddbyexamples.delivery.planning.delivery.Transport
import io.dddbyexamples.delivery.planning.delivery.TransportType

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class SampleDeliveryData {
    static date = LocalDate.now()
    static String refNo = "refNo"
    static Transport transport = new Transport(LocalDateTime.of(date, LocalTime.MIDNIGHT), new TransportType("palette", 22))
    static Payload scheduledPayload = new Payload(Collections.singletonList(new Payload.TransportedProduct(refNo, new StorageUnit(refNo, 10), 1)))
    static Payload editedPayload = new Payload(Collections.singletonList(new Payload.TransportedProduct(refNo, new StorageUnit(refNo, 20), 1)))
}
