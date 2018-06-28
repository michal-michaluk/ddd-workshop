package io.dddbyexamples.delivery.planning;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;

public class DeliveryPlanSteps {
    @Given("^(\\d+) pieces of product \"([^\"]*)\" are stored on \"([^\"]*)\"$")
    public void piecesOfProductAreStoredOn(int arg0, String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
