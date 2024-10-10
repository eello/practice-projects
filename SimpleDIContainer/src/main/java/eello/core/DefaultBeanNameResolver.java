package main.java.eello.core;

public class DefaultBeanNameResolver {

	public static String resolve(String simpleClassName) {
		char[] charArray = simpleClassName.toCharArray();
		charArray[0] = Character.toLowerCase(charArray[0]);
		return new String(charArray);
	}
}
