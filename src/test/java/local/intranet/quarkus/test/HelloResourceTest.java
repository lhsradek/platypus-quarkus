package local.intranet.quarkus.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import local.intranet.quarkus.api.controller.IndexController;

@QuarkusTest
@TestProfile(CustomProfile.class)
public class HelloResourceTest {

	@Test
	public void testHelloEndpoint() {
		given()
        .when().get("/hello")
        .then()
        .statusCode(200)
        .body("content", is(IndexController.HELLO));
	}

}