package local.intranet.quarkus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(CustomProfile.class)
public class VertxResourceTest {

	@Test
	public void testAhojVertxEndpoint() {
		given()
		.param("query", "Platypus Vertx Ahooooj")
		.when().get("/vertx/ahoj")
        .then()
        .statusCode(200)
		.body(is("Ahoj"));
	}

	@Test
	public void testHelloVertxEndpoint() {
		given()
		.param("query", "Platypus Vertx Hellooooo")
		.when().get("/vertx/hello")
		.then()
        .statusCode(200)
		.body(is("Hello"));
	}
	
	@Test
	public void testPLatypusVertxEndpoint() {
		given()
		.param("query", "Platypus Vertx sapiens")
		.when().get("/vertx/platypus")
		.then()
		.statusCode(200);
	}
	
	@Test
	public void testQuarkusVertxEndpoint() {
		given()
		.param("query", "Platypus Vertx Quarkus")
        .when().get("/vertx/quarkus")
        .then()
        .statusCode(200);
	}

	/*
	@Test
	public void testPLatypusJobEndpoint() {
		given()
		.param("query", "Platypus Vertx Job")
		.put("/vertx/startJob");
	}
	*/
	
}