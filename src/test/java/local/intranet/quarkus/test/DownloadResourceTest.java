package local.intranet.quarkus.test;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class DownloadResourceTest {

	@Test
	public void testDownloadEndpoint() {
		given()
		.when().get("/downloads/platypus.quarkus")
		.then().statusCode(404);
	}

}