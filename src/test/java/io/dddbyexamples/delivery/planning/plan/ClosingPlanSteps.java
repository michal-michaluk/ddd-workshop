package io.dddbyexamples.delivery.planning.plan;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.dddbyexamples.delivery.planning.Amounts;
import io.dddbyexamples.delivery.planning.DeliveryEvents;
import io.dddbyexamples.delivery.planning.PlanningCompleted;
import lombok.Value;
import org.assertj.core.api.Assertions;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ClosingPlanSteps {

    private DemandForecasting forecasting = Mockito.mock(DemandForecasting.class);
    private CompletenessPolicy policy = new SimpleCompletenessPolicy();
    private DeliveryEvents events = Mockito.mock(DeliveryEvents.class);

    private LocalDate date = LocalDate.now();
    private Amounts demanded = Amounts.empty();
    private Amounts previousReminder = Amounts.empty();
    private Amounts planned = Amounts.empty();

    private Set<String> newReminder = Collections.emptySet();
    private Set<String> adjustDemands = Collections.emptySet();

    private ArgumentCaptor<PlanningCompleted> planCompleted = ArgumentCaptor.forClass(PlanningCompleted.class);
    private ArgumentCaptor<Amounts> adjustedDemands = ArgumentCaptor.forClass(Amounts.class);

    @Given("^customers demands:$")
    public void customersDemands(List<ProductAmount> demands) throws Throwable {
        this.demanded = new Amounts(demands.stream().collect(Collectors.toMap(
                ProductAmount::getProduct,
                ProductAmount::getAmount))
        );
    }

    @Given("^reminders from previous day:$")
    public void remindersFromPreviousDay(List<ProductAmount> reminders) throws Throwable {
        this.previousReminder = new Amounts(reminders.stream().collect(Collectors.toMap(
                ProductAmount::getProduct,
                ProductAmount::getAmount))
        );
    }

    @Given("^amounts delivered according to plan$")
    public void amountsDeliveredAccordingToPlan(List<ProductAmount> planned) throws Throwable {
        this.planned = new Amounts(planned.stream().collect(Collectors.toMap(
                ProductAmount::getProduct,
                ProductAmount::getAmount))
        );
    }

    @When("^customer decided to adjust demands for: \"([^\"]*)\"$")
    public void customerDecidedToAdjustDemandsFor(String refNos) throws Throwable {
        adjustDemands = new HashSet<>(Arrays.asList(refNos.split(", ?")));
    }

    @When("^customer decided to deliver missing pieces for next day: \"([^\"]*)\"$")
    public void customerDecidedToDeliverMissingPiecesForNextDay(String refNos) throws Throwable {
        newReminder = new HashSet<>(Arrays.asList(refNos.split(", ?")));
    }

    @When("^plan is closing$")
    public void planIsClosing() throws Throwable {
        DeliveryPlan plan = new DeliveryPlan(
                date, date,
                new PlanCompleteness(date, planned, demanded, previousReminder),
                forecasting, policy, events
        );
        plan.close(new ClosePlan(newReminder, adjustDemands));
    }

    @Then("^planning is completed$")
    public void planningIsCompleted() throws Throwable {
        Mockito.verify(events, Mockito.times(1))
                .emit(planCompleted.capture());
        Mockito.verify(forecasting, Mockito.atMost(1))
                .adjustDemands(Mockito.any(), adjustedDemands.capture());
        Assertions.assertThat(planCompleted.getValue())
                .isNotNull()
                .extracting(PlanningCompleted::getDate)
                .containsOnly(date)
        ;
    }

    @Then("^planning is NOT completed$")
    public void planningIsNOTCompleted() throws Throwable {
        Mockito.verifyZeroInteractions(events);
        Mockito.verifyZeroInteractions(forecasting);
    }

    @Then("^there was no need for adjusting demands$")
    public void thereWasNoNeedForAdjustingDemands() throws Throwable {
        Mockito.verifyZeroInteractions(forecasting);
    }

    @Then("^there was no need for reminder for next day$")
    public void thereWasNoNeedForReminderForNextDay() throws Throwable {
        planningIsCompleted();
        Assertions.assertThat(planCompleted.getValue().getReminder().isEmpty())
                .isTrue();
    }

    @Then("^demand for \"([^\"]*)\" was adjusted to (\\d+)$")
    public void demandForWasAdjustedTo(String refNo, long amount) throws Throwable {
        planningIsCompleted();
        Amounts adjustedDemands = this.adjustedDemands.getValue();
        Assertions.assertThat(adjustedDemands.get(refNo))
                .isEqualTo(amount);
    }

    @Then("^reminder of (\\d+) for \"([^\"]*)\" was saved$")
    public void reminderOfForWasSaved(long amount, String refNo) throws Throwable {
        Assertions.assertThat(planCompleted.getValue().getReminder().get(refNo))
                .isEqualTo(amount);
    }

    @Value
    public static class ProductAmount {
        String product;
        Long amount;
    }
}
