package com.liu.common.utils;

import java.security.MessageDigest;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.liu.common.exception.EncodeException;



/**
 * 加密机
 * 
 * @author chenhuiran
 * 
 */
public class Encoder {
	static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 通过公钥加密
	 * 
	 * @param publicKey
	 * @param obj
	 * @return byte[]
	 * @throws EncodeException
	 */
	public static byte[] encrypt(RSAPublicKey publicKey, byte[] obj)
			throws EncodeException {
		if (publicKey == null) {
			throw new EncodeException();
		}
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(obj);
		} catch (Exception e) {
			throw new EncodeException(e);
		}
	}

	/**
	 * 通过私钥加密
	 * 
	 * @param privateKey
	 * @param obj
	 * @return byte[]
	 * @throws EncodeException
	 */
	public static byte[] encrypt(RSAPrivateKey privateKey, byte[] obj)
			throws EncodeException {
		if (privateKey == null) {
			throw new EncodeException();
		}
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			return cipher.doFinal(obj);
		} catch (Exception e) {
			throw new EncodeException(e);
		}
	}

	/**
	 * 通过私钥解密
	 * 
	 * @param privateKey
	 * @param obj
	 * @return byte[]
	 * @throws EncodeException
	 */
	public static byte[] decrypt(RSAPrivateKey privateKey, byte[] obj)
			throws EncodeException {
		if (privateKey == null) {
			throw new EncodeException();
		}
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(obj);
		} catch (Exception e) {
			throw new EncodeException(e);
		}
	}

	/**
	 * 通过公钥解密
	 * 
	 * @param publicKey
	 * @param obj
	 * @return byte[]
	 * @throws EncodeException
	 */
	public static byte[] decrypt(RSAPublicKey publicKey, byte[] obj)
			throws EncodeException {
		if (publicKey == null) {
			throw new EncodeException();
		}
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			return cipher.doFinal(obj);
		} catch (Exception e) {
			throw new EncodeException(e);
		}
	}

	/**
	 * md5 不可逆摘要 + Base64摘要加密
	 * 
	 * @param obj
	 * @return String
	 */
	public static String summaryMD5AndBase64(byte[] obj) {
		byte[] digest = abstractSummary(obj, "MD5");
		return summaryBase64(digest);
	}

	/**
	 * md5 不可逆摘要 + Base64摘要加密
	 * 
	 * @param originalString
	 * @return String
	 */
	public static String summaryMD5AndBase64(String originalString) {
		return summaryMD5AndBase64(originalString.getBytes());
	}

	/**
	 * MD5摘要指纹方法，返回长度为32
	 * 
	 * @param originalString
	 * @return String
	 */
	public static String summaryMD5_32(String originalString) {
		return summaryMD5_32(originalString.getBytes());
	}

	/**
	 * MD5摘要指纹方法，返回长度为32
	 * 
	 * @param obj
	 * @return String
	 */
	public static String summaryMD5_32(byte[] obj) {
		byte[] digest = abstractSummary(obj, "MD5");
		return byteToHexString(digest);
	}

	/**
	 * MD5摘要指纹方法，返回长度16
	 * 
	 * @param originalString
	 * @return String
	 */
	public static String summaryMD5_16(String originalString) {
		return summaryMD5_16(originalString.getBytes());
	}

	/**
	 * MD5摘要指纹方法，返回长度16
	 * 
	 * @param originalString
	 * @return String
	 */
	public static String summaryMD5_16(byte[] obj) {
		//[49, 53, 54, 53, 56, 51, 51, 55, 51, 55, 48, 53, 56, 48, 46, 54, 49, 57, 50, 55, 51, 53, 57, 48, 57, 56, 52, 55, 49, 54, 50, 106, 97, 118, 97, 46, 108, 97, 110, 103, 46, 79, 98, 106, 101, 99, 116, 64, 53, 51, 57, 53, 49, 48]
		//MD5摘要指纹方法，返回长度16
		String originalMd5 = summaryMD5_32(obj);
		if (StringUtil.isNotEmpty(originalMd5) && originalMd5.length() > 24)
			return originalMd5.substring(8, 24);
		else
			return "";
	}

	/**
	 * HSA512摘要
	 * 
	 * @param obj
	 * @return String
	 */
	public static String summarySHA512(byte[] obj) {
		byte[] digest = abstractSummary(obj, "SHA-512");
		return byteToHexString(digest);
	}

	/**
	 * HSA512摘要
	 * 
	 * @param obj
	 * @return String
	 */
	public static String summarySHA512(String originalString) {
		return summarySHA512(originalString.getBytes());
	}

	/**
	 * 摘要指纹方法
	 * 
	 * @param obj
	 *            要被摘要的原数据，byte[]
	 * @param algorithm
	 *            摘要算法，可以为以下几种：
	 *            <li><b>MD2</b> The MD2 message digest algorithm as defined
	 *            in RFC 1319.
	 *            <li><b>MD5</b> The MD5 message digest algorithm as defined
	 *            in RFC 1321.
	 *            <li><b>SHA-1 SHA-256 SHA-384 或 SHA-512 </b>SHA-1 SHA-256
	 *            SHA-384 SHA-512 Hash algorithms defined in the FIPS PUB 180-2.
	 *            SHA-256 is a 256-bit hash function intended to provide 128
	 *            bits of security against collision attacks, while SHA-512 is a
	 *            512-bit hash function intended to provide 256 bits of
	 *            security. A 384-bit hash may be obtained by truncating the
	 *            SHA-512 output.
	 * 
	 * @return 加密后的byte[]
	 */
	public static byte[] abstractSummary(byte[] obj, String algorithm) {
		if (obj == null || obj.length == 0) {
			return null;
		}
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(obj);
			return md.digest();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Base64摘要指纹方法
	 * 
	 * @param digest
	 *            要被摘要的原数据byte[]
	 * @return String 摘要后的字符串
	 */
	public static String summaryBase64(byte[] digest) {
		if (digest == null || digest.length < 1) {
			return "";
		}
		BASE64Encoder base64 = new BASE64Encoder();
		String base64String = base64.encode(digest);
		return base64String;
	}
	
	/**
	 * Base64摘要指纹解密方法
	 * 
	 * @param String
	 *            解密前的数据
	 * @return String 解密后的字符串
	 */
	public static String decodeBase64(String digest) {
		if (StringUtil.isEmpty(digest)) {
			return "";
		}
		BASE64Decoder base64 = new BASE64Decoder();
		try{
			byte[] base64byte = base64.decodeBuffer(digest);
			return new String(base64byte);
		}catch(Exception e){
			return "";
		}
	}


	/**
	 * 把byte[]数组转换成十六进制字符串表示形式
	 * 
	 * @param tmp
	 *            要转换的byte[]
	 * @return 十六进制字符串表示形式
	 */
	public static String byteToHexString(byte[] tmp) {
		String s = "";
		if (tmp != null || tmp.length > 1) {
			char str[] = new char[tmp.length * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < tmp.length; i++) { // 从第一个字节开始，对 tmp 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				// >>> 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串
		}
		return s;
	}
	
	public static void main(String args []){
		System.out.println(decodeBase64(
				"PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz48cm9vdD48cmRhdGE+PG9wbWFp"+
				"biBhY3Rpb249ItDC1PYiIGRhdGFpZD0iNjA5OTYwOTUwNDdfX19fX19fX182MDk5NjA5MTA2NzM1"+
				"M19fX19fXzIwMDMuMTEyMDAzLjEyIiBkYnRhYmxlPSJHUl9TWUpGX1pITkIiIGRhdGU9IjIwMDku"+
				"MDQuMDkgMTQ6MDM6MDEiIGFwcGlkPSI2NCIgbW9kdWxlPSJHUl9aSEhfU0hJWUpGX0JUIiBhY3Rp"+
				"b25pbmZvPSJbuPbIy9XKu6fKp9K1vcm30bK5zO5dW831t7JdW7j2yMvVyrunyqfStb3Jt9G8x8K8"+
				"XVvQwtT2XSIvPjxvcGRhdGE+PGRhdGEgYmVmb3JlPSI2MDAwMzA5NCIgYWZ0ZXI9IjYwMDAzMDk0"+
				"Ii8+PGRhdGEgYmVmb3JlPSKxsb6pyKvQy7TFv6jM2Mri06HLotPQz965q8u+IiBhZnRlcj0isbG+"+
				"qcir0Mu0xb+ozNjK4tOhy6LT0M/euavLviIvPjxkYXRhIGJlZm9yZT0iMjIwNjAyMTk4NzA3MjEy"+
				"NDE0IiBhZnRlcj0iMjIwNjAyMTk4NzA3MjEyNDE0Ii8+PGRhdGEgYmVmb3JlPSLW3LqjvvwiIGFm"+
				"dGVyPSLW3LqjvvwiLz48ZGF0YSBiZWZvcmU9IiIgYWZ0ZXI9IjEwOTAyMDA5MDQwOTAwMDEiLz48"+
				"ZGF0YSBiZWZvcmU9IiIgYWZ0ZXI9IjIwMDMuMTEuMDEiLz48ZGF0YSBiZWZvcmU9IiIgYWZ0ZXI9"+
				"IjIwMDMuMTIuMDEiLz48ZGF0YSBiZWZvcmU9IiIgYWZ0ZXI9IjIwMDMiLz48ZGF0YSBiZWZvcmU9"+
				"IiIgYWZ0ZXI9IjkzMC4wMCIvPjxkYXRhIGJlZm9yZT0iIiBhZnRlcj0iMiIvPjxkYXRhIGJlZm9y"+
				"ZT0iIiBhZnRlcj0iMC4wMCIvPjxkYXRhIGJlZm9yZT0iIiBhZnRlcj0iMTMuOTYiLz48L29wZGF0"+
				"YT48L3JkYXRhPjxyZGF0YT48b3BtYWluIGFjdGlvbj0i0MLU9iIgZGF0YWlkPSI2MDk5NjA5NTA0"+
				"N19fX19fX19fXzYwOTk2MDkxMDY3MzUzX19fX19fMjAwNC4wMTIwMDQuMDMiIGRidGFibGU9IkdS"+
				"X1NZSkZfWkhOQiIgZGF0ZT0iMjAwOS4wNC4wOSAxNDowMzowMSIgYXBwaWQ9IjY0IiBtb2R1bGU9"+
				"IkdSX1pISF9TSElZSkZfQlQiIGFjdGlvbmluZm89Ilu49sjL1cq7p8qn0rW9ybfRsrnM7l1bzfW3"+
				"sl1buPbIy9XKu6fKp9K1vcm30bzHwrxdW9DC1PZdIi8+PG9wZGF0YT48ZGF0YSBiZWZvcmU9IjYw"+
				"MDAzMDk0IiBhZnRlcj0iNjAwMDMwOTQiLz48ZGF0YSBiZWZvcmU9IrGxvqnIq9DLtMW/qMzYyuLT"+
				"ocui09DP3rmry74iIGFmdGVyPSKxsb6pyKvQy7TFv6jM2Mri06HLotPQz965q8u+Ii8+PGRhdGEg"+
				"YmVmb3JlPSIyMjA2MDIxOTg3MDcyMTI0MTQiIGFmdGVyPSIyMjA2MDIxOTg3MDcyMTI0MTQiLz48"+
				"ZGF0YSBiZWZvcmU9ItbcuqO+/CIgYWZ0ZXI9ItbcuqO+/CIvPjxkYXRhIGJlZm9yZT0iIiBhZnRl"+
				"cj0iMTA5MDIwMDkwNDA5MDAwMSIvPjxkYXRhIGJlZm9yZT0iIiBhZnRlcj0iMjAwNC4wMS4wMSIv"+
				"PjxkYXRhIGJlZm9yZT0iIiBhZnRlcj0iMjAwNC4wMy4wMSIvPjxkYXRhIGJlZm9yZT0iIiBhZnRl"+
				"cj0iMjAwNCIvPjxkYXRhIGJlZm9yZT0iIiBhZnRlcj0iMTM5NS4wMCIvPjxkYXRhIGJlZm9yZT0i"+
				"IiBhZnRlcj0iMyIvPjxkYXRhIGJlZm9yZT0iIiBhZnRlcj0iMC4wMCIvPjxkYXRhIGJlZm9yZT0i"+
				"IiBhZnRlcj0iMjAuOTQiLz48L29wZGF0YT48L3JkYXRhPjxyZGF0YT48b3BtYWluIGFjdGlvbj0i"+
				"0MLU9iIgZGF0YWlkPSI2MDk5NjA5NTA0N19fX19fX19fXzYwOTk2MDkxMDY3MzUzX19fX19fMjAw"+
				"NC4wNDIwMDQuMDgiIGRidGFibGU9IkdSX1NZSkZfWkhOQiIgZGF0ZT0iMjAwOS4wNC4wOSAxNDow"+
				"MzowMSIgYXBwaWQ9IjY0IiBtb2R1bGU9IkdSX1pISF9TSElZSkZfQlQiIGFjdGlvbmluZm89Ilu4"+
				"9sjL1cq7p8qn0rW9ybfRsrnM7l1bzfW3sl1buPbIy9XKu6fKp9K1vcm30bzHwrxdW9DC1PZdIi8+"+
				"PG9wZGF0YT48ZGF0YSBiZWZvcmU9IjYwMDAzMDk0IiBhZnRlcj0iNjAwMDMwOTQiLz48ZGF0YSBi"+
				"ZWZvcmU9IrGxvqnIq9DLtMW/qMzYyuLTocui09DP3rmry74iIGFmdGVyPSKxsb6pyKvQy7TFv6jM"+
				"2Mri06HLotPQz965q8u+Ii8+PGRhdGEgYmVmb3JlPSIyMjA2MDIxOTg3MDcyMTI0MTQiIGFmdGVy"+
				"PSIyMjA2MDIxOTg3MDcyMTI0MTQiLz48ZGF0YSBiZWZvcmU9ItbcuqO+/CIgYWZ0ZXI9ItbcuqO+"+
				"/CIvPjxkYXRhIGJlZm9yZT0iIiBhZnRlcj0iMTA5MDIwMDkwNDA5MDAwMSIvPjxkYXRhIGJlZm9y"+
				"ZT0iIiBhZnRlcj0iMjAwNC4wNC4wMSIvPjxkYXRhIGJlZm9yZT0iIiBhZnRlcj0iMjAwNC4wOC4w"+
				"MSIvPjxkYXRhIGJlZm9yZT0iIiBhZnRlcj0iMjAwNCIvPjxkYXRhIGJlZm9yZT0iIiBhZnRlcj0i"+
				"MjMyNS4wMCIvPjxkYXRhIGJlZm9yZT0iIiBhZnRlcj0iNSIvPjxkYXRhIGJlZm9yZT0iIiBhZnRl"+
				"cj0iMC4wMCIvPjxkYXRhIGJlZm9yZT0iIiBhZnRlcj0iMzQuOTAiLz48L29wZGF0YT48L3JkYXRh"+
				"Pjwvcm9vdD4="
		));
	}

	
}