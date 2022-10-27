package com.example.ranunstrgen;

import java.util.TreeSet;

public class Utils {

	public static int calculateFactorial(int n) {
		int factorial = 1;

		for (int i = 2; i <= n; i++)
			factorial *= i;

		return factorial;
	}

	static public String swap(String str, int i, int j) {
		char[] ch = str.toCharArray();
		char c = ch[i];
		ch[i] = ch[j];
		ch[j] = c;
		return ch.toString();
	}

	static public boolean isPossibleToSwap(String str, int start, int curr) {
		for (int i = start; i < curr; i++) {
			if (str.charAt(i) == str.charAt(curr)) {
				return false;
			}
		}
		return true;
	}

	static public void findPermutations(String str, int pos, int n, TreeSet<String> resultSet) {
		if (pos >= n) {
			resultSet.add(str);
			return;
		}

		for (int i = pos; i < n; i++) {
			boolean isPossibleToSwap = isPossibleToSwap(str, pos, i);
			if (isPossibleToSwap) {
				str = swap(str, pos, i);
				findPermutations(str, pos + 1, n, resultSet);
				str = swap(str, pos, i);
			}
		}
	}

	static public TreeSet<String> findSubpermutations(int min, int max, TreeSet<String> permSet, Long size) {
		TreeSet<String> resultSet = new TreeSet<>();
		for (int i = min; i <= max; i++) {
			for (String p : permSet) {
				resultSet.add(p.substring(0, i));
				if (resultSet.size() == size)
					return resultSet;
			}
		}
		return resultSet;
	}
}
