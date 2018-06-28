package io.dddbyexamples.delivery.planning;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DeliveryPlanSteps {

    private Result result;
    private DeliveryPlan plan;
    private int scheduledDeliveries;

    @Given("^(\\d+) pieces of product \"([^\"]*)\" are stored on \"([^\"]*)\"$")
    public void piecesOfProductAreStoredOn(int amount, String productRefNo, String storageUnit) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^customers demands for tomorrow:$")
    public void customersDemandsForTomorrow(List<Demands> demands) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^new delivery is scheduled at (\\d+:\\d+) with truck of capacity \\d+:$")
    public void newDeliveryIsPlannedAtWith(String time, int transportCapacity, List<TransportedProduct> content) throws Throwable {
        plan = new DeliveryPlan(new ArrayList<>());

        scheduledDeliveries = plan.getDeliveries().size();
        result = plan.scheduleDelivery(
                new ScheduleDelivery(time, transportCapacity, content)
        );
    }

    @Then("^new delivery was scheduled$")
    public void newDeliveryWasScheduled() throws Throwable {
        // check internal plan state
        assertThat(plan.getDeliveries())
                .hasSize(scheduledDeliveries + 1);
        // or check event was emitted
    }

    @Then("^new delivery was not scheduled$")
    public void newDeliveryWasNotScheduled() throws Throwable {
        assertThat(plan.getDeliveries())
                .hasSize(scheduledDeliveries);
    }

    @Then("^Transport capacity is exceeded$")
    public void transportCapacityIsExceeded() throws Throwable {
        assertThat(result.isCapacityIsExceeded())
                .isTrue();
    }

    @Then("^Transport capacity is not exceeded$")
    public void transportCapacityIsNotExceeded() throws Throwable {
        assertThat(result.isCapacityIsExceeded())
                .isFalse();
    }

    @Then("^customers demands are fulfilled$")
    public void customersDemandsAreFulfilled() throws Throwable {
        assertThat(result.isDemandsAreFulfilled())
                .isTrue();
    }

    @Then("^customers demands are not fulfilled$")
    public void customersDemandsAreNotFulfilled() throws Throwable {
        assertThat(result.isDemandsAreFulfilled())
                .isFalse();
    }

    @Value
    public static class Demands {
        String product;
        int amount;
        String deliverySchema;
    }

}
