package noam.utils;

public class StringHelper {
	public static Function<String, String> addPrefix(final String prefix){
		return new Function<String, String>(){
			public String apply(String s) {
				return prefix + s;
			}
		};
	}
	
	public static Function<String, String> removePrefix(final String prefix){
		return new Function<String, String>(){
			public String apply(String s) {
				if (s.startsWith(prefix))
					return s.substring(prefix.length());
				else
					throw new RuntimeException();
			}			
		};
	}
}
