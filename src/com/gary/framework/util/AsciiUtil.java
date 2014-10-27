package com.gary.framework.util;

/**
 * Ascii工具
 * @author gary
 *
 */
public class AsciiUtil {

	/**
	 * 获取字符串Ascii码
	 * @param str
	 * @return
	 */
	public static String encode(String str) {
		char[] temp = str.toCharArray();
		StringBuilder builder = new StringBuilder();
		for(char each : temp){
			builder.append((int)each);
		}
		return builder.toString();
	}
	
//	/**
//	 * 解码
//	 * @param str
//	 * @return
//	 */
//	public static String decode(String str) {
////		char[] temp = str.toCharArray();
//		StringBuilder builder = new StringBuilder();
//		for (int i = 0; i < str.length(); i+=2) {
//			int asciiCode = Integer.parseInt(str.charAt(i) + str.charAt(i + 1) + "");
//			builder.append((char)asciiCode);
//		}
//		return builder.toString();
//	}
	
	public static void main(String[] args) {
		System.out.println(encode("408036296@163.com"));
//		String asciiStr = "524856485154505754644954514699111109";
//		System.out.println(decode(asciiStr));
	}

}
