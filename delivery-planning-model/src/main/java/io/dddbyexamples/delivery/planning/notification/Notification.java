package io.dddbyexamples.delivery.planning.notification;

import io.dddbyexamples.delivery.planning.Amounts;
import io.dddbyexamples.demand.forecasting.DemandedLevelsChanged;

public interface Notification {
    void sendNotificationAboutLateDemandChanges(DemandedLevelsChanged event, Amounts diff);
}
