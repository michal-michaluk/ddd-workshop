package io.dddbyexamples.delivery.planning;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.dddbyexamples.delivery.planning.delivery.*;
import io.dddbyexamples.delivery.planning.plan.*;
import lombok.Data;
import org.assertj.core.api.Assertions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class DeliveryModificationsSteps {

    // objects under test
    private DeliveryPlan plan;
    private Map<Object, Delivery> deliveries = new HashMap<>();
    private PlanCompleteness planCompleteness;

    // dependencies
    private PayloadCapacityPolicy payloadCapacityPolicy = new SimplePayloadCapacityPolicy();
    private CompletenessPolicy completenessPolicy = new SimpleCompletenessPolicy();
    private DemandForecasting forecasting = change -> {
    };

    // state
    private Map<String, StorageUnit> storageUnits = new HashMap<>();

    // consequences
    private Amounts exceeded = Amounts.empty();
    private DeliveryEvents events = createEventsListener();
    private DeliveredAmountsChanged planChanged;
    private PlanningCompleted planCompleted;

    @Before
    public void setUp() throws Exception {
        plan = new DeliveryPlan(
                UUID.randomUUID(), planCompleteness, forecasting, completenessPolicy, events
        );
    }

    @Given("^(\\d+) pieces of product \"([^\"]*)\" are stored on \"([^\"]*)\"$")
    public void piecesOfProductAreStoredOn(int amount, String refNo, String storageUnitType) throws Throwable {
        storageUnits.put(refNo, new StorageUnit(storageUnitType, amount));
    }

    @Given("^customers demands for tomorrow:$")
    public void customersDemandsForTomorrow(List<ExpectedDemand> demands) throws Throwable {
        Amounts todayDemands = new Amounts(demands.stream().collect(Collectors.toMap(
                ExpectedDemand::getProduct,
                ExpectedDemand::getAmount
        )));
        this.planCompleteness = new PlanCompleteness(
                LocalDate.now(), Amounts.empty(), todayDemands, Amounts.empty()
        );
    }

    @Given("^delivery \"([^\"]*)\" scheduled at \"([^\"]*)\" with \"([^\"]*)\" of capacity (\\d+):$")
    public void deliveryWasScheduledAtWithOfCapacity(String id,
                                                     String time, String transportType,
                                                     int transportCapacity,
                                                     List<DeliveryPayload> payload) throws Throwable {
        Delivery object = createNewDelivery(id);
        EditDelivery command = createCommand(id,
                time,
                transportType,
                transportCapacity,
                payload
        );
        object.editDelivery(command);
        planChanged = null;
    }

    @When("^new delivery is scheduled at \"([^\"]*)\" with \"([^\"]*)\" of capacity (\\d+):$")
    public void newDeliveryIsPlannedAtWith(String time, String transportType,
                                           int transportCapacity, List<DeliveryPayload> payload) throws Throwable {
        String id = "transport at " + time;
        Assertions.assertThat(deliveries).doesNotContainKeys(id);
        Delivery object = createNewDelivery(id);
        EditDelivery command = createCommand(id,
                time,
                transportType,
                transportCapacity,
                payload
        );
        exceeded = object.editDelivery(command);
    }

    @When("^new delivery \"([^\"]*)\" is scheduled at \"([^\"]*)\" with \"([^\"]*)\" of capacity (\\d+):$")
    public void newDeliveryIsPlannedAtWith(String id,
                                           String time, String transportType,
                                           int transportCapacity,
                                           List<DeliveryPayload> payload) throws Throwable {
        Delivery object = createNewDelivery(id);
        EditDelivery command = createCommand(id,
                time,
                transportType,
                transportCapacity,
                payload
        );
        exceeded = object.editDelivery(command);
    }

    @When("^delivery \"([^\"]*)\" is edited: scheduled at \"([^\"]*)\" with \"([^\"]*)\" of capacity (\\d+):$")
    public void editDeliveryIsPlannedAtWith(String id,
                                            String time, String transportType,
                                            int transportCapacity,
                                            List<DeliveryPayload> payload) throws Throwable {
        EditDelivery command = createCommand(id, time, transportType, transportCapacity, payload);
        exceeded = deliveries.get(id).editDelivery(command);
    }

    @When("^delivery \"([^\"]*)\" is cancelled$")
    public void deliveryIsCancelled(String id) throws Throwable {
        deliveries.get(id).clearDelivery();
    }

    private Delivery createNewDelivery(String id) {
        new DeliveryFactory(payloadCapacityPolicy, events);
        Delivery object = new Delivery(id, payloadCapacityPolicy, events);
        deliveries.put(id, object);
        return object;
    }

    private EditDelivery createCommand(String id, String time, String transportType, int transportCapacity, List<DeliveryPayload> payload) {
        return new EditDelivery(id,
                LocalDateTime.of(
                        LocalDate.now().plusDays(1),
                        LocalTime.parse(time)
                ),
                new TransportType(transportType, transportCapacity),
                Payload.create(payload.stream().collect(Collectors.toMap(
                        DeliveryPayload::getProduct,
                        DeliveryPayload::getStorageUnits
                )), storageUnits));
    }

    @Then("^Transport capacity is not exceeded$")
    public void transportCapacityInNotExceeded() throws Throwable {
        assertThat(exceeded.isEmpty()).isTrue();
    }

    @Then("^Transport capacity is exceeded$")
    public void transportCapacityInExceeded() throws Throwable {
        assertThat(exceeded.isEmpty()).isFalse();
    }

    @Then("^customers demands are fulfilled$")
    public void customersDemandsAreFulfilled() throws Throwable {
        assertThat(isDemandsFulfilled()).isTrue();
    }

    @Then("^customers demands are not fulfilled$")
    public void customersDemandsAreNotFulfilled() throws Throwable {
        assertThat(isDemandsFulfilled()).isFalse();
    }

    @Then("^plan was changed$")
    public void newDeliveryWasScheduled() throws Throwable {
        assertThat(planChanged).isNotNull();
    }

    @Then("^plan was NOT changed$")
    public void newDeliveryWasNotScheduled() throws Throwable {
        assertThat(planChanged).isNull();
    }

    private boolean isDemandsFulfilled() {
        return planCompleteness.getDiff()
                .allMatch((refNo, amount) -> amount >= 0L);
    }

    private DeliveryEvents createEventsListener() {
        return new DeliveryEvents() {
            @Override
            public void emit(DeliveredAmountsChanged event) {
                planChanged = event;
                planCompleteness.apply(event);
            }

            @Override
            public void emit(PlanningCompleted event) {
                planCompleted = event;

            }
        };
    }

    @Data
    static class ExpectedDemand {
        private String product;
        private long amount;
        private String deliverySchema;
    }

    @Data
    static class DeliveryPayload {
        private String product;
        private int storageUnits;
    }
}
