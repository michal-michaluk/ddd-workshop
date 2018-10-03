package io.dddbyexamples.delivery.planning;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.dddbyexamples.delivery.planning.delivery.PayloadCapacityPolicy;
import io.dddbyexamples.delivery.planning.delivery.PayloadCapacityPolicyVer1;
import io.dddbyexamples.delivery.planning.delivery.TransportType;
import org.assertj.core.api.Assertions;

public class PayloadCapacityPolicySteps {

    PayloadCapacityPolicy policy = new PayloadCapacityPolicyVer1();

    private TransportType type;
    private Amounts amounts;

    private Amounts exceededAmount;

    @Given("^\"([^\"]*)\" of capacity (\\d+)$")
    public void ofCapacity(String type, int capacity) throws Throwable {
        this.type = new TransportType(type, capacity);
    }

    @Given("^payload contains (\\d+) palette$")
    public void payloadContainsPalette(long amount) throws Throwable {
        amounts = Amounts.of("palette", amount);
    }

    @When("^Capacity Policy is checked$")
    public void capacityPolicyIsChecked() throws Throwable {
        exceededAmount = policy.calculateExceeded(type, amounts);
    }

    @Then("^capacity is not exceeded$")
    public void capacityIsNotExceeded() throws Throwable {
        Assertions.assertThat(exceededAmount.isEmpty())
                .isTrue();
    }

    @Given("^payload contains (\\d+) cages$")
    public void payloadContainsCagesCages(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^payload contains (\\d+) trailers$")
    public void payloadContainsTrailersTrailers(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^capacity is exceeded with (\\d+) palette$")
    public void capacityIsExceededWithOverPalletsPalette(long amount) throws Throwable {
        Assertions.assertThat(exceededAmount.get("palette"))
                .isEqualTo(amount);
    }

    @Then("^capacity is exceeded with (\\d+) cages$")
    public void capacityIsExceededWithOverCagesCages(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^capacity is exceeded with (\\d+) trailers$")
    public void capacityIsExceededWithOverTrailersTrailers(int arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
