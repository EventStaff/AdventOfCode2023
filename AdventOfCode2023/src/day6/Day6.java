package day6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Day6 {

	public static void main(String[] args) throws IOException {
		// part1();
		part2();
	}


//	static int time[] = { 7, 15, 30 };
//	static int dist[] = { 9, 40, 200 };
	static int time[] = { 41, 96, 88, 94 };
	static int dist[] = { 214, 1789, 1127, 1055 };

	static int waysToBeat[] = new int[time.length];

	public static void part1() throws IOException {
		int answer = 1;
		for (int i = 0; i < time.length; i++) {
			waysToBeat[i] = letMeCountTheWays(time[i], dist[i]);
			answer *= waysToBeat[i];
		}
		System.out.println("Answer: " + answer);

	}

	public static void part2() throws IOException {
		long answer = 1;
//		long longtime = 71530;
//		long longdist = 940200;
		long longtime = 41968894;
		BigInteger bigDist = new BigInteger("214178911271055");

		answer = letMeCountTheWays(longtime, bigDist);

		System.out.println("Answer: " + answer);

	}

	private static int letMeCountTheWays(int time, int distToBeat) {
		int ways = 0;
		for (int i = 1; i < time; i++) {
			if (i * (time - i) > distToBeat) {
				ways++;
			}
		}
		return ways;
	}

	private static long letMeCountTheWays(long time, BigInteger distToBeat) {
		long ways = 0;
		boolean inRange = false;
		for (long i = 1; i < time; i++) {
			if (!distToBeat.max(new BigInteger(Long.toString(i * (time - i)))).equals(distToBeat)) {
				ways++;
				inRange = true;
			} else if (inRange) {
				break;
			}
		}
		return ways;
	}
}
