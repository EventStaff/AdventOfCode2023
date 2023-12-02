package day2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Day2 {

	public static final int MAX_RED = 12;
	public static final int MAX_BLUE = 14;
	public static final int MAX_GREEN = 13;

	public static void main(String[] args) throws IOException {
		// part1();
		part2();
	}

	public static void part1() throws IOException {
		FileInputStream in = new FileInputStream(new File("src/day2/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		int answer = 0;
//Game 17: 15 blue, 3 green, 2 red; 2 green, 2 red, 15 blue; 1 red, 1 blue, 7 green
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, "\t\n\r\f;:");
			String gameStr = st.nextToken(); // Game 17
			StringTokenizer gamest = new StringTokenizer(gameStr);
			gamest.nextToken(); // Game
			int gameNum = Integer.parseInt(gamest.nextToken()); // #
			boolean gameValid = true;
			while (st.hasMoreTokens() && gameValid) {
				String round = st.nextToken(); // 15 blue, 3 green, 2 red
				StringTokenizer roundst = new StringTokenizer(round, " ,");
				boolean roundValid = true;
				while (roundst.hasMoreTokens() && roundValid) {
					int numCubes = Integer.parseInt(roundst.nextToken());
					String colour = roundst.nextToken();
					if (colour.equals("red")) {
						if (numCubes > MAX_RED) {
							roundValid = false;
						}
					} else if (colour.equals("blue")) {
						if (numCubes > MAX_BLUE) {
							roundValid = false;
						}
					} else if (colour.equals("green")) {
						if (numCubes > MAX_GREEN) {
							roundValid = false;
						}
					} else {
						System.err.println("wtf colour is this: " + colour);
						return;
					}
				}
				gameValid = roundValid;
			}
			if (gameValid) {
				answer += gameNum;
			}
		}
		System.out.println("Total: " + answer);

	}

	public static void part2() throws IOException {
		FileInputStream in = new FileInputStream(new File("src/day2/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		int answer = 0;
//Game 17: 15 blue, 3 green, 2 red; 2 green, 2 red, 15 blue; 1 red, 1 blue, 7 green
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, "\t\n\r\f;:");
			st.nextToken(); // Game 17

			int minred = 0, minblue = 0, mingreen = 0;
			while (st.hasMoreTokens()) {
				String round = st.nextToken(); // 15 blue, 3 green, 2 red
				StringTokenizer roundst = new StringTokenizer(round, " ,");

				while (roundst.hasMoreTokens()) {
					int numCubes = Integer.parseInt(roundst.nextToken());
					String colour = roundst.nextToken();
					if (colour.equals("red")) {
						minred = Math.max(minred, numCubes);
					} else if (colour.equals("blue")) {
						minblue = Math.max(minblue, numCubes);
					} else if (colour.equals("green")) {
						mingreen = Math.max(mingreen, numCubes);
					} else {
						System.err.println("wtf colour is this: " + colour);
						return;
					}
				}
			}
			answer += minred * minblue * mingreen;
		}
		System.out.println("Total: " + answer);
	}
}
