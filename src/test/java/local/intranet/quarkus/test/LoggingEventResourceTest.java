package local.intranet.quarkus.test;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class LoggingEventResourceTest {

	@Test
	public void testLoggingEventEndpoint() {
		given()
		.when().get("/app/v1/info/loggingEvent")
		.then().statusCode(200);
	}

}