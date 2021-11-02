package utils;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.file.*;
import java.io.IOException;
import java.nio.charset.MalformedInputException;

public class Utils {
	public static byte[] sha256sum (byte[] b) {
		try {
			MessageDigest d = MessageDigest.getInstance("SHA-256");
			return d.digest(b);
		} catch (NoSuchAlgorithmException e) { die(e); }

		return b;
	}

	public static byte[] successiveSha256sum (byte[] b, int iterations) {
		byte[] out = sha256sum(b);
		for (int i = 1; i < iterations; i++) {
			out = sha256sum(out);
		}
		return out;
	}

	public static void die(Exception deathRattle) {
		System.err.println(deathRattle);
		System.exit(1);
	}

	public static byte[] utf8Bytes (String s) {
		return s.getBytes(StandardCharsets.UTF_8);
	}

	public static String utf8String (byte[] b) {
		return new String(b, StandardCharsets.UTF_8);
	}

	public static BigInteger biggify(int i) {
		return new BigInteger(intToByteArray(i));
	}

	/* taken from https://stackoverflow.com/a/2183259/7158192 */
	public static final byte[] intToByteArray(int value) {
	return new byte[] {
			(byte)(value >>> 24),
			(byte)(value >>> 16),
			(byte)(value >>> 8),
			(byte)value};
	}

	/* taken from https://stackoverflow.com/a/80503/7158192 */
	public static <T> T concatenate(T a, T b) {
		if (!a.getClass().isArray() || !b.getClass().isArray()) {
			throw new IllegalArgumentException();
		}

		Class<?> resCompType;
		Class<?> aCompType = a.getClass().getComponentType();
		Class<?> bCompType = b.getClass().getComponentType();

		if (aCompType.isAssignableFrom(bCompType)) {
			resCompType = aCompType;
		} else if (bCompType.isAssignableFrom(aCompType)) {
			resCompType = bCompType;
		} else {
			throw new IllegalArgumentException();
		}

		int al = Array.getLength(a);
		int bl = Array.getLength(b);

		@SuppressWarnings("unchecked")
		T out = (T) Array.newInstance(a.getClass().getComponentType(), al+bl );
		System.arraycopy(a, 0, out, 0, al);
		System.arraycopy(b, 0, out, al, bl);

		return out;
	}

	public static int bitsToBytes(int bits) {
		return bits / 8 + ((bits % 8 == 0) ? 0 : 1); //divide by 8 and round up
	}

	public static String readFile(String path) {
		try {
			return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
		} catch ( IOException e ) {
			die(e);
		}
		return "";
	}

	public static byte[] readFileBytes(String path) {
		try {
			return Files.readAllBytes(FileSystems.getDefault().getPath(path));
		} catch ( IOException e ) {
			die(e);
		}
		return new byte[16];
	}

	public static byte[] hexDecodeFile(String path) {
		return hexStringToByteArray(readFile(path));
	}

	/* taken from https://stackoverflow.com/a/140861 */
	/* s must be an even-length string. */
	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
		data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}
}
