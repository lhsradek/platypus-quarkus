package local.intranet.quarkus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;

import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.http.ContentType;
import local.intranet.quarkus.api.controller.StatusController;

@QuarkusTest
@TestProfile(CustomProfile.class)
public class StatusResourceTest {

	@Inject
    StatusController statusController;
	
	/**
	 * 
	 * <code>quarkus.version</code> from application.properties
	 * 
	 */
	@ConfigProperty(name = "platypus.quarkus.version")
	public String quarkusVersion;
	
	/**
	 * 
	 * <code>quarkus.application.artifactId</code> from application.properties
	 * 
	 */
	@ConfigProperty(name = "platypus.application.artifactId")
	public String quarkusApplicationArtifactId;
	
	@TestHTTPResource("/app/v1/status/counter")
	protected URL statusEndpoint;
	
	@Test
    void testPlainStatus() {
        assertFalse(statusController.plainStatus() == null);
	}
	
    @Test
	public void testStatusEndpoint() {
		given().contentType(ContentType.JSON)
		.param("query", "Platypus Plastic")
        .when().get(statusEndpoint)
        .then().statusCode(200);
	}

    public void testIndexHtml() throws IOException {
		given().contentType(ContentType.HTML)
		.param("query", "Platypus Webus")
        .when().get(statusEndpoint)
        .then().statusCode(200)
        .body("title", is(MessageFormat.format("{0} - {1}", quarkusApplicationArtifactId, quarkusVersion)));
    }
    
}