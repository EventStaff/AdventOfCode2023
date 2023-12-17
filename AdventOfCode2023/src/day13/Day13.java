package day13;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day13 {

	public static void main(String[] args) throws IOException {
		// part1();
		part2();
	}

	public static void part1() throws IOException {

		FileInputStream in = new FileInputStream(new File("src/day13/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		long answer = 0;
		List<Map> maps = new ArrayList<Map>();
		ArrayList<String> lines = new ArrayList<String>();
		while ((line = br.readLine()) != null) {
			if (line.length() > 0) {
				lines.add(line);
			} else {
				Map m = new Map(lines);
				maps.add(m);
				if (m.aboveRows > 0) {
					answer += 100 * m.aboveRows;
				} else if (m.leftCols > 0) {
					answer += m.leftCols;
				} else {
					throw new RuntimeException("Didn't find reflection");
				}
				lines.clear();
			}
		}
		Map m = new Map(lines);
		maps.add(m);
		if (m.aboveRows > 0) {
			answer += 100 * m.aboveRows;
		} else if (m.leftCols > 0) {
			answer += m.leftCols;
		} else {
			throw new RuntimeException("Didn't find reflection");
		}
		System.out.println("Total:" + answer);

	}

	public static void part2() throws IOException {
		FileInputStream in = new FileInputStream(new File("src/day13/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		long answer = 0;
		List<Map2> maps = new ArrayList<Map2>();
		ArrayList<String> lines = new ArrayList<String>();
		while ((line = br.readLine()) != null) {
			if (line.length() > 0) {
				lines.add(line);
			} else {
				Map2 m = new Map2(lines);
				maps.add(m);
				if (m.aboveRows > 0) {
					answer += 100 * m.aboveRows;
				} else if (m.leftCols > 0) {
					answer += m.leftCols;
				} else {
					throw new RuntimeException("Didn't find reflection");
				}
				lines.clear();
			}
		}
		Map2 m = new Map2(lines);
		maps.add(m);
		if (m.aboveRows > 0) {
			answer += 100 * m.aboveRows;
		} else if (m.leftCols > 0) {
			answer += m.leftCols;
		} else {
			throw new RuntimeException("Didn't find reflection");
		}
		System.out.println("Total:" + answer);
	}

}

class Map {
	char map[][];
	int leftCols, aboveRows;

	public Map(List<String> lines) {
		map = new char[lines.size()][lines.get(0).length()];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = lines.get(i).charAt(j);
			}
		}
		leftCols = aboveRows = -1;
		findReflection();
	}

	private void findReflection() {
		// check rows
		for (int i = 0; i < map.length - 1; i++) {
			int above = i;
			int below = i + 1;
			boolean allMatch = true;
			while (allMatch && above >= 0 && below < map.length) {
				if (!rowMatches(above, below)) {
					allMatch = false;
				}
				above--;
				below++;
			}
			if (allMatch) {
				aboveRows = i + 1;
				return;
			}
		}
		// check columns
		for (int i = 0; i < map[0].length - 1; i++) {
			int left = i;
			int right = i + 1;
			boolean allMatch = true;
			while (allMatch && left >= 0 && right < map[0].length) {
				if (!colMatches(left, right)) {
					allMatch = false;
				}
				left--;
				right++;
			}
			if (allMatch) {
				leftCols = i + 1;
				return;
			}
		}
	}

	private boolean rowMatches(int row1, int row2) {
		for (int i = 0; i < map[row1].length; i++) {
			if (map[row1][i] != map[row2][i]) {
				return false;

			}
		}
		return true;
	}

	private boolean colMatches(int col1, int col2) {
		for (int i = 0; i < map.length; i++) {
			if (map[i][col1] != map[i][col2]) {
				return false;
			}
		}
		return true;
	}

}

class Map2 {
	char map[][];
	int leftCols, aboveRows;

	public Map2(List<String> lines) {
		map = new char[lines.size()][lines.get(0).length()];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = lines.get(i).charAt(j);
			}
		}
		leftCols = aboveRows = -1;
		findReflection();
	}

	private void findReflection() {
		// check rows
		for (int i = 0; i < map.length - 1; i++) {
			int above = i;
			int below = i + 1;
			boolean allMatch = true;
			int runningDiff = 0;
			while (allMatch && above >= 0 && below < map.length) {
				runningDiff += rowDiff(above, below);
				if (runningDiff > 1) {
					allMatch = false;
				}
				above--;
				below++;
			}
			if (runningDiff == 1) {
				aboveRows = i + 1;
				return;
			}
		}
		// check columns
		for (int i = 0; i < map[0].length - 1; i++) {
			int left = i;
			int right = i + 1;
			boolean allMatch = true;
			int runningDiff = 0;
			while (allMatch && left >= 0 && right < map[0].length) {
				runningDiff += colDiff(left, right);
				if (runningDiff > 1) {
					allMatch = false;
				}
				left--;
				right++;
			}
			if (runningDiff == 1) {
				leftCols = i + 1;
				return;
			}
		}
	}

	private int rowDiff(int row1, int row2) {
		int diffCount = 0;
		for (int i = 0; i < map[row1].length; i++) {
			if (map[row1][i] != map[row2][i]) {
				diffCount++;

			}
		}
		return diffCount;
	}

	private int colDiff(int col1, int col2) {
		int diffCount = 0;
		for (int i = 0; i < map.length; i++) {
			if (map[i][col1] != map[i][col2]) {
				diffCount++;
			}
		}
		return diffCount;
	}

}