package io.dddbyexamples.delivery.planning.plan;

import java.time.LocalDate;

public interface PlanCompletenessProvider {

    PlanCompleteness get(LocalDate date);
}
