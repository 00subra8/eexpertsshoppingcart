Feature: Feature to test add products to cart

  Scenario: Happy path - Successfully Add a product
    When Product with Below details and quantity "5" is added
      | productName | unitPrice |
      | Dove Soap   | 39.99     |
    And Product with Below details and quantity "3" is added
      | productName | unitPrice |
      | Dove Soap   | 39.99     |
    Then Expected totalPrice is "319.92" with cart quantity "8"