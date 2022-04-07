package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException
	{
		//write a code to get place id
		//execute this code only when the place id is null
		
		StepDefinations sd = new StepDefinations();
		
		if(StepDefinations.place_id==null)
		{
		sd.add_place_payload_with("Gayatri", "French", "India");
		sd.user_calls_api_with_post_http_request("addPlaceAPI", "POST");
		sd.verify_place_id_created_maps_to_using("Gayatri", "getPlaceAPI");
		}
	}

}
