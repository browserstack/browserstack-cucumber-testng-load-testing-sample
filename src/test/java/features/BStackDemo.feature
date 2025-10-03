Feature: Add product to cart on bstack demo
  As a user of the bstack demo site
  I want to add a product to the cart
  So that I can verify the product appears correctly

  Scenario: Add first product to cart
    Given I open the bstack demo homepage
    Then the bstack demo title should be "StackDemo"
    When I note the name of the first product
    And I add the first product to the cart
    Then the mini cart should be displayed
    And the product name in the cart should match noted name
