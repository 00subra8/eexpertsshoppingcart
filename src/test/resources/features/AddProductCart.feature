Feature: Feature to test add products to cart

  Scenario: Happy path - Successfully Add a product
    When Product with Below details and quantity "2" is added
      | productName | unitPrice |
      | Dove Soap   | 39.99     |
    And Product with Below details and quantity "2" is added
      | productName | unitPrice |
      | Axe deo     | 99.99     |
    Then Expected totalPrice is "314.96" with cart quantity "4" and Sales tax "35.00"