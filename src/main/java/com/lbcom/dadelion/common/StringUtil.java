package com.lbcom.dadelion.common;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @description
 * @author liubin
 * @date 2015年12月8日 上午10:19:12
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class StringUtil {

	/**
	 * 判断字符串是否是空 isNullOrEmpty(这里用一句话描述这个方法的作用)
	 */
	public static boolean isNullOrEmpty(String resStr) {
		boolean ret = false;
		if (null == resStr || resStr.isEmpty()) {
			ret = true;
		}
		return ret;
	}

	/**
	 * 判断字符串不为空 isNotNullOrEmpty(这里用一句话描述这个方法的作用)
	 */
	public static boolean isNotNullOrEmpty(String resStr) {
		boolean ret = true;
		if (null == resStr || resStr.isEmpty()) {
			ret = false;
		}
		return ret;
	}

	/**
	 * @description 字符串不为null 并且不为空字符串
	 * @return boolean
	 */
	public static boolean isNotNullOrTrimEmpty(String str) {
		return !(str == null || str.trim().isEmpty());
	}

	/**
	 * 将字符串分割成list splitStr(这里用一句话描述这个方法的作用)
	 */
	public static List<String> splitStr(String resStr, String reg) {
		String[] temp = resStr.split(reg);
		return Arrays.asList(temp);
	}

	/**
	 * Convert byte[] to hex
	 * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
	 * 
	 * @param src
	 *            byte[] data
	 * @return hex string
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * SuppressWarnings压制警告，即去除警告 
	 * rawtypes是说传参时也要传递带泛型的参数
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean  isNULLOrEmpty(List list){
		boolean ret = false;
		if(null == list || list.isEmpty()){
			ret = true;
		}
		return ret;
	}

	public static boolean isNULLOrEmpty(Set<String> scanResult) {
		boolean ret = false;
		if(null == scanResult || scanResult.isEmpty()){
			ret = true;
		}
		return ret;
	}
	
}
