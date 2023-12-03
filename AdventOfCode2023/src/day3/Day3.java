package day3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class Day3 {

	public static void main(String[] args) throws IOException {
		// part1();
		part2();
	}

	public static char[][] buildSchematic() throws IOException {
		FileInputStream in = new FileInputStream(new File("src/day3/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		Vector<String> v = new Vector<String>();
		int biggestLine = 0;
		String line = "";
		while ((line = br.readLine()) != null) {
			v.add("." + line + ".");
			biggestLine = Math.max(biggestLine, line.length());
		}
		String pad = "";
		for (int i = 0; i < biggestLine; i++) {
			pad += ".";
		}
		v.insertElementAt(pad, 0);
		v.add(pad);

		char schematic[][] = new char[v.size()][biggestLine];

		for (int i = 0; i < schematic.length; i++) {
			schematic[i] = v.elementAt(i).toCharArray();
		}

		return schematic;
	}

	public static void part1() throws IOException {
		int answer = 0;

		char schematic[][] = buildSchematic();

		for (int i = 0; i < schematic.length; i++) {
			for (int j = 0; j < schematic[i].length; j++) {
				if (Character.isDigit(schematic[i][j])) {
					int numStart = j;
					String numStr = "" + schematic[i][j];
					while (Character.isDigit(schematic[i][j + 1])) {
						j++;
						numStr += schematic[i][j];
					}
					boolean foundPart = false;
					for (int x = i - 1; x <= i + 1 && !foundPart; x++) {
						for (int y = numStart - 1; y <= j + 1 && !foundPart; y++) {
							char toCheck = schematic[x][y];
							foundPart = (toCheck != '.' && !Character.isDigit(toCheck));
						}
					}
					if (foundPart) {
						answer += Integer.parseInt(numStr);
					}
				}

			}
		}
		System.out.println(answer + "");
	}

	public static void part2() throws IOException {
		int answer = 0;

		char schematic[][] = buildSchematic();

		for (int i = 0; i < schematic.length; i++) {
			for (int j = 0; j < schematic[i].length; j++) {
				if (schematic[i][j] == '*') {
					Vector<Integer> nums = new Vector<Integer>();
					if (Character.isDigit(schematic[i][j - 1])) {
						nums.add(Integer.valueOf(findNum(schematic, i, j - 1)));
					}
					if (Character.isDigit(schematic[i - 1][j])) {
						nums.add(Integer.valueOf(findNum(schematic, i - 1, j)));
					} else {
						if (Character.isDigit(schematic[i - 1][j - 1])) {
							nums.add(Integer.valueOf(findNum(schematic, i - 1, j - 1)));
						}
						if (Character.isDigit(schematic[i - 1][j + 1])) {
							nums.add(Integer.valueOf(findNum(schematic, i - 1, j + 1)));
						}
					}
					if (Character.isDigit(schematic[i][j + 1])) {
						nums.add(Integer.valueOf(findNum(schematic, i, j + 1)));
					}

					if (Character.isDigit(schematic[i + 1][j])) {
						nums.add(Integer.valueOf(findNum(schematic, i + 1, j)));

					} else {
						if (Character.isDigit(schematic[i + 1][j + 1])) {
							nums.add(Integer.valueOf(findNum(schematic, i + 1, j + 1)));

						}
						if (Character.isDigit(schematic[i + 1][j - 1])) {
							nums.add(Integer.valueOf(findNum(schematic, i + 1, j - 1)));
						}
					}

					if (nums.size() == 2) {
						answer += nums.elementAt(0).intValue() * nums.elementAt(1).intValue();
					}
				}
			}
		}
		System.out.println("answer: " + answer);

	}

	private static int findNum(char schematic[][], int x, int y) {

		String numStr = "" + schematic[x][y];

		int left = y - 1;
		int right = y + 1;

		while (Character.isDigit(schematic[x][left])) {
			numStr = schematic[x][left--] + numStr;
		}

		while (Character.isDigit(schematic[x][right])) {
			numStr = numStr + schematic[x][right++];
		}
		return Integer.parseInt(numStr);
	}
}
