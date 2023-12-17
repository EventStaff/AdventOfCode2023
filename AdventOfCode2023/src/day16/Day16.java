package day16;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day16 {

	public static void main(String[] args) throws IOException {
		// part1();
		part2();
	}

	public static void part1() throws IOException {

		FileInputStream in = new FileInputStream(new File("src/day16/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		long answer = 0;
		ArrayList<String> lines = new ArrayList<String>();
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		MapCell map[][] = new MapCell[lines.size()][lines.get(0).length()];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = new MapCell(lines.get(i).charAt(j));
			}
		}
		List<Beam> beams = new ArrayList<Beam>();
		beams.add(new Beam(0, 0, Beam.EAST));
		while (beams.size() > 0) {
			for (int i = 0; i < beams.size(); i++) {
				Beam b = beams.get(i);
				beams.remove(i);
				beams.addAll(b.step(map));
			}
		}
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				answer += map[i][j].energized ? 1 : 0;
			}
		}
		System.out.println("Total:" + answer);
	}

	public static void part2() throws IOException {
		FileInputStream in = new FileInputStream(new File("src/day16/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		long answer = 0;
		ArrayList<String> lines = new ArrayList<String>();
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		MapCell map[][] = new MapCell[lines.size()][lines.get(0).length()];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = new MapCell(lines.get(i).charAt(j));
			}
		}
		// walk top edge
		for (int c = 0; c < map[0].length; c++) {
			List<Beam> beams = new ArrayList<Beam>();
			beams.add(new Beam(0, c, Beam.SOUTH));
			while (beams.size() > 0) {
				for (int i = 0; i < beams.size(); i++) {
					Beam b = beams.get(i);
					beams.remove(i);
					beams.addAll(b.step(map));
				}
			}
			int thisAnswer = 0;
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					thisAnswer += map[i][j].energized ? 1 : 0;
				}
			}
			if (thisAnswer > answer) {
				answer = thisAnswer;
			}
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					map[i][j].reset();
				}
			}

		}
		// walk bottom edge
		for (int c = 0; c < map[0].length; c++) {
			List<Beam> beams = new ArrayList<Beam>();
			beams.add(new Beam(map.length - 1, c, Beam.NORTH));
			while (beams.size() > 0) {
				for (int i = 0; i < beams.size(); i++) {
					Beam b = beams.get(i);
					beams.remove(i);
					beams.addAll(b.step(map));
				}
			}
			int thisAnswer = 0;
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					thisAnswer += map[i][j].energized ? 1 : 0;
				}
			}
			if (thisAnswer > answer) {
				answer = thisAnswer;
			}
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					map[i][j].reset();
				}
			}

		}
		// walk left edge
		for (int r = 0; r < map.length; r++) {
			List<Beam> beams = new ArrayList<Beam>();
			beams.add(new Beam(r, 0, Beam.EAST));
			while (beams.size() > 0) {
				for (int i = 0; i < beams.size(); i++) {
					Beam b = beams.get(i);
					beams.remove(i);
					beams.addAll(b.step(map));
				}
			}
			int thisAnswer = 0;
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					thisAnswer += map[i][j].energized ? 1 : 0;
				}
			}
			if (thisAnswer > answer) {
				answer = thisAnswer;
			}
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					map[i][j].reset();
				}
			}
		}
		// walk right edge
		for (int r = 0; r < map.length; r++) {
			List<Beam> beams = new ArrayList<Beam>();
			beams.add(new Beam(r, map[0].length - 1, Beam.WEST));
			while (beams.size() > 0) {
				for (int i = 0; i < beams.size(); i++) {
					Beam b = beams.get(i);
					beams.remove(i);
					beams.addAll(b.step(map));
				}
			}
			int thisAnswer = 0;
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					thisAnswer += map[i][j].energized ? 1 : 0;
				}
			}
			if (thisAnswer > answer) {
				answer = thisAnswer;
			}
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					map[i][j].reset();
				}
			}
		}
		System.out.println("Total:" + answer);
	}
}

class MapCell {
	char value;
	boolean energized;
	boolean seenNorth, seenEast, seenWest, seenSouth;

	public MapCell(char val) {
		value = val;
		reset();
	}

	public void reset() {
		energized = seenNorth = seenEast = seenWest = seenSouth = false;
	}

	public boolean seen(int direction) {
		if (direction == Beam.NORTH) {
			return seenNorth;
		} else if (direction == Beam.EAST) {
			return seenEast;
		} else if (direction == Beam.WEST) {
			return seenWest;
		} else if (direction == Beam.SOUTH) {
			return seenSouth;
		} else {
			throw new RuntimeException("Invalid direction " + direction);
		}
	}

	public void see(int direction) {
		energized = true;

		if (direction == Beam.NORTH) {
			seenNorth = true;
		} else if (direction == Beam.EAST) {
			seenEast = true;
		} else if (direction == Beam.WEST) {
			seenWest = true;
		} else if (direction == Beam.SOUTH) {
			seenSouth = true;
		} else {
			throw new RuntimeException("Invalid direction " + direction);
		}
	}
}

class Beam {
	public static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3, DONE = 4;
	int row, col, dir;

	public Beam(int row, int col, int direction) {
		this.row = row;
		this.col = col;
		this.dir = direction;
	}

	// ./\-|
	public List<Beam> step(MapCell map[][]) {

		if (dir == DONE || col < 0 || col >= map[0].length || row < 0 || row >= map.length) {
			return new ArrayList<Beam>();
		} else if (map[row][col].seen(dir)) {
			dir = DONE;
			return new ArrayList<Beam>();
		} else {
			ArrayList<Beam> beams = new ArrayList<Beam>();
			beams.add(this);
			MapCell cell = map[row][col];
			cell.see(dir);
			if (dir == NORTH) {
				if (cell.value == '.') {
					row--;
				} else if (cell.value == '/') {
					dir = EAST;
					col++;
				} else if (cell.value == '\\') {
					dir = WEST;
					col--;
				} else if (cell.value == '-') {
					beams.add(new Beam(row, col++, EAST));
					col--;
					dir = WEST;
				} else if (cell.value == '|') {
					row--;
				}
			} else if (dir == EAST) {
				if (cell.value == '.') {
					col++;
				} else if (cell.value == '/') {
					dir = NORTH;
					row--;
				} else if (cell.value == '\\') {
					dir = SOUTH;
					row++;
				} else if (cell.value == '-') {
					col++;
				} else if (cell.value == '|') {
					beams.add(new Beam(row--, col, NORTH));
					dir = SOUTH;
					row++;
				}

			} else if (dir == SOUTH) {
				if (cell.value == '.') {
					row++;
				} else if (cell.value == '/') {
					dir = WEST;
					col--;
				} else if (cell.value == '\\') {
					dir = EAST;
					col++;
				} else if (cell.value == '-') {
					beams.add(new Beam(row, col++, EAST));
					col--;
					dir = WEST;
				} else if (cell.value == '|') {
					row++;
				}

			} else if (dir == WEST) {
				if (cell.value == '.') {
					col--;
				} else if (cell.value == '/') {
					dir = SOUTH;
					row++;
				} else if (cell.value == '\\') {
					dir = NORTH;
					row--;
				} else if (cell.value == '-') {
					col--;
				} else if (cell.value == '|') {
					beams.add(new Beam(row--, col, NORTH));
					dir = SOUTH;
					row++;
				}
			} else {
				throw new RuntimeException("we have a bad direction?");
			}
			return beams;
		}
	}
}
