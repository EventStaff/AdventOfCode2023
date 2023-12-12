package day11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 {

	public static void main(String[] args) throws IOException {
		// part1();
		part2();
	}

	// Total:9795148
	public static void part1() throws IOException {

		FileInputStream in = new FileInputStream(new File("src/day11/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		int answer = 0;
		List<List<Character>> universe = new ArrayList<List<Character>>();

		while ((line = br.readLine()) != null) {
			universe.add(line.chars().mapToObj(x -> (char) x).collect(Collectors.toList()));
		}
		print(universe);
		System.out.println("-----------------------------------");
		List<Integer> milCols = new ArrayList<Integer>();
		List<Integer> milRows = new ArrayList<Integer>();
		universe = expand(universe);
		print(universe);

		List<Galaxy> galaxies = new ArrayList<Galaxy>();
		for (int i = 0; i < universe.size(); i++) {
			for (int j = 0; j < universe.get(i).size(); j++) {
				if (universe.get(i).get(j).charValue() == '#') {
					galaxies.add(new Galaxy(i, j));
				}
			}
		}
		for (int i = 0; i < galaxies.size() - 1; i++) {
			for (int j = i + 1; j < galaxies.size(); j++) {
				answer += Math.abs(galaxies.get(i).row - galaxies.get(j).row)
						+ Math.abs(galaxies.get(i).col - galaxies.get(j).col);
			}
		}
		System.out.println("Total:" + answer);

	}

	public static void part2() throws IOException {

		FileInputStream in = new FileInputStream(new File("src/day11/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		long answer = 0;
		List<List<Character>> universe = new ArrayList<List<Character>>();

		while ((line = br.readLine()) != null) {
			universe.add(line.chars().mapToObj(x -> (char) x).collect(Collectors.toList()));
		}
		List<Integer> milCols = new ArrayList<Integer>();
		List<Integer> milRows = new ArrayList<Integer>();
		findExpansions(universe, milCols, milRows);

		List<Galaxy> galaxies = new ArrayList<Galaxy>();

		for (int i = 0; i < universe.size(); i++) {
			for (int j = 0; j < universe.get(i).size(); j++) {
				if (universe.get(i).get(j).charValue() == '#') {
					galaxies.add(new Galaxy(i, j));
				}
			}
		}

		for (int i = 0; i < galaxies.size() - 1; i++) {
			for (int j = i + 1; j < galaxies.size(); j++) {
				long x1 = galaxies.get(i).row;
				long x2 = galaxies.get(j).row;
				long y1 = galaxies.get(i).col;
				long y2 = galaxies.get(j).col;
				answer += Math.abs(x1 - x2) + Math.abs(y1 - y2);

				for (int x = 0; x < milCols.size(); x++) {
					int col = milCols.get(x).intValue();
					if ((y1 < col && y2 > col) || (y1 > col && y2 < col)) {
						answer += 999999;
					}
				}
				for (int x = 0; x < milRows.size(); x++) {
					int row = milRows.get(x).intValue();
					if ((x1 < row && x2 > row) || (x1 > row && x2 < row)) {
						answer += 999999;
					}
				}
			}
		}

		/*
		 * for (Galaxy g : galaxies) { int milCount = 0; for (Integer i : milCols) { if
		 * (g.col > i.intValue()) { milCount++; } } System.out.println(g +
		 * " col milcount: " + milCount); g.col += 999999 * milCount; milCount = 0; for
		 * (Integer i : milRows) { if (g.row > i.intValue()) { milCount++; } }
		 * System.out.println(g + " row milcount: " + milCount); g.row += 999999 *
		 * milCount; } for (int i = 0; i < galaxies.size(); i++) { for (int j = i + 1; j
		 * < galaxies.size(); j++) { // System.out.println(++comparison + ". " +
		 * galaxies.get(i).x + ", " + galaxies.get(i).y + " - " // + galaxies.get(j).x +
		 * ", " + galaxies.get(j).y); long x1 = galaxies.get(i).row; long x2 =
		 * galaxies.get(j).row; long y1 = galaxies.get(i).col; long y2 =
		 * galaxies.get(j).col; answer += Math.abs(x1 - x2) + Math.abs(y1 - y2); } }
		 * 
		 */

		System.out.println("Total:" + answer);

	}

	public static void print(List<List<Character>> universe) {
		for (int i = 0; i < universe.size(); i++) {
			for (int j = 0; j < universe.get(i).size(); j++) {
				System.out.print(universe.get(i).get(j));
			}
			System.out.println("");
		}

	}

	public static final Character EMPTY = '.';

	public static void findExpansions(List<List<Character>> universe, List<Integer> milCols, List<Integer> milRows) {

		for (int i = 0; i < universe.size(); i++) {

			if (!universe.get(i).contains(Character.valueOf('#'))) {
				milRows.add(Integer.valueOf(i));
			}
		}
		for (int i = 0; i < universe.get(0).size(); i++) {
			boolean empty = true;
			for (List<Character> row : universe) {
				if (!row.get(i).equals(EMPTY)) {
					empty = false;
					break;
				}
			}
			if (empty) {
				milCols.add(Integer.valueOf(i));
			}
		}
	}

	public static List<List<Character>> expand(List<List<Character>> universe) {

		List<List<Character>> expanded = new ArrayList<List<Character>>();
		for (List<Character> row : universe) {
			expanded.add(row);
			if (!row.contains(Character.valueOf('#'))) {
				expanded.add(new ArrayList<Character>(row));
			}
		}
		for (int i = 0; i < expanded.get(0).size(); i++) {
			boolean empty = true;
			for (List<Character> row : universe) {
				if (!row.get(i).equals(EMPTY)) {
					empty = false;
					break;
				}
			}
			if (empty) {
				for (List<Character> row : expanded) {
					row.add(i, EMPTY);
				}
				i++;
			}
		}
		return expanded;
	}
}

class Galaxy {
	long row, col;

	public Galaxy(long row, long col) {
		this.row = row;
		this.col = col;
	}

	public String toString() {
		return "G:[" + row + ", " + col + "]";
	}
}
