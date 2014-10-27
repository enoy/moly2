package com.gary.framework.util;

import java.util.Random;

public class JSUtil {
	
	/**
	 * Math.random()
	 * @return
	 */
	public static String getMathRandom(){
//		return String.valueOf(new Random().nextDouble() * (end-start) + start);
		return String.valueOf(new Random().nextDouble());
	}
}
