import java.security.*;
import static utils.Utils.*;
//import java.math.BigInteger;

class Assignment1 {//} implements Assignment1Interface {
	public static void main( String args[] ) {
		System.out.println(
				generateKey(utf8Bytes("password"), utf8Bytes("salt"))
		);
	}

	byte[] generateKey(byte[] password, byte[] salt) {
		byte[] key = new byte[password.length + salt.length];
		for(int i = 0; i <password.length; i++) {
			key[i] = password[i];
		}
		for(int i = 0; i <salt.length; i++) {
			key[i + password.length - 1] = salt[i];
		}

		for (int i = 0; i<2; i++) {
			key = sha256sum(key);
			//System.out.println(something);
		}

		return key;
	 }
	
//	/* AES encryption of the given plaintext using the given iv and key */
//	byte[] encryptAES(byte[] plaintext, byte[] iv, byte[] key) {
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
//	 
//	/* result of raising the given base to the power of the given exponent using the given modulus */
//	BigInteger modExp(BigInteger base, BigInteger exponent, BigInteger modulus) {
//	
//	}

}
