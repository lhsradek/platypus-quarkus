package local.intranet.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class NazdarResourceTest {

    @Test
    public void testNazdarEndpoint() {
        given()
          .when().get("/Nazdar")
          .then()
             .statusCode(200)
             .body(is("Nazdar from RESTEasy Reactive"));
    }

}