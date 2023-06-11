package local.intranet.quarkus;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.net.URL;

import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.DisabledOnIntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.http.ContentType;
import local.intranet.quarkus.api.controller.StatusController;

@QuarkusTest
@TestProfile(CustomProfile.class)
public class StatusResourceTest {

	/**
	 * 
	 * {@link StatusController}
	 */
	@Inject
	protected StatusController statusController;

	/**
	 * 
	 * <code>quarkus.application.artifactId</code> from application.properties
	 * 
	 */
	@ConfigProperty(name = "platypus.application.artifactId")
	protected String quarkusApplicationArtifactId;

	@TestHTTPResource("/app/v1/status/statusCounter")
	private URL statusEndpoint;

	@Test
        @DisabledOnIntegrationTest
	void testPlainStatus() {
		assertFalse(statusController.plainStatus() == null);
	}

	@Test
        @DisabledOnIntegrationTest
	public void testStatusEndpoint() {
		given().contentType(ContentType.JSON).param("query", "Platypus Plastic").when().get(statusEndpoint).then()
				.statusCode(200);
	}

	@Test
        @DisabledOnIntegrationTest
	public void testIndexHtml() throws IOException {
		given().contentType(ContentType.HTML).param("query", "Platypus Sapiens").get(statusEndpoint);
	}

}
