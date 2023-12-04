package day4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Day4 {

	public static void main(String[] args) throws IOException {
	//	part1();
		part2();
	}

//Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53

	public static void part1() throws IOException {
		int answer = 0;

		FileInputStream in = new FileInputStream(new File("src/day4/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);

		String line = "";
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, ":|");
			st.nextToken();
			List<String> winners = getNums(st.nextToken());
			List<String> numbers = getNums(st.nextToken());
			int wincount = 0;
			for (String num : numbers) {
				if (winners.indexOf(num) >= 0) {
					wincount++;
				}
			}
			if (wincount > 0) {
				answer += Math.pow(2, wincount - 1);
			}
		}

		System.out.println(answer + "");
	}

	private static List<String> getNums(String line) {
		return Collections.list(new StringTokenizer(line)).stream().map(token -> (String) token)
				.collect(Collectors.toList());
	}

	public static void part2() throws IOException {
		int answer = 0;

		FileInputStream in = new FileInputStream(new File("src/day4/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);

		String line = "";
		List<List<List<String>>> list = new ArrayList<List<List<String>>>();
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, ":|");
			st.nextToken();

			List<List<String>> oneline = new ArrayList<List<String>>();
			oneline.add(getNums(st.nextToken()));
			oneline.add(getNums(st.nextToken()));
			list.add(oneline);
		}
		int count[] = new int[list.size()];
		
		
		for(int i = 0; i < count.length; i++) {
			count[i]++;
			
			List<String> winners = list.get(i).get(0);
			List<String> numbers = list.get(i).get(1);
			int wincount = 0;
			for (String num : numbers) {
				if (winners.indexOf(num) >= 0) {
					wincount++;
				}
			}
			for(int j = i + wincount; j > i; j--) {
				count[j] += count[i];
			}
			answer += count[i];
		}

		
		System.out.println(answer + "");

	}
}
