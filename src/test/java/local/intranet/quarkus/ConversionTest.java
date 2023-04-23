package local.intranet.quarkus;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import local.intranet.quarkus.api.util.SecurityUtil;

public class ConversionTest {

	@Test
	public void testConversion() {
		
		// a bit of Dadaism
		
		assertEquals("We have a stuffed grandfather in the closet.", SecurityUtil
				.getBase64("V2UgaGF2ZSBhIHN0dWZmZWQgZ3JhbmRmYXRoZXIgaW4gdGhlIGNsb3NldC4="));
		
		assertEquals("V2UgaGF2ZSBhIHN0dWZmZWQgZ3JhbmRmYXRoZXIgaW4gdGhlIGNsb3NldC4=", SecurityUtil
				.setBase64("We have a stuffed grandfather in the closet."));
		
		assertEquals("My cork badtub is like your giraffe rye!", SecurityUtil
				.getHex("4d7920636f726b20626164747562206973206c696b6520796f757220676972616666652072796521"));
		
		assertEquals("4d7920636f726b20626164747562206973206c696b6520796f757220676972616666652072796521", SecurityUtil
		 		.setHex("My cork badtub is like your giraffe rye!"));
	}

}