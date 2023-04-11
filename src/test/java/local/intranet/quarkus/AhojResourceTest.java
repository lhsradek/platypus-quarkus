package local.intranet.quarkus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import local.intranet.quarkus.api.controller.IndexController;

@QuarkusTest
@TestProfile(CustomProfile.class)
public class AhojResourceTest {

	@Test
	public void testAhojEndpoint() {
		given()
        .when().get("/ahoj")
        .then()
        .statusCode(200)
        .body("content", is(IndexController.AHOJ));
	}

}