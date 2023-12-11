package day10;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Day10 {

	public static void main(String[] args) throws IOException {
		// part1();
		part2();
	}

	public static void part1() throws IOException {

		char map[][] = buildPaddedMap();
		int startPosition[] = findStart(map);
		int pathLength = findLoopLength(map, startPosition);

		System.out.println("Total:" + pathLength / 2);

	}

	public static void part2() throws IOException {

		char map[][] = buildPaddedMap();
		int startPosition[] = findStart(map);
		ArrayList<Position> loop = collectLoop(map, startPosition);
		clearMap(map, loop);
		replaceS(map, startPosition);
		markInside(map);
		fillIn(map);
		System.out.println("inside: " + countIs(map));

	}

	private static int countIs(char map[][]) {
		int count = 0;
		for (int i = 1; i < map.length - 1; i++) {
			for (int j = 1; j < map[i].length - 1; j++) {
				if (map[i][j] == 'I') {
					count++;
				}
			}
		}
		return count;

	}

	private static void fillIn(char map[][]) {
		for (int i = 1; i < map.length - 1; i++) {
			for (int j = 1; j < map[i].length - 1; j++) {
				if (map[i][j] == 'I') {
					fillFrom(i, j, map);
				}
			}
		}

	}

	private static void fillFrom(int r, int c, char map[][]) {
		if (map[r - 1][c] == '.') {
			map[r - 1][c] = 'I';
			fillFrom(r - 1, c, map);
		}
		if (map[r + 1][c] == '.') {
			map[r + 1][c] = 'I';
			fillFrom(r + 1, c, map);
		}
		if (map[r][c - 1] == '.') {
			map[r][c - 1] = 'I';
			fillFrom(r, c - 1, map);
		}
		if (map[r][c + 1] == '.') {
			map[r][c + 1] = 'I';
			fillFrom(r, c + 1, map);
		}
	}

	private static void replaceS(char map[][], int pos[]) {
		boolean w = (map[pos[0]][pos[1] - 1] == 'L' || map[pos[0]][pos[1] - 1] == 'F'
				|| map[pos[0]][pos[1] - 1] == '-');
		boolean e = (map[pos[0]][pos[1] + 1] == '7' || map[pos[0]][pos[1] + 1] == 'J'
				|| map[pos[0]][pos[1] + 1] == '-');
		boolean n = (map[pos[0] - 1][pos[1]] == 'F' || map[pos[0] - 1][pos[1]] == '7'
				|| map[pos[0] - 1][pos[1]] == '|');
		boolean s = (map[pos[0] + 1][pos[1]] == 'J' || map[pos[0] + 1][pos[1]] == 'L'
				|| map[pos[0] + 1][pos[1]] == '|');

		if (w && e) {
			map[pos[0]][pos[1]] = '-';
		} else if (w && n) {
			map[pos[0]][pos[1]] = 'J';
		} else if (w && s) {
			map[pos[0]][pos[1]] = '7';
		} else if (e && n) {
			map[pos[0]][pos[1]] = 'L';
		} else if (e && s) {
			map[pos[0]][pos[1]] = 'F';
		} else if (n && s) {
			map[pos[0]][pos[1]] = '|';
		} else {
			throw new RuntimeException("Could not replace S");
		}

	}

	private static void clearMap(char map[][], ArrayList<Position> loop) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (!loop.contains(new Position(i, j))) {
					map[i][j] = '.';
				}
			}
		}
	}

	private static ArrayList<Position> collectLoop(char map[][], int start[]) {
		int current[] = new int[] { start[0], start[1] };
		int last[] = current;
		ArrayList<Position> pos = new ArrayList<Position>();
		pos.add(new Position(start[0], start[1]));
		current = findNextFromStart(map, current);
		pos.add(new Position(current[0], current[1]));
		do {
			int oldCurrent[] = current;
			current = findNext(map, current, last);
			last = oldCurrent;
			pos.add(new Position(current[0], current[1]));
		} while (!(current[0] == start[0] && current[1] == start[1]));
		return pos;
	}

	private static char[][] buildPaddedMap() throws IOException {
		FileInputStream in = new FileInputStream(new File("src/day10/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String line = "";

		ArrayList<String> lines = new ArrayList<String>();
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}

		char map[][] = new char[lines.size() + 2][lines.get(0).length() + 2];
		for (int i = 0; i < map[0].length; i++) {
			map[0][i] = 'N';
			map[map.length - 1][i] = 'N';
		}
		for (int i = 1; i < map.length - 1; i++) {
			map[i][0] = 'N';
			map[i][map[i].length - 1] = 'N';
			char lineArr[] = lines.get(i - 1).toCharArray();
			for (int j = 0; j < lineArr.length; j++) {
				map[i][j + 1] = lineArr[j];
			}
		}
		return map;

	}

	public static void printTheMap(char map[][]) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.print("" + map[i][j]);
			}
			System.out.println("");
		}

	}

	public static int[] findStart(char map[][]) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 'S') {
					return new int[] { i, j };
				}
			}
		}
		throw new RuntimeException("Couldn't find start :(");
	}

	public static int findLoopLength(char map[][], int start[]) {
		int current[] = new int[] { start[0], start[1] };
		int last[] = current;
		int length = 1;
		current = findNextFromStart(map, current);
		do {
			int oldCurrent[] = current;
			current = findNext(map, current, last);
			last = oldCurrent;
			length++;
		} while (!(current[0] == start[0] && current[1] == start[1]));
		return length;
	}

	public static final int UP = 1, DOWN = 2, LEFT = 3, RIGHT = 4;

	public static void markInside(char map[][]) {
		int topleft[] = new int[2];
		boolean found = false;
		for (int i = 0; !found && i < map.length; i++) {
			for (int j = 0; !found && j < map[i].length; j++) {
				if (map[i][j] != '.') {
					topleft[0] = i;
					topleft[1] = j;
					found = true;
				}
			}
		}

		int direction = UP;
		int current[] = new int[2];
		current[0] = topleft[0];
		current[1] = topleft[1];

		do {
			char pipe = map[current[0]][current[1]];
			if (pipe == 'F') {
				if (direction == UP) {
					current[1]++;
					direction = RIGHT;
				} else if (direction == LEFT) {
					if (map[current[0] - 1][current[1]] == '.') {
						map[current[0] - 1][current[1]] = 'I';
					}
					if (map[current[0]][current[1] - 1] == '.') {
						map[current[0]][current[1] - 1] = 'I';
					}
					current[0]++;
					direction = DOWN;
				} else {
					throw new RuntimeException("Bad direction when encountered F");
				}

			} else if (pipe == '-') {
				if (direction == LEFT) {
					if (map[current[0] - 1][current[1]] == '.') {
						map[current[0] - 1][current[1]] = 'I';
					}
					current[1]--;
				} else if (direction == RIGHT) {
					if (map[current[0] + 1][current[1]] == '.') {
						map[current[0] + 1][current[1]] = 'I';
					}
					current[1]++;
				} else {
					throw new RuntimeException("Bad direction when encountered -");
				}

			} else if (pipe == '7') {
				if (direction == UP) {
					if (map[current[0] - 1][current[1]] == '.') {
						map[current[0] - 1][current[1]] = 'I';
					}
					if (map[current[0]][current[1] + 1] == '.') {
						map[current[0]][current[1] + 1] = 'I';
					}
					current[1]--;
					direction = LEFT;
				} else if (direction == RIGHT) {
					current[0]++;
					direction = DOWN;
				} else {
					throw new RuntimeException("Bad direction when encountered 7");
				}
			} else if (pipe == '|') {
				if (direction == UP) {
					if (map[current[0]][current[1] + 1] == '.') {
						map[current[0]][current[1] + 1] = 'I';
					}
					current[0]--;
				} else if (direction == DOWN) {
					if (map[current[0]][current[1] - 1] == '.') {
						map[current[0]][current[1] - 1] = 'I';
					}
					current[0]++;
				} else {
					throw new RuntimeException("Bad direction when encountered |");
				}

			} else if (pipe == 'J') {
				if (direction == DOWN) {
					current[1]--;
					direction = LEFT;
				} else if (direction == RIGHT) {
					if (map[current[0]][current[1] + 1] == '.') {
						map[current[0]][current[1] + 1] = 'I';
					}
					if (map[current[0] + 1][current[1]] == '.') {
						map[current[0] + 1][current[1]] = 'I';
					}
					current[0]--;
					direction = UP;
				} else {
					throw new RuntimeException("Bad direction when encountered J " + current[0] + "," + current[1]);
				}

			} else if (pipe == 'L') {
				if (direction == DOWN) {
					if (map[current[0]][current[1] - 1] == '.') {
						map[current[0]][current[1] - 1] = 'I';
					}
					if (map[current[0] + 1][current[1]] == '.') {
						map[current[0] + 1][current[1]] = 'I';
					}
					current[1]++;
					direction = RIGHT;
				} else if (direction == LEFT) {
					current[0]--;
					direction = UP;
				} else {
					throw new RuntimeException("Bad direction when encountered L");
				}

			}

		} while (!(current[0] == topleft[0] && current[1] == topleft[1]));
	}

//	| is a vertical pipe connecting north and south.
//	- is a horizontal pipe connecting east and west.
//	L is a 90-degree bend connecting north and east.
//	J is a 90-degree bend connecting north and west.
//	7 is a 90-degree bend connecting south and west.
//	F is a 90-degree bend connecting south and east.
//	. is ground; there is no pipe in this tile.
//	S is the starting position of the animal;
	public static int[] findNextFromStart(char map[][], int start[]) {
		return findNext(map, start, new int[] { 0, 0 });
	}

	public static int[] findNext(char map[][], int current[], int last[]) {
		if ((last[0] != 0 && last[1] != 0) && Math.abs(current[0] - last[0] + current[1] - last[1]) != 1) {
			throw new RuntimeException("Invalid positions");
		}
		char currentVal = map[current[0]][current[1]];
		if (currentVal == 'S') {
			if ((last[0] != current[0] - 1 || last[1] != current[1]) && (map[current[0] - 1][current[1]] == '|'
					|| map[current[0] - 1][current[1]] == '7' || map[current[0] - 1][current[1]] == 'F')) {
				return new int[] { current[0] - 1, current[1] };
			} else if ((last[0] != current[0] || last[1] != current[1] + 1) && (map[current[0]][current[1] + 1] == '-'
					|| map[current[0]][current[1] + 1] == 'J' || map[current[0]][current[1] + 1] == '7')) {
				return new int[] { current[0], current[1] + 1 };

			} else if ((last[0] != current[0] + 1 || last[1] != current[1]) && (map[current[0] + 1][current[1]] == '|'
					|| map[current[0] + 1][current[1]] == 'L' || map[current[0] + 1][current[1]] == 'J')) {
				return new int[] { current[0] + 1, current[1] };
			} else if ((last[0] != current[0] || last[1] != current[1] - 1) && (map[current[0]][current[1] - 1] == '-'
					|| map[current[0]][current[1] - 1] == 'L' || map[current[0]][current[1] - 1] == 'F')) {
				return new int[] { current[0], current[1] - 1 };
			} else {
				throw new RuntimeException("Can't find next from " + current[0] + "," + current[1]);
			}
		} else {
			if (currentVal == '|') {
				if (last[0] == current[0] - 1) {
					return new int[] { current[0] + 1, current[1] };
				} else if (last[0] == current[0] + 1) {
					return new int[] { current[0] - 1, current[1] };
				} else {
					throw new RuntimeException("Something bad happen " + map[current[0]][current[1]] + "(" + current[0]
							+ "," + current[1] + ")" + " last: " + map[last[0]][last[1]]);
				}
			} else if (currentVal == '-') {
				if (last[1] == current[1] - 1) {
					return new int[] { current[0], current[1] + 1 };
				} else if (last[1] == current[1] + 1) {
					return new int[] { current[0], current[1] - 1 };
				} else {
					throw new RuntimeException("Something bad happen " + map[current[0]][current[1]] + "(" + current[0]
							+ "," + current[1] + ")" + " last: " + map[last[0]][last[1]]);
				}
			} else if (currentVal == 'L') {
				if (last[0] == current[0] - 1) {
					return new int[] { current[0], current[1] + 1 };
				} else if (last[1] == current[1] + 1) {
					return new int[] { current[0] - 1, current[1] };
				} else {
					throw new RuntimeException("Something bad happen " + map[current[0]][current[1]] + "(" + current[0]
							+ "," + current[1] + ")" + " last: " + map[last[0]][last[1]]);
				}
			} else if (currentVal == 'J') {
				if (last[0] == current[0] - 1) {
					return new int[] { current[0], current[1] - 1 };
				} else if (last[1] == current[1] - 1) {
					return new int[] { current[0] - 1, current[1] };
				} else {
					throw new RuntimeException("Something bad happen " + map[current[0]][current[1]] + "(" + current[0]
							+ "," + current[1] + ")" + " last: " + map[last[0]][last[1]]);
				}
			} else if (currentVal == '7') {
				if (last[1] == current[1] - 1) {
					return new int[] { current[0] + 1, current[1] };
				} else if (last[0] == current[0] + 1) {
					return new int[] { current[0], current[1] - 1 };
				} else {
					throw new RuntimeException("Something bad happen " + map[current[0]][current[1]] + "(" + current[0]
							+ "," + current[1] + ")" + " last: " + map[last[0]][last[1]]);
				}
			} else if (currentVal == 'F') {
				if (last[0] == current[0] + 1) {
					return new int[] { current[0], current[1] + 1 };
				} else if (last[1] == current[1] + 1) {
					return new int[] { current[0] + 1, current[1] };
				} else {
					throw new RuntimeException("Something bad happen " + map[current[0]][current[1]] + "(" + current[0]
							+ "," + current[1] + ")" + " last: " + map[last[0]][last[1]]);
				}
			} else if (currentVal == '|') {
				if (last[0] == current[0] - 1) {
					return new int[] { current[0] + 1, current[1] };
				} else if (last[0] == current[0] + 1) {
					return new int[] { current[0] - 1, current[1] };
				} else {
					throw new RuntimeException("Something bad happen " + map[current[0]][current[1]] + "(" + current[0]
							+ "," + current[1] + ")" + " last: " + map[last[0]][last[1]]);
				}
			}

		}
		throw new RuntimeException("Can't find next from " + current[0] + "," + current[1]);
	}

}

class Position {
	int row;
	int col;

	public Position(int r, int c) {
		row = r;
		col = c;
	}

	public boolean equals(Object o) {
		if (o instanceof Position) {
			Position p = (Position) o;
			return (p.row == row && p.col == col);
		} else {
			return o == this;
		}
	}
}