Feature: Notification about late demand changes

  Demand changes leading for incompleteness of closed plan
  should cause alert for employee responsible for delivery plan.


  Scenario: Demand changes and plan not yet completed
    Given customers demands for today:
      | product | amount |
      | 3009000 | 2000   |
      | 3009001 | 2000   |
    Given amounts delivered today according to plan:
      | product | amount |
      | 3009000 | 2000   |
      | 3009001 | 2000   |
    Given plan for today NOT yet closed
    When today's demand changed for product "3009000" from 2000 to 2500
    Then notification about late demand changes will NOT be send


  Scenario: Closed plan still fulfills demand after demand changes
    Given customers demands for today:
      | product | amount |
      | 3009000 | 2450   |
      | 3009001 | 2000   |
    Given amounts delivered today according to plan:
      | product | amount |
      | 3009000 | 2500   |
      | 3009001 | 2000   |
    Given plan for today is closed
    When today's demand changed for product "3009000" from 2450 to 2500
    Then notification about late demand changes will NOT be send


  Scenario: Closed plan become incompleteness after demand changes
    Given customers demands for today:
      | product | amount |
      | 3009000 | 2000   |
      | 3009001 | 2000   |
    Given amounts delivered today according to plan:
      | product | amount |
      | 3009000 | 2000   |
      | 3009001 | 2000   |
    Given plan for today is closed
    When today's demand changed for product "3009000" from 2000 to 2500
    Then notification about late demand changes is send with diff:
      | product | amount |
      | 3009000 | -500   |

