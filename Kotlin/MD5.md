
```

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class SecurityUtils {

	// /** 算法/模式/填充 **/
	private static final String CipherMode = "AES/CBC/PKCS5Padding";

	/**
	  * 
	  * MD5加密
	 */
	public static String md5(String string){
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] bytes = string.getBytes();
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(bytes);
			byte[] updateBytes = messageDigest.digest();
			int len = updateBytes.length;
			char myChar[] = new char[len * 2];
			int k = 0;
			for (int i = 0; i < len; i++) {
				byte byte0 = updateBytes[i];
				myChar[k++] = hexDigits[byte0 >>> 4 & 0x0f];
				myChar[k++] = hexDigits[byte0 & 0x0f];
			}
			return new String(myChar);
		} catch (Exception e) {
			return string;
		}
	}

	//  加密结果为base64编码后字符串
	public static String encryptAES(String content, String password, String iv) {
		byte[] data = null;
		byte[] keyPassword = new byte[0];
		try {
			data = content.getBytes("UTF-8");
			keyPassword = Base64.decode(password,Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}

		data = encryptAES(data, keyPassword, iv);
		String result = Base64.encodeToString(data,Base64.DEFAULT);//  base64编码后;
		return result;
	}


	// 加密字节数据
	public static byte[] encryptAES(byte[] content, byte[] password, String iv) {
		try {
			SecretKeySpec key = createKey(password);
			Cipher cipher = Cipher.getInstance(CipherMode);
			cipher.init(Cipher.ENCRYPT_MODE, key, createIV(iv));
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static IvParameterSpec createIV(String password) {
		byte[] data = null;
		if (password == null) {
			password = "";
		}
		StringBuffer sb = new StringBuffer(16);
		sb.append(password);
		while (sb.length() < 16) {
			sb.append("0");
		}
		if (sb.length() > 16) {
			sb.setLength(16);
		}

		try {
			data = sb.toString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new IvParameterSpec(data);
	}

	private static SecretKeySpec createKey(byte[] data){
		return new SecretKeySpec(data, "AES");
	}


	public static String md5ForFansCard(String string){
		return md5(string+"o5uqRJL6SmqPBsx3LHiT");
	}

}

```
