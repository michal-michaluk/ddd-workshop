package io.dddbyexamples.delivery.planning.delivery;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.dddbyexamples.delivery.planning.delivery.capacity.PayloadCapacityPolicy;
import io.dddbyexamples.delivery.planning.delivery.capacity.PayloadCapacityPolicyVer1;
import io.dddbyexamples.delivery.planning.delivery.capacity.StorageUnitsAmount;
import org.assertj.core.api.Assertions;

public class PayloadCapacityPolicySteps {

    private PayloadCapacityPolicy policy = new PayloadCapacityPolicyVer1();

    private TransportType type;
    private StorageUnitsAmount.Builder amounts = StorageUnitsAmount.builder();

    private StorageUnitsAmount exceededAmount;

    @Given("^\"([^\"]*)\" of capacity (\\d+)$")
    public void ofCapacity(String type, int capacity) throws Throwable {
        this.type = new TransportType(type, capacity);
    }

    @Given("^payload contains (\\d+) palette$")
    public void payloadContainsPalette(long amount) throws Throwable {
        amounts = amounts.addPalettes(amount);
    }

    @Given("^payload contains (\\d+) cages$")
    public void payloadContainsCagesCages(long amount) throws Throwable {
        amounts = amounts.addCages(amount);
    }

    @Given("^payload contains (\\d+) trailers$")
    public void payloadContainsTrailersTrailers(long amount) throws Throwable {
        amounts = amounts.addTrailers(amount);
    }

    @When("^Capacity Policy is checked$")
    public void capacityPolicyIsChecked() throws Throwable {
        exceededAmount = policy.calculateExceeded(type, amounts.build());
    }

    @Then("^capacity is not exceeded$")
    public void capacityIsNotExceeded() throws Throwable {
        Assertions.assertThat(exceededAmount.isEmpty())
                .isTrue();
    }

    @Then("^capacity is exceeded with (\\d+) palette$")
    public void capacityIsExceededWithOverPalletsPalette(long amount) throws Throwable {
        Assertions.assertThat(exceededAmount.getPalette())
                .isEqualTo(amount);
    }

    @Then("^capacity is exceeded with (\\d+) cages$")
    public void capacityIsExceededWithOverCagesCages(int amount) throws Throwable {
        Assertions.assertThat(exceededAmount.getCages())
                .isEqualTo(amount);
    }

    @Then("^capacity is exceeded with (\\d+) trailers$")
    public void capacityIsExceededWithOverTrailersTrailers(int amount) throws Throwable {
        Assertions.assertThat(exceededAmount.getTrailers())
                .isEqualTo(amount);
    }
}
