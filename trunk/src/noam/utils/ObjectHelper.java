package noam.utils;

public class ObjectHelper {
	public static boolean safetyEquals(Object a, Object b){
		if (a == null || b == null)
			return a == null && b == null;
		else
			return a.equals(b);
	}
}
