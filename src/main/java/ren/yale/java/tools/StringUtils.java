package ren.yale.java.tools;

public final class StringUtils {

	public final static boolean isEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
}
