Feature: Calculation and presentation of Plan Completeness

  Completeness answers question how "complete is the plan"
  or in others words: shows how many units of products we need to put in deliveries
  to match demands of all customers for given day.

  Plan Completeness takes into account:
  - parts amount currently planed in deliveries,
  - current demands expressed in parts amount,
  - the remainder (not fulfilled demands for product from last plan).

  So Plan Completeness is recalculated after each event:
  - DeliveredAmountsChanged - influences planned amount in deliveries for a day of planning
  - DemandedLevelsChanged - influences demanded amount for a day of planning
  - PlanningCompleted - contains optional reminder for next planning day
