package by.epam.trainings.task6.util;

import org.apache.commons.codec.digest.DigestUtils;

public class Coder {
    	public static String hashMD5(String text) {
    		return DigestUtils.md5Hex(text);
    	}
}
