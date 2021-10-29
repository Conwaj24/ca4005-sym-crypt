package utils;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
	public static byte[] sha256sum (byte[] b) {
		try {
			MessageDigest d = MessageDigest.getInstance("SHA-256");
			return d.digest(b);
		} catch (NoSuchAlgorithmException e) { die(e); }

		return b;
	}
	public static void die(Exception deathRattle) {
		System.err.println(deathRattle);
		System.exit(1);
	}
	public static byte[] utf8Bytes (String s) {
		return s.getBytes(StandardCharsets.UTF_8);
	}

//	public <T> T[] concatenate(T[] a, T[] b) {
//		int aLen = a.length;
//		int bLen = b.length;


}
