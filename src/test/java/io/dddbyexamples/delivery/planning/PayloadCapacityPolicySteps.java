package io.dddbyexamples.delivery.planning;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

class PayloadCapacityPolicySteps {
    @Given("^\"([^\"]*)\" of capacity (\\d+)$")
    public void ofCapacity(String arg0, int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^payload contains (\\d+) palette$")
    public void payloadContainsPalette(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^simple Capacity Policy is checked$")
    public void simpleCapacityPolicyIsChecked() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^capacity is not exceeded$")
    public void capacityIsNotExceeded() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^payload contains <cages> cages$")
    public void payloadContainsCagesCages() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^payload contains <trailers> trailers$")
    public void payloadContainsTrailersTrailers() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^capacity is exceeded with <overPallets> palette$")
    public void capacityIsExceededWithOverPalletsPalette() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^capacity is exceeded with <overCages> cages$")
    public void capacityIsExceededWithOverCagesCages() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^capacity is exceeded with <overTrailers> trailers$")
    public void capacityIsExceededWithOverTrailersTrailers() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
