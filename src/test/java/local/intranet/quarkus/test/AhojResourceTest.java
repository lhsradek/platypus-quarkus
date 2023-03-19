package local.intranet.quarkus.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import local.intranet.quarkus.api.controller.IndexController;

@QuarkusTest
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