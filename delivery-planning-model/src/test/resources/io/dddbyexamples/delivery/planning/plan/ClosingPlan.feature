Feature: Closing Delivery Plan

  Closing Plan:
  Before closing, plan should fulfill demands.
  In other case, confirmation for each product need to be taken:
  confirm customer acceptance: „deliver part next day” or „adjust demand”
  the remainder is left for next plan accordingly or demands should be adjusted.
  Decision "adjust demand" adjusts demands in Demand Forecasting module.

  After closing:
  Emails for each spedition company should be send.
  Email should contain information about each delivery:
  - arrival time at our location,
  - transport type,
  - delivery location.
  Pick Lists and Loading Schedule should be generated for warehouse crew.


  Scenario: closing plan fulfilling demands
    Given customers demands:
      | product | amount |
      | 3009000 | 2000   |
      | 3009001 | 2000   |
    Given amounts delivered according to plan
      | product | amount |
      | 3009000 | 2000   |
      | 3009001 | 2000   |
    When plan is closing
    Then planning is completed
    And there was no need for adjusting demands
    And there was no need for remainder for next day


  Scenario: cannot close plan NOT fulfilling demands
    Given customers demands:
      | product | amount |
      | 3009000 | 2000   |
    Given amounts delivered according to plan
      | product | amount |
      | 3009000 | 1000   |
    When plan is closing
    Then planning is NOT completed


  Scenario: closing plan with demand adjustment
    Given customers demands:
      | product | amount |
      | 3009000 | 2000   |
      | 3009001 | 2000   |
    Given amounts delivered according to plan
      | product | amount |
      | 3009000 | 2000   |
      | 3009001 | 1800   |
    When customer decided to adjust demands for: "3009001"
    And plan is closing
    Then planning is completed
    And demand for "3009001" was adjusted to 1800
    And there was no need for remainder for next day


  Scenario: closing plan with remainder for next day
    Given customers demands:
      | product | amount |
      | 3009000 | 2000   |
      | 3009001 | 2000   |
    Given amounts delivered according to plan
      | product | amount |
      | 3009000 | 2000   |
      | 3009001 | 1800   |
    When customer decided to deliver missing pieces for next day: "3009001"
    And plan is closing
    Then planning is completed
    And there was no need for adjusting demands
    And remainder of 200 for "3009001" was saved


  Scenario: closing plan with multiple different decisions
    Given customers demands:
      | product | amount |
      | 3009000 | 2000   |
      | 3009001 | 2000   |
      | 3009002 | 2000   |
    Given amounts delivered according to plan
      | product | amount |
      | 3009000 | 1800   |
      | 3009001 | 1800   |
      | 3009002 | 1800   |
    When customer decided to adjust demands for: "3009000, 3009001"
    And customer decided to deliver missing pieces for next day: "3009002"
    And plan is closing
    Then planning is completed
    And demand for "3009000" was adjusted to 1800
    And demand for "3009001" was adjusted to 1800
    And remainder of 200 for "3009002" was saved


  Scenario: counting previous remainder
    Given customers demands:
      | product | amount |
      | 3009000 | 2000   |
      | 3009001 | 2000   |
    Given remainders from previous day:
      | product | amount |
      | 3009000 | 150    |
    Given amounts delivered according to plan
      | product | amount |
      | 3009000 | 2000   |
      | 3009001 | 2000   |
