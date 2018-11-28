package io.dddbyexamples.delivery.planning.plan

import spock.lang.Specification

class PlanCompletenessSpec extends Specification {

    def 'plan completeness should contain proper plannned amount'() {
        when: 'logistician schedules the delivery'
        then: 'delivery planned for the date should be included in plan completeness'
        when: 'logistician edits the delivery'
        then: 'delivery planned for the date should be included in plan completeness'
    }
}
