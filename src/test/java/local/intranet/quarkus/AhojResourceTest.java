package local.intranet.quarkus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.DisabledOnIntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.security.TestSecurity;
import local.intranet.quarkus.api.controller.IndexController;

@QuarkusTest
@TestProfile(CustomProfile.class)
public class AhojResourceTest {

	@Test
        @DisabledOnIntegrationTest
	@TestSecurity(user = "admin", roles = { "userRole", "adminRole" })
	public void testAhojEndpoint() {
		given().param("query", "Platypus Ahooooj").when().get("/ahoj").then().statusCode(200).body("content",
				is(IndexController.AHOJ));
	}

}
