package local.intranet.quarkus;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import local.intranet.quarkus.api.scheduler.PlatypusJob;


@QuarkusTest
@TestProfile(CustomProfile.class)
public class JobCounterResourceTest {

	/**
	 * 
	 * {@link PlatypusJob} 
	 */
	@Inject
    protected PlatypusJob platypusJob;
	
	@TestHTTPResource("/jobCounter")
	private URL jobEndpoint;
	
	@Test
    void testPlatypusJob() {
        assertTrue(platypusJob.testJob());
	}
	
	@Test
	public void testJobStart() {
		assertTrue(platypusJob.startJob());
    }
		
}