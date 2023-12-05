package day5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class Day5 {

	public static void main(String[] args) throws IOException {
		// part1();
//		part2();
		part2_attempt2();
	}

	public static final String START = "seed";
	public static final String GOAL = "location";

	public static void part1() throws IOException {
		long answer = 0;

		FileInputStream in = new FileInputStream(new File("src/day5/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);

		long seedList[] = parseSeedList(br.readLine());
		String line = "";
		br.readLine();
		Vector<Mapping> mappings = new Vector<Mapping>();

		while ((line = br.readLine()) != null) {
			Mapping m = new Mapping(line);
			while ((line = br.readLine()) != null && line.length() > 0) {
				m.addRange(line);
			}
			mappings.add(m);

		}
		for (int i = 0; i < seedList.length; i++) {
			String jump = START;
			long value = seedList[i];
			while (!jump.equals(GOAL)) {
				Mapping m = findNextMapping(jump, mappings);
				jump = m.to;
				for (Range range : m.ranges) {
					if (range.isIn(value)) {
						value = range.valueOf(value);
						break;
					}

				}
			}

			if (answer == 0 || value < answer) {
				answer = value;
			}
		}

		System.out.println(answer + "");
	}

	private static Mapping findNextMapping(String jump, Vector<Mapping> mappings) {
		for (int i = 0; i < mappings.size(); i++) {
			if (mappings.get(i).from.equals(jump)) {
				return mappings.get(i);
			}
		}
		System.out.println("uh oh");
		return null;
	}

	private static long[] parseSeedList(String line) {
		StringTokenizer st = new StringTokenizer(line, ": ");
		st.nextToken();
		long list[] = new long[st.countTokens()];
		for (int i = 0; i < list.length; i++) {
			list[i] = Long.parseLong(st.nextToken());
		}
		return list;
	}

	public static void part2() throws IOException {
		long answer = Long.MAX_VALUE;

		FileInputStream in = new FileInputStream(new File("src/day5/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);

		long seedList[] = parseSeedList(br.readLine());
		String line = "";
		br.readLine();
		Vector<Mapping> mappings = new Vector<Mapping>();

		while ((line = br.readLine()) != null) {
			Mapping m = new Mapping(line);
			while ((line = br.readLine()) != null && line.length() > 0) {
				m.addRange(line);
			}
			mappings.add(m);

		}
		for (int i = 0; i < seedList.length - 1; i += 2) {
			System.out.println("i: " + i);
			long lastVal = seedList[i] + seedList[i + 1] - 1;
			for (long j = seedList[i]; j <= lastVal; j++) {
				long value = j;
				for (Mapping mapping : mappings) {
					boolean found = false;
					for (Range range : mapping.ranges) {
						if (!found && range.isIn(value)) {
							value = range.valueOf(value);
							found = true;
						}
					}
				}
				if (value < answer) {
					answer = value;
				}
			}
		}

		System.out.println(answer + "");
	}

//11554136 is too high
//20033248
	public static void part2_attempt2() throws IOException {
		long answer = Long.MAX_VALUE;

		FileInputStream in = new FileInputStream(new File("src/day5/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);

		long seedList[] = parseSeedList(br.readLine());
		String line = "";
		br.readLine();
		Vector<Mapping> mappings = new Vector<Mapping>();

		while ((line = br.readLine()) != null) {
			Mapping m = new Mapping(line);
			while ((line = br.readLine()) != null && line.length() > 0) {
				m.addRange(line);
			}
			mappings.add(m);

		}
		ArrayList<SeedRange> seedRanges = new ArrayList<SeedRange>();

		for (int i = 0; i < seedList.length - 1; i += 2) {
			long lastVal = seedList[i] + seedList[i + 1] - 1;
			seedRanges.add(new SeedRange(seedList[i], lastVal));
		}

		for (Mapping mapping : mappings) {
			ArrayList<SeedRange> newSeedRanges = new ArrayList<SeedRange>();
			ArrayList<SeedRange> rangesToCheck = new ArrayList<SeedRange>();
			for (SeedRange sr : seedRanges) {
				rangesToCheck.add(sr);
				while (!rangesToCheck.isEmpty()) {
					boolean found = false;
					SeedRange toCheck = rangesToCheck.get(0);
					rangesToCheck.remove(0);
					for (Range range : mapping.ranges) {
						if (intersects(toCheck, range)) {
							rangesToCheck.addAll(getUnIntersectedParts(toCheck, range));
							newSeedRanges.add(getIntersectedPart(toCheck, range));
							found = true;
							break;
						}
					}
					if (!found) {
						newSeedRanges.add(sr);
					}
				}
			}
			seedRanges = newSeedRanges;
		}

		for (SeedRange sr : seedRanges) {
			if (sr.start < answer) {
				answer = sr.start;
			}
		}

		System.out.println(answer + "");
	}

	private static boolean intersects(SeedRange sr, Range r) {
		return r.length > 0 && (sr.start < (r.fromStart + r.length) && sr.end >= r.fromStart);
	}

	private static ArrayList<SeedRange> getUnIntersectedParts(SeedRange sr, Range r) {
		ArrayList<SeedRange> newRanges = new ArrayList<SeedRange>();

		if (sr.start < r.fromStart) {
			newRanges.add(new SeedRange(sr.start, r.fromStart - 1));
		}
		if (sr.end >= r.fromStart + r.length) {
			newRanges.add(new SeedRange(r.fromStart + r.length, sr.end));
		}
		return newRanges;
	}

	private static SeedRange getIntersectedPart(SeedRange sr, Range r) {

		long start = sr.start <= r.fromStart ? r.toStart : r.valueOf(sr.start);
		long end = sr.end >= r.fromStart + r.length - 1 ? r.toStart + r.length - 1 : r.valueOf(sr.end);

		return new SeedRange(start, end);
	}
}

class SeedRange {
	long start;
	long end;

	public SeedRange(long s, long e) {
		start = s;
		end = e;
	}

	public String toString() {
		return "SeedRange " + start + " - " + end;
	}
}

class Mapping {
	String from, to;
	List<Range> ranges;

	public Mapping(String line) {// seed-to-soil map:
		StringTokenizer st = new StringTokenizer(line, ":- ");
		from = st.nextToken();
		st.nextToken();
		to = st.nextToken();
		ranges = new ArrayList<Range>();

	}

	public Mapping(String f, String t) {
		from = f;
		to = t;
		ranges = new ArrayList<Range>();
	}

	public void addRange(String line) throws IOException {
		ranges.add(new Range(line));
	}

	public String toString() {
		return "Mapping - from: " + from + " to: " + to + " ranges: " + ranges.size();
	}
}

class Range {
	long toStart, fromStart, length;

	public Range(String line) throws IOException {
		StringTokenizer st = new StringTokenizer(line, " ");
		toStart = Long.parseLong(st.nextToken());
		fromStart = Long.parseLong(st.nextToken());
		length = Long.parseLong(st.nextToken());

	}

	public Range(long ts, long fs, long l) {
		toStart = ts;
		fromStart = fs;
		length = l;
	}

	public boolean isIn(long val) {
		if (val >= fromStart) {
			if (val <= (fromStart + length)) {
				return true;
			}
		}
		return false;
	}

	public long valueOf(long fromVal) {
		return fromVal + (toStart - fromStart);
	}

	public String toString() {
		return "Range - from: " + fromStart + " to: " + toStart + " length: " + length;
	}
}
