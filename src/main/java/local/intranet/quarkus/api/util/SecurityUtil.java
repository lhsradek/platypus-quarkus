package local.intranet.quarkus.api.util;

import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.wildfly.security.password.Password;
import org.wildfly.security.password.PasswordFactory;
import org.wildfly.security.password.interfaces.BCryptPassword;
import org.wildfly.security.password.util.ModularCrypt;

/**
 * 
 * {@link SecurityUtil} for
 * {@link local.intranet.quarkus.api.controller.IndexController}
 * 
 * <p>
 * https://www.baeldung.com/java-aes-encryption-decryption <br>
 * https://github.com/eugenp/tutorials/blob/master/core-java-modules/core-java-security-algorithms/src/main/java/com/baeldung/aes/AESUtil.java
 * 
 */
public class SecurityUtil {

	private static final String AESUTIL_AES = "AES";
	private static final String AESUTIL_AES_PADDING = "AES/CBC/PKCS5Padding";
	private static final String AESUTIL_PBKDF2_WITH_HMAC_SHA256 = "PBKDF2WithHmacSHA256";

	/**
	 * 
	 * Encrypt
	 * 
	 * @param input {@link String}
	 * @param key   {@link SecretKey}
	 * @param iv    {@link IvParameterSpec}
	 * @return {@link String}
	 * @throws NoSuchPaddingException             {@link NoSuchPaddingException}
	 * @throws NoSuchAlgorithmException           {@link NoSuchAlgorithmException}
	 * @throws InvalidAlgorithmParameterException {@link InvalidAlgorithmParameterException}
	 * @throws InvalidKeyException                {@link InvalidKeyException}
	 * @throws BadPaddingException                {@link BadPaddingException}
	 * @throws IllegalBlockSizeException          {@link IllegalBlockSizeException}
	 */
	public static String encrypt(String input, SecretKey key, IvParameterSpec iv)
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		final Cipher cipher = Cipher.getInstance(AESUTIL_AES_PADDING);
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		final byte[] cipherText = cipher.doFinal(input.getBytes());
		final String ret = Base64.getEncoder().encodeToString(cipherText);
		return ret;
	}

	/**
	 * 
	 * Decrypt
	 * 
	 * @param cipherText {@link String}
	 * @param key        {@link SecretKey}
	 * @param iv         {@link IvParameterSpec}
	 * @return {@link String}
	 * @throws NoSuchPaddingException             {@link NoSuchPaddingException}
	 * @throws NoSuchAlgorithmException           {@link NoSuchAlgorithmException}
	 * @throws InvalidAlgorithmParameterException {@link InvalidAlgorithmParameterException}
	 * @throws InvalidKeyException                {@link InvalidKeyException}
	 * @throws BadPaddingException                {@link BadPaddingException}
	 * @throws IllegalBlockSizeException          {@link IllegalBlockSizeException}
	 */
	public static String decrypt(String cipherText, SecretKey key, IvParameterSpec iv)
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		final Cipher cipher = Cipher.getInstance(AESUTIL_AES_PADDING.toUpperCase(Locale.US));
		cipher.init(Cipher.DECRYPT_MODE, key, iv);
		final byte[] ret = cipher.doFinal(Base64.getDecoder().decode(cipherText));
		return new String(ret);
	}

	/**
	 * 
	 * Generate key
	 * 
	 * @param n (128, 192 a 256)
	 * @return {@link SecretKey}
	 */
	public static SecretKey generateKey(int n) {
		try {
			final KeyGenerator keyGenerator = KeyGenerator.getInstance(AESUTIL_AES);
			keyGenerator.init(n);
			final SecretKey ret = keyGenerator.generateKey();
			return ret;
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	/**
	 * 
	 * Generate salt
	 * 
	 * @return {@link String}
	 */
	public static String generateSalt() {
		final SecureRandom random = new SecureRandom();
		final byte ret[] = new byte[20];
		random.nextBytes(ret);
		return new String(ret);
	}

	/**
	 * 
	 * Get key from password
	 * 
	 * @param password {@link String}
	 * @param salt     {@link String}
	 * @return {@link SecretKey}
	 * @throws NoSuchAlgorithmException {@link NoSuchAlgorithmException}
	 * @throws InvalidKeySpecException  {@link InvalidKeySpecException}
	 */
	public static SecretKey getKeyFromPassword(String password, String salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		final SecretKeyFactory factory = SecretKeyFactory.getInstance(AESUTIL_PBKDF2_WITH_HMAC_SHA256);
		final KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
		final SecretKey ret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), AESUTIL_AES);
		return ret;
	}

	/**
	 * 
	 * Generate Iv
	 * 
	 * @return {@link IvParameterSpec}
	 */
	public static IvParameterSpec generateIv() {
		final byte[] iv = new byte[16];
		new SecureRandom().nextBytes(iv);
		final IvParameterSpec ret = new IvParameterSpec(iv);
		return ret;
	}

	/**
	 * 
	 * Verify BCrypt password
	 * 
	 * @param originalPwd  {@link String}
	 * @param encryptedPwd {@link String}
	 * @return boolean
	 * @throws InvalidKeySpecException  {@link InvalidKeySpecException}
	 * @throws NoSuchAlgorithmException {@link NoSuchAlgorithmException}
	 * @throws InvalidKeyException      {@link InvalidKeyException}
	 */
	public static boolean verifyBCryptPassword(String originalPwd, String encryptedPwd)
			throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException {
		// convert encrypted password string to a password key
		final Password rawPassword = ModularCrypt.decode(encryptedPwd);
		// create the password factory based on the bcrypt algorithm
		final PasswordFactory factory = PasswordFactory.getInstance(BCryptPassword.ALGORITHM_BCRYPT);
		// create encrypted password based on stored string
		final BCryptPassword restored = (BCryptPassword) factory.translate(rawPassword);
		// verify restored password against original
		return factory.verify(restored, originalPwd.toCharArray());
	}

	/**
	 * 
	 * Get plain text from Base64
	 * 
	 * @param data base64
	 * @return plain text
	 */
	public static String getBase64(String data) {
		final String ret = new String(Base64.getDecoder().decode(data));
		return ret;
	}

	/**
	 * 
	 * Set to Base64
	 * 
	 * @param data plain text
	 * @return base64
	 */
	public static String setBase64(String data) {
		final String ret = new String(Base64.getEncoder().encode(data.getBytes(Charset.forName("UTF-8"))));
		return ret;
	}

	/**
	 * 
	 * Get plain text from hex
	 * 
	 * @param data as hex
	 * @return plain text
	 */
	public static String getHex(String data) {
		final String ret =  new String(DatatypeConverter.parseHexBinary(data.toLowerCase(Locale.US)), Charset.forName("UTF-8"));
		return ret;
	}

	/**
	 * 
	 * Set to Hex
	 * 
	 * @param data plain text
	 * @return hex
	 */
	public static String setHex(String data) {
		final String ret = new String(DatatypeConverter.printHexBinary(data.getBytes(Charset.forName("UTF-8")))).toLowerCase(Locale.US);
		return ret;
	}
	
}
