package local.intranet.quarkus;

import io.quarkus.test.junit.DisabledOnIntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(CustomProfile.class)
@DisabledOnIntegrationTest
public class VertxResourceTest {

	/*
	@Test
	public void testAhojVertxEndpoint() {
		given().param("query", "Platypus Vertx Ahooooj").when().get("/vertx/ahoj").then().statusCode(200)
				.body(is("Ahoj"));
	}

	@Test
	public void testHelloVertxEndpoint() {
		given().param("query", "Platypus Vertx Hellooooo").when().get("/vertx/hello").then().statusCode(200)
				.body(is("Hello"));
	}

	@Test
	public void testPLatypusVertxEndpoint() {
		given().param("query", "Platypus Vertx Sapiens").when().get("/vertx/platypus").then().statusCode(200);
	}

	@Test
	public void testQuarkusVertxEndpoint() {
		given().param("query", "Platypus Vertx Quarkus").when().get("/vertx/quarkus").then().statusCode(200);
	}
	 */

}
