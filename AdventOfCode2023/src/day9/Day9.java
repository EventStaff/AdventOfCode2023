package day9;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Day9 {

	public static void main(String[] args) throws IOException {

		FileInputStream in = new FileInputStream(new File("src/day9/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		int answer = 0;
		ArrayList<ValueSet> sets = new ArrayList<ValueSet>();
		while ((line = br.readLine()) != null) {
			sets.add(new ValueSet(line));
		}
		for (ValueSet set : sets) {
//			answer += set.nextValue();
			answer += set.previousValue();
		}
		System.out.println("Total:" + answer);
	}
}

class ValueSet {
	int values[];
	boolean hasNonZeroes;
	ValueSet nextLevel;

	public ValueSet(String valueString) {
		StringTokenizer st = new StringTokenizer(valueString);
		values = new int[st.countTokens()];
		hasNonZeroes = false;
		for (int i = 0; i < values.length; i++) {
			values[i] = Integer.parseInt(st.nextToken());
			if (!hasNonZeroes && values[i] != 0) {
				hasNonZeroes = true;
			}
		}
		if (hasNonZeroes) {
			nextLevel = new ValueSet(this);
		}
	}

	public ValueSet(ValueSet prev) {
		values = new int[prev.values.length - 1];
		for (int i = 0; i < values.length; i++) {
			values[i] = prev.values[i + 1] - prev.values[i];
			if (!hasNonZeroes && values[i] != 0) {
				hasNonZeroes = true;
			}
		}
		if (hasNonZeroes) {
			nextLevel = new ValueSet(this);
		}
	}

	public int nextValue() {
		if (hasNonZeroes) {
			return nextLevel.nextValue() + values[values.length - 1];
		} else {
			return 0;
		}
	}

	public int previousValue() {
		if (hasNonZeroes) {
			return values[0] - nextLevel.previousValue();
		} else {
			return 0;
		}

	}
}
