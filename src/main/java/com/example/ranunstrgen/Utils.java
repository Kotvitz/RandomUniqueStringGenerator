package com.example.ranunstrgen;

import java.util.TreeSet;

public class Utils {

	public static int calculateFactorial(int n) {
		int factorial = 1;

		for (int i = 2; i <= n; i++)
			factorial *= i;

		return factorial;
	}

	static public char[] swap(char[] cs, int i, int j) {
		char c = cs[i];
		cs[i] = cs[j];
		cs[j] = c;
		return cs;
	}

	static public boolean isPossibleToSwap(char[] cs, int start, int curr) {
		for (int i = start; i < curr; i++) {
			if (cs[i] == cs[curr]) {
				return false;
			}
		}
		return true;
	}

	static public void findPermutations(char[] cs, int pos, int n, TreeSet<String> resultSet) {
		if (pos >= n) {
			resultSet.add(cs.toString());
			return;
		}

		for (int i = pos; i < n; i++) {
			boolean isPossibleToSwap = isPossibleToSwap(cs, pos, i);
			if (isPossibleToSwap) {
				cs = swap(cs, pos, i);
				findPermutations(cs, pos + 1, n, resultSet);
				cs = swap(cs, pos, i);
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
	
    static public int permute(int n, int r) {
        return calculateFactorial(n) / calculateFactorial(n - r);
    }
    
    static public int findNumberOfPermutations(int min, int max) {
    	int n = 0;
        int p;
        for (int i = min; i <= max; i++) {
        	p = permute(max, i);
        	n += p;
        }
        return n;
    }
}
