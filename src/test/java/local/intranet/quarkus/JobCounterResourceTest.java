package local.intranet.quarkus;

import static io.restassured.RestAssured.given;

import java.net.URL;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.http.ContentType;


@QuarkusTest
@TestProfile(CustomProfile.class)
public class JobCounterResourceTest {

	@TestHTTPResource("/jobCounter")
	private URL jobEndpoint;
	
	@Test
	public void testJobEndpoint() {
		given().contentType(ContentType.JSON)
		.param("query", "Platypus Quartz")
        .when().get(jobEndpoint)
        .then().statusCode(200);
		
		// assertTrue(indexController.jobCounter().contains());
		
	}

}