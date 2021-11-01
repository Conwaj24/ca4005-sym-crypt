import java.security.*;
import static utils.Utils.*;
import java.util.Arrays;
import java.math.BigInteger;

class Assignment1 {//} implements Assignment1Interface {
	public static void main( String args[] ) {
		System.out.println(modExp( biggify(123), biggify(5), biggify(511)));
		System.out.println(
				generateKey(utf8Bytes("password"), utf8Bytes("salt"))
		);
	}

	/**
	 * The password (p) and salt (s) will be concatenated together (p||s) and hashed 200 times using SHA-256.
	 * The resulting digest (H200(p||s)) will then be used as your 256-bit AES key (k).
	 */
	static byte[] generateKey(byte[] password, byte[] salt) {
		return successiveSha256sum( concatenate(password, salt), 200 );
	 }
	
//	static byte[] encryptAES(byte[] plaintext, byte[] iv, byte[] key) {
//	
//	}
//	
//	/* AES decryption of the given ciphertext using the given iv and key */
//	byte[] decryptAES(byte[] ciphertext, byte[] iv, byte[] key) {
//	
//	}
//			
//	/* encryption of the given plaintext using the given encryption exponent and modulus */
//	byte[] encryptRSA(byte[] plaintext, BigInteger exponent, BigInteger modulus) {
//	
//	}
	 
//	/* result of raising the given base to the power of the given exponent using the given modulus */

	static boolean nthBit(BigInteger i, int n) {
		return !BigInteger.ZERO.equals(
				i.and(biggify(2)).pow(i.bitLength() - 1 - n)
		);
	}

	static BigInteger modExp(BigInteger base, BigInteger exponent, BigInteger modulus) {
		BigInteger y = biggify(1);
		for(int i = exponent.bitLength()-1; i >= 0; i--) {
			y = y.multiply(y).remainder(modulus);
			if (nthBit(base, i))
				y = y.multiply(base).remainder(modulus);
			System.out.println(y);

		}
		return y;
	}
/*
*/

}
