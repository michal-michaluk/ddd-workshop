Feature: Payload capacity policy

  Transport Capacity info:
  each transport type (truck etc.) defines its capacity expressed by
  amount of euro pallet fitting in it.

  There are 3 different types of Storage Units:
  *euro pallet* - capacity defined explicitly for transport type,
  *cages* - with same size like euro pallet, but two cages can be stocked one on another,
  so for transport with capacity od 22 euro pallets we can fit 44 cages,
  *trailers* - 10 trailers can be transported in standard 22 pallet transport size,
  no other transport types are leveraged for trailers delivery.


  Scenario: Full truck
    Given "truck" of capacity 22
    Given payload contains 22 pallets
    When Capacity Policy is checked
    Then capacity is not exceeded


  Scenario: Full truck, one pallet over capacity
    Given "truck" of capacity 22
    Given payload contains 23 pallets
    When Capacity Policy is checked
    Then capacity is exceeded with 1 pallets


  Scenario Outline: scenarios for pallets
    Given "<transport>" of capacity <capacity>
    Given payload contains <pallets> pallets
    When Capacity Policy is checked
    Then capacity is exceeded with <overPallets> pallets

    Examples:
      | transport | capacity | pallets | overPallets |
      | truck     | 22       | 10      | 0           |
      | truck     | 22       | 22      | 0           |
      | truck     | 22       | 23      | 1           |
      | truck     | 22       | 25      | 3           |
      | truck     | 33       | 25      | 0           |
      | truck     | 33       | 32      | 0           |
      | truck     | 33       | 33      | 0           |
      | truck     | 33       | 35      | 2           |


  Scenario Outline: Verify payload capacity policy
    Given "<transport>" of capacity <capacity>
    Given payload contains <pallets> pallets
    Given payload contains <cages> cages
    Given payload contains <trailers> trailers
    When Capacity Policy is checked
    Then capacity is exceeded with <overPallets> pallets
    Then capacity is exceeded with <overCages> cages
    Then capacity is exceeded with <overTrailers> trailers

    Examples:
      | transport | capacity | pallets | cages | trailers | overPallets | overCages | overTrailers |
      # partial payload:
      | truck     | 22       | 10      | 0     | 0        | 0           | 0         | 0            |
      | truck     | 22       | 0       | 20    | 0        | 0           | 0         | 0            |
      | truck     | 22       | 0       | 0     | 5        | 0           | 0         | 0            |

      # full payload:
      | truck     | 10       | 10      | 0     | 0        | 0           | 0         | 0            |
      | truck     | 10       | 0       | 20    | 0        | 0           | 0         | 0            |
      | truck     | 10       | 0       | 0     | 5        | 0           | 0         | 5            |

      # capacity exceeded with mixed types:
      | truck     | 10       | 10      | 2     | 0        | 1           | 2         | 0            |

      # trailers are not mixable with other storage units
      | truck     | 22       | 2       | 0     | 3        | 2           | 0         | 3            |
      | truck     | 22       | 0       | 2     | 3        | 0           | 2         | 3            |

      # trailers capacity is defined only for truck of capacity 22
      | truck     | 10       | 0       | 0     | 3        | 0           | 0         | 3            |
      | truck     | 10       | 2       | 0     | 3        | 0           | 0         | 3            |
      | truck     | 10       | 0       | 2     | 3        | 0           | 0         | 3            |

      # capacity exceeded:
      | truck     | 10       | 11      | 0     | 0        | 1           | 0         | 0            |
      | truck     | 10       | 0       | 21    | 0        | 0           | 1         | 0            |
      | truck     | 10       | 0       | 0     | 6        | 0           | 0         | 6            |


  Scenario Outline: 2 cages can be stacked on each other
    Given "truck" of capacity <capacity>
    Given payload contains <pallets> pallets
    Given payload contains <cages> cages
    When Capacity Policy is checked
    Then capacity is exceeded with <overPallets> pallets
    Then capacity is exceeded with <overCages> cages

    Examples:
      | capacity | pallets | cages | overPallets | overCages |
      | 10       | 9       | 1     | 0           | 0         |
      | 10       | 10      | 1     | 1           | 1         |
      | 10       | 11      | 1     | 2           | 1         |

      | 10       | 9       | 2     | 0           | 0         |
      | 10       | 10      | 2     | 1           | 2         |
      | 10       | 11      | 2     | 2           | 2         |

      | 10       | 8       | 3     | 0           | 0         |
      | 10       | 9       | 3     | 1           | 1         |
      | 10       | 10      | 3     | 2           | 3         |

      | 10       | 8       | 4     | 0           | 0         |
      | 10       | 9       | 4     | 1           | 2         |
      | 10       | 10      | 4     | 2           | 4         |


  Scenario Outline: Very limited support for trailers
    Given "<transport>" of capacity <capacity>
    Given payload contains <pallets> pallets
    Given payload contains <cages> cages
    Given payload contains <trailers> trailers
    When Capacity Policy is checked
    Then capacity is exceeded with <overPallets> pallets
    Then capacity is exceeded with <overCages> cages
    Then capacity is exceeded with <overTrailers> trailers

    Examples:
      | transport | capacity | pallets | cages | trailers | overPallets | overCages | overTrailers |
      | truck     | 22       | 0       | 0     | 9        | 0           | 0         | 0            |
      | truck     | 22       | 0       | 0     | 10       | 0           | 0         | 0            |
      | truck     | 22       | 0       | 0     | 11       | 0           | 0         | 1            |
      | truck     | 22       | 0       | 0     | 12       | 0           | 0         | 2            |

      # that truck not support trailers
      | truck     | 10       | 0       | 0     | 1        | 0           | 0         | 1            |
      | truck     | 10       | 0       | 0     | 9        | 0           | 0         | 9            |
      | truck     | 10       | 0       | 0     | 10       | 0           | 0         | 10           |
      | truck     | 10       | 0       | 0     | 11       | 0           | 0         | 11           |
      | truck     | 10       | 0       | 0     | 12       | 0           | 0         | 12           |

      # that truck not support trailers
      | truck     | 33       | 0       | 0     | 9        | 0           | 0         | 9            |
      | truck     | 33       | 0       | 0     | 10       | 0           | 0         | 10           |
      | truck     | 33       | 0       | 0     | 11       | 0           | 0         | 11           |
      | truck     | 33       | 0       | 0     | 12       | 0           | 0         | 12           |
