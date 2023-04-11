package local.intranet.quarkus;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(CustomProfile.class)
public class VertxResourceTest {

	@Test
	public void testVertxEndpoint() {
		given()
        .when().get("/vertx/web")
        .then()
        .statusCode(200);
	}

}