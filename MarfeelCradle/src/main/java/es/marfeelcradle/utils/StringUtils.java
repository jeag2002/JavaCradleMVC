package es.marfeelcradle.utils;

import java.util.UUID;

public class StringUtils {
	
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}

}
