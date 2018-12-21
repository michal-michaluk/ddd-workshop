package io.dddbyexamples.delivery.planning.plan

import io.dddbyexamples.delivery.planning.Amounts
import io.dddbyexamples.delivery.planning.DeliveryPlanningService
import io.dddbyexamples.delivery.planning.delivery.EditDelivery
import io.dddbyexamples.delivery.planning.delivery.ScheduleDelivery
import spock.lang.Specification

import static io.dddbyexamples.delivery.planning.plan.SampleDeliveryData.*

class PlanCompletenessSpec extends Specification {

    private final PlanConfiguration configuration = new PlanConfiguration()
    private final DeliveryPlanningService deliveryPlanningService = configuration.deliveryPlanningService()
    private final PlanCompletenessProvider provider = configuration.planCompletenessProvider()

    def 'plan completeness should contain proper plannned amount'() {
        when: 'logistician schedules the delivery'
            ScheduleDelivery scheduleCommand = new ScheduleDelivery(transport, scheduledPayload)
            UUID uuid = deliveryPlanningService.scheduleDelivery(scheduleCommand)
        then: 'delivery planned for the date should be included in plan completeness'
            assert provider.get(date).getPlanned() == Amounts.of(refNo, 10)
        when: 'logistician edits the delivery'
            EditDelivery editDelivery = new EditDelivery(uuid, transport, editedPayload)
            deliveryPlanningService.editDelivery(editDelivery)
        then: 'updated delivery planned for the date should be included in plan completeness'
            assert provider.get(date).getPlanned() == Amounts.of(refNo, 20)
    }
}
