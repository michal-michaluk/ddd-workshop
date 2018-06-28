package io.dddbyexamples.delivery.planning;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.Value;

import java.util.List;

public class DeliveryPlanSteps {

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

    @When("^new delivery is planned at (\\d+:\\d+) with truck of capacity \\d+:$")
    public void newDeliveryIsPlannedAtWith(String time, int transportCapacity, List<TransportedProduct> content) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Transport capacity is not exceeded$")
    public void transportCapacityIsNotExceeded() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^customers demands are fulfilled$")
    public void customersDemandsAreFulfilled() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Transport capacity is exceeded$")
    public void transportCapacityIsExceeded() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Transport capacity in not exceeded$")
    public void transportCapacityInNotExceeded() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^customers demands are not fulfilled$")
    public void customersDemandsAreNotFulfilled() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Value
    public static class Demands {
        String product;
        int amount;
        String deliverySchema;
    }

    @Value
    public static class TransportedProduct {
        String product;
        String storageUnits;
    }
}
