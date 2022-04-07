
Feature: Validating place APIs
  I want to use this template for my feature file

@AddPlace
Scenario Outline: Verify if place is being successfully added using addPlace API
    Given Add place payload with "<name>" "<language>" "<address>"
    When User calls "addPlaceAPI" with "post" http request
    Then API should get executed successfully with status code 200
    And "status" in response body is "OK"
		And "scope" in response body is "APP"
		And verify place_id created maps to "<name>" using "getPlaceAPI"
		
Examples:
				|name    | language  | address      |
				|AAhouse | English   | World center |
  		#	|BBhouse | Spanish   | Test center  |
  		
@DeletePlace  		
Scenario: Verify if delete place functionality is working
				Given DeletePlace payload
				When User calls "deletePlaceAPI" with "post" http request
				Then API should get executed successfully with status code 200
				And "status" in response body is "OK"