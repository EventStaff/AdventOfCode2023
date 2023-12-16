package day14;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Determine the ASCII code for the current character of the string.
//Increase the current value by the ASCII code you just determined.
//Set the current value to itself multiplied by 17.
//Set the current value to the remainder of dividing itself by 256.

//rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7
//52

public class Day14 {

	public static void main(String[] args) throws IOException {
		//part1();
		part2();
	}

	public static void part1() throws IOException {

		FileInputStream in = new FileInputStream(new File("src/day14/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		long answer = 0;
		ArrayList<String> buffer = new ArrayList<String>();
		while ((line = br.readLine()) != null) {
			buffer.add(line);
		}

		char map[][] = new char[buffer.size()][buffer.get(0).length()];
		for (int i = 0; i < map.length; i++) {
			line = buffer.get(i);
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = line.charAt(j);
			}
		}

		for (int i = 1; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 'O') {
					int i2 = i - 1;
					while (i2 >= 0 && map[i2][j] == '.') {
						map[i2][j] = 'O';
						map[i2 + 1][j] = '.';
						i2--;
					}
				}
			}
		}
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 'O') {
					answer += (map.length - i);
				}
			}
		}
		System.out.println("Total:" + answer);

	}

	public static void part2() throws IOException {
		FileInputStream in = new FileInputStream(new File("src/day14/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		long answer = 0;
		ArrayList<String> buffer = new ArrayList<String>();
		while ((line = br.readLine()) != null) {
			buffer.add(line);
		}

		char map[][] = new char[buffer.size()][buffer.get(0).length()];
		for (int i = 0; i < map.length; i++) {
			line = buffer.get(i);
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = line.charAt(j);
			}
		}

		Map<String, Long> setups = new HashMap<String, Long>();
		long l;
		boolean looped = false;

		for (l = 0; l < 1000000000; l++) {
			cycle(map);
			String mapStr = mapToString(map);
			if (!looped) {
				if (setups.containsKey(mapStr)) {
					looped = true;
					long loopStart = setups.get(mapStr).longValue();
					long loopLength = l - loopStart;
					long remaining = (1000000000 - loopStart) % (loopLength);
					l = 1000000000 - remaining;
				} else {
					setups.put(mapStr, Long.valueOf(l));
				}
			}
		}

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 'O') {
					answer += (map.length - i);
				}
			}
		}
		System.out.println("Total:" + answer);

	}

	public static String mapToString(char map[][]) {
		StringBuilder sb = new StringBuilder();
		for (char line[] : map) {
			sb.append(line);
		}
		return sb.toString();
	}

	public static void cycle(char map[][]) {
		// N
		for (int i = 1; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 'O') {
					int i2 = i - 1;
					while (i2 >= 0 && map[i2][j] == '.') {
						map[i2][j] = 'O';
						map[i2 + 1][j] = '.';
						i2--;
					}
				}
			}
		}
		// W
		for (int j = 1; j < map[0].length; j++) {
			for (int i = 0; i < map.length; i++) {
				if (map[i][j] == 'O') {
					int j2 = j - 1;
					while (j2 >= 0 && map[i][j2] == '.') {
						map[i][j2] = 'O';
						map[i][j2 + 1] = '.';
						j2--;
					}
				}
			}
		}
		// S
		for (int i = map.length - 1; i >= 0; i--) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 'O') {
					int i2 = i + 1;
					while (i2 < map.length && map[i2][j] == '.') {
						map[i2][j] = 'O';
						map[i2 - 1][j] = '.';
						i2++;
					}
				}
			}
		}
		// E
		for (int j = map[0].length - 2; j >= 0; j--) {
			for (int i = 0; i < map.length; i++) {
				if (map[i][j] == 'O') {
					int j2 = j + 1;
					while (j2 < map[0].length && map[i][j2] == '.') {
						map[i][j2] = 'O';
						map[i][j2 - 1] = '.';
						j2++;
					}
				}
			}
		}

	}
}
