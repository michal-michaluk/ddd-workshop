Feature: Editing Delivery Plan

  Transport capacity cannot be exceeded:
  euro palette - defined for transport type,
  cages - size like palette, but 2 can be stocked,
  trailers - 10 in standard 22 palette transport size.

  Plan change recalculates plan completeness by taking into account:
  - planed deliveries
  - current demands in parts amount
  - the remainder (not fulfilled demands for product from last plan)

  Feasibility of the plan by taking into account:
  - stock state
  - planed deliveries
  - production plan
  all those information combined creates stock forecast

  Preferences per product:
  - storage units type
  - parts amount per storage units


  Background:
    Given 150 pieces of product "3009000" are stored on "euro pallet"
    Given 200 pieces of product "3009001" are stored on "euro pallet"


  Scenario: two products can fit single transport
    Given customers demands for tomorrow:
      | product | amount | delivery schema |
      | 3009000 | 2000   | at day start    |
      | 3009001 | 2000   | at day start    |
    When new delivery is planned at 07:00 with "truck (33)":
      | product | storage units |
      | 3009000 | 15            |
      | 3009001 | 10            |
    Then Transport capacity is not exceeded
    Then customers demands are fulfilled


  Scenario: delivery exceeded transport capacity
    Given customers demands for tomorrow:
      | product | amount | delivery schema |
      | 3009000 | 4000   | at day start    |
    When new delivery is planned at 07:00 with "truck (22)":
      | product | storage units |
      | 3009000 | 27            |
    Then Transport capacity is exceeded
    Then customers demands are fulfilled


  Scenario: products delivery with two transports
    Given customers demands for tomorrow:
      | product | amount | delivery schema |
      | 3009000 | 4000   | at day start    |
    When new delivery is planned at 06:00 with "truck (22)":
      | product | storage units |
      | 3009000 | 22            |
    Then Transport capacity in not exceeded
    Then customers demands are not fulfilled
    When new delivery is planned at 07:00 with "truck (22)":
      | product | storage units |
      | 3009000 | 5             |
    Then Transport capacity is not exceeded
    Then customers demands are fulfilled
