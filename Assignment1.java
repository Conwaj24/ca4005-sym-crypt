import java.security.*;
import static utils.Utils.*;
import utils.JavaIsACruelJokeException;
import java.util.Arrays;
import java.math.BigInteger;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.*;

class Assignment1 implements Assignment1Interface {
	public static void main( String args[] ) {
		final BigInteger publicModulus = new BigInteger("137652547120171623831577580754018445993017304218165206272713364463792456896637113989329788703535234386034180124255655381988418908310009282516131873615137567204485155842487301882808765314419120804845170697501924015753046752363774869922950979952518437590219507132881645941023261582043811465190751407051926698921");
		final BigInteger encryptionExponent = biggify(65537);

		byte[] initializationVector = fixedLength(hexDecodeFile("IV.txt"), 128);
		byte[] salt = fixedLength(hexDecodeFile("Salt.txt"), 128);
		byte[] password = utf8Bytes("00000000000000000000000000000000");

		byte[] plaintext;
		try {
			plaintext = readFileBytes(args[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
			plaintext = utf8Bytes(e.toString());
		}

		Assignment1 ass = new Assignment1();

		byte[] key = ass.generateKey(password, salt);
		byte[] ciphertext = ass.encryptAES(plaintext, initializationVector, key);
		System.out.println( hexEncode(ciphertext));
		writeFile("Password.txt", hexEncode(ass.encryptRSA(password, encryptionExponent, publicModulus)));
	}

	/**
	 * The password (p) and salt (s) will be concatenated together (p||s) and hashed 200 times using SHA-256.
	 * The resulting digest (H200(p||s)) will then be used as your 256-bit AES key (k).
	 */
	public byte[] generateKey(byte[] password, byte[] salt) {
		return successiveSha256sum( concatenate(password, salt), 200 );
	 }

	public byte[] encryptAES(byte[] plaintext, byte[] iv, byte[] key) {
		try {
			return getAESCipher(Cipher.ENCRYPT_MODE, iv, key).doFinal(pad(plaintext, 128));
		} catch (Exception e) {
			die(e);
		}
		return plaintext;

	}

	public byte[] decryptAES(byte[] ciphertext, byte[] iv, byte[] key) {
		try {
			return getAESCipher(Cipher.DECRYPT_MODE, iv, key).doFinal(ciphertext);
		} catch (Exception e) {
			die(e);
		}
		return ciphertext;
	}

	public byte[] encryptRSA(byte[] plaintext, BigInteger exponent, BigInteger modulus) {
		return modExp(new BigInteger(plaintext), exponent, modulus).toByteArray();
	}

	public BigInteger modExp(BigInteger base, BigInteger exponent, BigInteger modulus) {
		BigInteger y = biggify(1);
		for(int i = exponent.bitLength()-1; i >= 0; i--) {
			y = y.multiply(y).remainder(modulus);
			if (exponent.testBit(i))
				y = y.multiply(base).remainder(modulus);
		}
		return y;
	}

	static Cipher getAESCipher(int mode, byte[] initializationVector, byte[] key) throws JavaIsACruelJokeException {
		try {
			Cipher c = Cipher.getInstance("AES/CBC/NoPadding");
			c.init(
					mode,
					new SecretKeySpec(key, "AES"),
					new IvParameterSpec(initializationVector)
				);
			return c;
		}
		catch(
				InvalidKeyException |
				InvalidAlgorithmParameterException |
				NoSuchPaddingException |
				NoSuchAlgorithmException e
		)
			{ throw new JavaIsACruelJokeException(); }
	}

	static byte[] pad (byte[] data, int bits) {
		return concatenate( data, new byte[ bits - data.length % bitsToBytes(bits) ] );
	}

	static byte[] fixedLength (byte[] data, int bits) {
		int bytes = bitsToBytes(bits);
		if (bytes > data.length)
			return concatenate( data, new byte[bytes - data.length] );
		return Arrays.copyOfRange(data, 0, bytes);
	}
}
