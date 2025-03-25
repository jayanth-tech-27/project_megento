Feature: Checking Logout Functionality  

  Scenario: To check the Logout functionality  
    Given User opens the browser  
    And User navigates to the URL "https://magento.softwaretestingboard.com/"  
    When User clicks the link "Sign In"  
    And User enters valid email "<email>" and password "<password>"  
    And User clicks the "Sign In" button  
    And User clicks "Logout" from "My Account" page  
    Then User verifies that the session ends and the user is redirected to the homepage  
    And User verifies the "Home Page" is displayed  
    
    
    Examples:  
      | email                        | password       |                         
      | jayanthaws2000@gmail.com     | 9948640337@27a |
      
      
      