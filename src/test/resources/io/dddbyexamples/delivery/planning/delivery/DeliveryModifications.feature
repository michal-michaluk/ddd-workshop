Feature: Editing Delivery Plan

  At end of his work day (around 16:00), Logistician prepares delivery plan
  for next day (plan include night shift of next day).
  Plan for today (including night shift) was prepared day before.

  Software supports Logistician while planning, by:
  - checking payload size against Transport Capacity (capacity exceeded),
  - continuously checking Plan Completeness against customers demands for that day.

  Transport Capacity:
  each transport type (truck etc.) defines its capacity expressed by
  amount of euro palette fitting in it.

  There are 3 different types of Storage Units:
  *euro palette* - capacity defined explicitly for transport type,
  *cages* - with same size like euro palette, but two cages can be stocked one on another,
  so for transport with capacity od 22 euro pallets we can fit 44 cages,
  *trailers* - 10 trailers can be transported in standard 22 palette transport size,
  no other transport types are leveraged for trailers delivery.

  Plan Completeness:
  Plan change recalculates plan completeness by taking into account:
  - parts amount currently planed in deliveries,
  - current demands expressed in parts amount,
  - the remainder (not fulfilled demands for product from last plan).

  For each Product following information is configurable:
  - preferred transport type and its capacity,
  - storage unit type used for storage and transport,
  - amount of product per storage unit.
  - typical delivery location
  - preferred spedition company


  Background:
    Given 150 pieces of product "3009000" are stored on "euro pallet"
    Given 200 pieces of product "3009001" are stored on "euro pallet"


  Scenario: two products can fit single transport
    Given customers demands for tomorrow:
      | product | amount |
      | 3009000 | 2000   |
      | 3009001 | 2000   |
    When new delivery is scheduled at "07:00" with "truck" of capacity 33:
      | product | storageUnits |
      | 3009000 | 15           |
      | 3009001 | 10           |
    Then plan was changed
    And Transport capacity is not exceeded
    And customers demands are fulfilled


  Scenario: delivery exceeded transport capacity
    Given customers demands for tomorrow:
      | product | amount |
      | 3009000 | 4000   |
    When new delivery is scheduled at "07:00" with "truck" of capacity 22:
      | product | storageUnits |
      | 3009000 | 27           |
    Then plan was NOT changed
    And Transport capacity is exceeded
    And customers demands are not fulfilled


  Scenario: products delivery with two transports
    Given customers demands for tomorrow:
      | product | amount |
      | 3009000 | 4000   |
    When new delivery is scheduled at "06:00" with "truck" of capacity 22:
      | product | storageUnits |
      | 3009000 | 22           |
    Then Transport capacity is not exceeded
    Then customers demands are not fulfilled
    When new delivery is scheduled at "07:00" with "truck" of capacity 22:
      | product | storageUnits |
      | 3009000 | 5            |
    Then plan was changed
    And Transport capacity is not exceeded
    And customers demands are fulfilled


  Scenario: transport with partial (not full) pallet
    Given customers demands for tomorrow:
      | product | amount |
      | 3009000 | 2000   |
      | 3009001 | 2100   |
    When new delivery is scheduled at "07:00" with "truck" of capacity 33:
      | product | storageUnits | pieces |
      | 3009000 | 15           |        |
      | 3009001 | 10           |        |
      | 3009001 | 1            | 100    |
    Then plan was changed
    And Transport capacity is not exceeded
    And customers demands are fulfilled


  Scenario: editing previously defined delivery
    Given customers demands for tomorrow:
      | product | amount |
      | 3009000 | 2000   |
      | 3009001 | 2000   |
    Given delivery "delivery #1" scheduled at "07:00" with "truck" of capacity 33:
      | product | storageUnits |
      | 3009001 | 10           |
    And customers demands are not fulfilled
    When delivery "delivery #1" is edited: scheduled at "07:00" with "truck" of capacity 33:
      | product | storageUnits |
      | 3009000 | 15           |
      | 3009001 | 10           |
    Then plan was changed
    And Transport capacity is not exceeded
    And customers demands are fulfilled


  Scenario: canceling previously defined delivery
    Given customers demands for tomorrow:
      | product | amount |
      | 3009000 | 2000   |
      | 3009001 | 2000   |
    Given delivery "delivery #1" scheduled at "07:00" with "truck" of capacity 33:
      | product | storageUnits |
      | 3009001 | 10           |
      | 3009000 | 15           |
    And customers demands are fulfilled
    When delivery "delivery #1" is cancelled
    Then plan was changed
    And customers demands are not fulfilled
