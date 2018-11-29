package io.dddbyexamples.delivery.planning.plan

import io.dddbyexamples.delivery.planning.Amounts
import io.dddbyexamples.delivery.planning.DeliveryPlanningService
import io.dddbyexamples.delivery.planning.delivery.*
import io.dddbyexamples.delivery.planning.delivery.capacity.SimplePayloadCapacityPolicy
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class PlanCompletenessSpec extends Specification {

    private String refNo = "refNo"

    private InMemoryPlannedAmounts amounts = new InMemoryPlannedAmounts()
    PlanCompletenessProvider planCompletenessProvider = new DefaultPlanCompletenessProvider(amounts)


    DeliveryFactory deliveryFactory = new DeliveryFactory(new SimplePayloadCapacityPolicy(), new SimpleDefaultEvents(amounts))

    DeliveryPlanningService deliveryPlanningService = new DeliveryPlanningService(deliveryFactory, new DeliveryRepository() {

        Map<Object, Delivery> deliveries = new HashMap<>()

        @Override
        Delivery get(Object id) {
            return deliveries.get(id);
        }

        @Override
        void save(Delivery model) {
            deliveries.put(model.getId(), model)
        }
    },

            new PlanRepository() {
                @Override
                DeliveryPlan get(Object id) {
                    return null
                }

                @Override
                void save(DeliveryPlan model) {

                }
            })

    private date = LocalDate.now()

    Transport transport = new Transport(LocalDateTime.of(date, LocalTime.MIDNIGHT), new TransportType("palette", 22))
    Payload payload = new Payload(Collections.singletonList(new Payload.TransportedProduct(refNo, new StorageUnit(refNo, 10), 1)))
    Payload editedPayload = new Payload(Collections.singletonList(new Payload.TransportedProduct(refNo, new StorageUnit(refNo, 20), 1)))
    private Amounts scheduledAmounts = Amounts.of(refNo, 10)

    def 'plan completeness should contain proper plannned amount'() {
        when: 'logistician schedules the delivery'
            ScheduleDelivery command = new ScheduleDelivery(transport, payload)
            UUID uuid = deliveryPlanningService.scheduleDelivery(command)
        then: 'delivery planned for the date should be included in plan completeness'
            assert planCompletenessProvider.get(date).getPlanned() == scheduledAmounts
        when: 'logistician edits the delivery'
            EditDelivery editDelivery = new EditDelivery(uuid, transport, editedPayload)
            deliveryPlanningService.editDelivery(editDelivery)
        then: 'delivery planned for the date should be included in plan completeness'
            assert planCompletenessProvider.get(date).getPlanned() == Amounts.of(refNo, 20)
    }
}
