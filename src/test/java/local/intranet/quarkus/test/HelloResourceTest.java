package local.intranet.quarkus.test;

import io.quarkus.test.junit.QuarkusTest;
import local.intranet.quarkus.api.controller.IndexController;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class HelloResourceTest {

	@Test
	public void testHelloEndpoint() {
		given()
		.when().get("/hello")
		.then().statusCode(200);
	}

}