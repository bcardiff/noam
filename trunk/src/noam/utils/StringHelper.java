package noam.utils;

public class StringHelper {
	public static Function<String, String> addPrefix(final String prefix) {
		return new Function<String, String>() {
			public String apply(String s) {
				return prefix + s;
			}
		};
	}

	public static Function<String, String> removePrefix(final String prefix) {
		return new Function<String, String>() {
			public String apply(String s) {
				if (s.startsWith(prefix))
					return s.substring(prefix.length());
				else
					throw new RuntimeException();
			}
		};
	}

	/**
	 * i=0 => A, i=1 => B, ... i=26 => AA, i=27 => AB,...
	 * @param i
	 * @return
	 */
	public static String asString(int i) {
		int aCode = 'A';
		int zCode = 'Z';
		int lettersCount = zCode - aCode + 1;
		StringBuffer sb = new StringBuffer();
		if (i < lettersCount) {
			sb.append((char) (aCode + i));
		} else {
			while (i >= lettersCount) {
				int l = i % lettersCount;
				sb.append((char) (aCode + l));
				i = (i - l) / lettersCount;
			}
			sb.append((char) (aCode + i - 1));
			sb.reverse();
		}		
		return sb.toString();
	}
}
