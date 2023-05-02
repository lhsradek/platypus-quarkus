package local.intranet.quarkus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.security.GeneralSecurityException;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import local.intranet.quarkus.api.controller.IndexController;
import local.intranet.quarkus.api.util.SecurityUtil;

@QuarkusTest
public class ConversionTest {

	/**
	 * 
	 * <code>platypus.api.key</code>from application.properties or <code>.env</code>
	 * 
	 */
	@ConfigProperty(name = "platypus.api.key")
	protected String key;

	@Test
	public void testConversion() throws GeneralSecurityException {

		final String salt = SecurityUtil.generateSalt();
		final IvParameterSpec iv = SecurityUtil.generateIv();

		final SecretKey secretKey1 = SecurityUtil.getKeyFromPassword(key, salt);
		final SecretKey secretKey2 = SecurityUtil.getKeyFromPassword(key, salt);
		assertNotEquals(((SecretKeySpec) secretKey1).getEncoded(), ((SecretKeySpec) secretKey2).getEncoded());

		final String en = SecurityUtil.setHex(SecurityUtil.encrypt(IndexController.AHOJ, secretKey1, iv));
		final String de = SecurityUtil.decrypt(SecurityUtil.getHex(en), secretKey2, iv);
		assertEquals(IndexController.AHOJ, de);

		assertEquals(SecurityUtil.verifyBCryptPassword("myPassword",
                "$2a$12$2aiw6tVPaWiyE07ukBEHHOWehfXKM2.bN4aLa3ZNjStxyULHD9DPm"), true);
		
		// a bit of Dadaism

		assertEquals("We have a stuffed grandfather in the closet.",
				SecurityUtil.getBase64("V2UgaGF2ZSBhIHN0dWZmZWQgZ3JhbmRmYXRoZXIgaW4gdGhlIGNsb3NldC4="));

		assertEquals(SecurityUtil.setBase64("We have a stuffed grandfather in the closet."),
				"V2UgaGF2ZSBhIHN0dWZmZWQgZ3JhbmRmYXRoZXIgaW4gdGhlIGNsb3NldC4=");

		assertEquals("My cork badtub is like your giraffe rye!", SecurityUtil
				.getHex("4D7920636F726B20626164747562206973206C696B6520796F757220676972616666652072796521"));

		assertEquals(SecurityUtil.setHex("My cork badtub is like your giraffe rye!"),
				"4D7920636F726B20626164747562206973206C696B6520796F757220676972616666652072796521");

	}

}