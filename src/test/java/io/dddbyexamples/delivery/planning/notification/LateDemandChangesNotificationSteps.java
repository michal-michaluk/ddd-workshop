package io.dddbyexamples.delivery.planning.notification;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.dddbyexamples.delivery.planning.Amounts;
import io.dddbyexamples.delivery.planning.PlanningCompleted;
import io.dddbyexamples.delivery.planning.plan.PlanCompletenessBuilder;
import io.dddbyexamples.delivery.planning.plan.ProductAmount;
import io.dddbyexamples.demand.forecasting.DemandedLevelsChanged;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

public class LateDemandChangesNotificationSteps {
    private final LocalDate today = LocalDate.now();
    private final Notification notification = Mockito.mock(Notification.class);
    private final PlanCompletenessBuilder completeness = new PlanCompletenessBuilder();

    private final LateDemandChangesNotification subject = new LateDemandChangesNotification(
            new HashSet<>(),
            notification,
            completeness
    );
    private DemandedLevelsChanged lastDemandedChanged;

    @Given("^customers demands for today:$")
    public void customersDemandsForToday(List<ProductAmount> demands) throws Throwable {
        Amounts amounts = ProductAmount.createAmounts(demands);
        completeness.demanded(today, amounts);
    }

    @Given("^amounts delivered today according to plan:$")
    public void amountsDeliveredTodayAccordingToPlan(List<ProductAmount> delivered) throws Throwable {
        Amounts amounts = ProductAmount.createAmounts(delivered);
        completeness.planned(today, amounts);
    }

    @Given("^plan for today NOT yet closed$")
    public void planForTodayNOTYetClosed() throws Throwable {
        // just not apply PlanningCompleted
    }

    @Given("^plan for today is closed$")
    public void planForTodayIsClosed() throws Throwable {
        PlanningCompleted event = new PlanningCompleted(today, today, Amounts.empty());
        subject.apply(event);
    }

    @When("^today's demand changed for product \"([^\"]*)\" from (\\d+) to (\\d+)$")
    public void todaySDemandChangedForProductTo(String refNo, long previous, long amount) throws Throwable {
        lastDemandedChanged = new DemandedLevelsChanged(
                today, refNo, previous, amount
        );
        completeness.get(today).apply(lastDemandedChanged);
        subject.apply(lastDemandedChanged);
    }

    @Then("^notification about late demand changes will NOT be send$")
    public void notificationAboutLateDemandChangesWillNOTBeSend() throws Throwable {
        Mockito.verifyZeroInteractions(notification);
    }

    @Then("^notification about late demand changes is send with diff:$")
    public void notificationAboutLateDemandChangesIsSend(List<ProductAmount> diffs) throws Throwable {
        Mockito.verify(notification, Mockito.times(1))
                .sendNotificationAboutLateDemandChanges(
                        Mockito.eq(lastDemandedChanged),
                        Mockito.eq(ProductAmount.createAmounts(diffs))
                );
    }
}
