package local.intranet.quarkus.test;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class JobResourceTest {

	@Test
	public void testJobEndpoint() {
		given()
        .when().get("/jobCounter")
        .then()
        .statusCode(200);
	}

}