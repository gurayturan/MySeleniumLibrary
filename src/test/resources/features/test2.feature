Feature: MY FEATURE2

  @guray
  Scenario: Title of your scenario
    Given I initialize InternetExplorer driver and run test local=True
    When I go to https://www.google.com url
    Then I see Homepage page
    Then I enter Ali İsmail Korkmaz text to input box text area
    Then I tap ENTER keyboard button with input box element
   Then I mouse hover to Videolar element
    Then I click Videolar element
    Then I wait 5 seconds

  @guray2
  Scenario: Title of your scenario
    Given I initialize Chrome driver and run test local=True
    When I go to http://automationpractice.com/index.php url
    Then I click Sign in element
    And I wait Create an account element 10 seconds

    When I see login page
    Then I enter abcxx77@gmail.com text to email create input box text area
    And I click create account button element
    
    When I wait Your personal information element 10 seconds
    Then I click PAGE_DOWN keyboard button
    Then I mouse hover to state combo box element
    Then I click state combo box element
    And I wait 5 seconds



