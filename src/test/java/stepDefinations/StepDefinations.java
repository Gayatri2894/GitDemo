package stepDefinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

//import java.io.FileNotFoundException;
import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
//import io.restassured.RestAssured;
//import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
//import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
//import pojo.AddPlace;
//import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefinations extends Utils{
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static String place_id;
	
	@Given("Add place payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
		// Write code here that turns the phrase above into concrete actions		
		// resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build(); -- not required as we are already checking if the response code is 200 or not

		res = given().spec(requestSpectification()).body(data.addPlacePayload(name,language,address));
	}

	@When("User calls {string} with {string} http request")
	public void user_calls_api_with_post_http_request(String resource, String method) {
		// Write code here that turns the phrase above into concrete actions
		
		//constructor will be called with valuOf() resource which we pass
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		
		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if(method.equalsIgnoreCase("POST"))
		 response=res.when().post(resourceAPI.getResource());
		else if (method.equalsIgnoreCase("GET"))
			response=res.when().post(resourceAPI.getResource());
	}

	@Then("API should get executed successfully with status code {int}")
	public void api_should_get_executed_successfully_with_status_code(Integer int1) {
		// Write code here that turns the phrase above into concrete actions
		
		assertEquals(response.getStatusCode(), 200);
		
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String Expectedvalue) {
		// Write code here that turns the phrase above into concrete actions
		
	//	js.get(Expectedvalue);
		assertEquals(getJsonPath(response, keyValue),Expectedvalue);
	}
	
	@Then("verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedname, String resource) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		
		// prepare request spec
		place_id = getJsonPath(response,"place_id");
		res = given().spec(requestSpectification()).queryParam("place_id",place_id);
		user_calls_api_with_post_http_request(resource,"GET");
		String actualname=getJsonPath(response,"name");
		assertEquals(actualname, expectedname);
	}
	
	@Given("DeletePlace payload")
	public void delete_place_payload() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		res = given().spec(requestSpectification()).body(data.deletePlacePayload(place_id));
	}


}
