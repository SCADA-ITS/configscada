package com.revenga.rits.back.data.core.util;

import java.lang.management.ManagementFactory;
import java.util.regex.Pattern;

public class RuntimeUtil {
	
	private static final Pattern debugPattern = Pattern.compile("-Xdebug|jdwp");
	
	private RuntimeUtil() {

		throw new IllegalStateException(this.getClass().getSimpleName());
	}

	public static boolean isDebugging() {
	    for (String arg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
	        if (debugPattern.matcher(arg).find()) {
	            return true;
	        }
	    }
	    return false;
	}
}
