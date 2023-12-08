package day7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Day7 {

	public static final boolean part1 = false;

	public static void main(String[] args) throws IOException {
		FileInputStream in = new FileInputStream(new File("src/day7/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);

		ArrayList<Hand> hands = new ArrayList<Hand>();

		String line = "";
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			hands.add(new Hand(st.nextToken(), Integer.parseInt(st.nextToken())));
		}
		Collections.sort(hands);
		int result = 0;
		for (int i = 0; i < hands.size(); i++) {
			result += hands.get(i).bid * (i + 1);
		}
		System.out.println("Answer: " + result);
	}

}

class Hand implements Comparable<Hand> {
	static final int FIVE = 6;
	static final int FOUR = 5;
	static final int FULL = 4;
	static final int THREE = 3;
	static final int TWOPAIR = 2;
	static final int PAIR = 1;
	static final int HIGHCARD = 0;
	char cards[];
	int handType;
	int bid;

	public String toString() {
		return "Type: " + handType + " - [" + cards[0] + "," + cards[1] + "," + cards[2] + "," + cards[3] + ","
				+ cards[4] + "," + "] BID: " + bid;
	}

	public Hand(String hand, int bid) {
		cards = new char[5];
		for (int i = 0; i < cards.length; i++) {
			cards[i] = hand.charAt(i);
		}
		handType = Hand.determineHandType(cards);
		this.bid = bid;
	}

	private static int determineHandType(char cards[]) {
		if (Day7.part1) {
			return determineHandTypePart1(cards);
		} else {
			return determineHandTypePart2(cards);
		}
	}

	private static int determineHandTypePart1(char cards[]) {
		int count[] = new int[13];
		for (char card : cards) {
			if (card == 'A') {
				count[12]++;
			} else if (card == 'K') {
				count[11]++;
			} else if (card == 'Q') {
				count[10]++;
			} else if (card == 'J') {
				count[9]++;
			} else if (card == 'T') {
				count[8]++;
			} else {
				count[Character.getNumericValue(card) - 2]++;
			}
		}
		int tripsCount = 0;
		int dubsCount = 0;

		for (int c : count) {
			if (c == 5)
				return FIVE;
			else if (c == 4)
				return FOUR;
			else if (c == 3) {
				tripsCount++;
			} else if (c == 2) {
				dubsCount++;
			}
		}
		if (tripsCount == 1 && dubsCount == 1) {
			return FULL;
		} else if (tripsCount == 1) {
			return THREE;
		} else if (dubsCount == 2) {
			return TWOPAIR;
		} else if (dubsCount == 1) {
			return PAIR;
		} else {
			return HIGHCARD;
		}

	}

	private static int determineHandTypePart2(char cards[]) {
		int count[] = new int[13];
		int jCount = 0;
		for (char card : cards) {
			if (card == 'A') {
				count[12]++;
			} else if (card == 'K') {
				count[11]++;
			} else if (card == 'Q') {
				count[10]++;
			} else if (card == 'J') {
				count[9]++;
				jCount++;
			} else if (card == 'T') {
				count[8]++;
			} else {
				count[Character.getNumericValue(card) - 2]++;
			}
		}
		int tripsCount = 0;
		int dubsCount = 0;

		for (int c : count) {
			if (c == 5)
				return FIVE;
			else if (c == 4) {
				if (jCount > 0) {
					return FIVE;
				} else {
					return FOUR;
				}
			} else if (c == 3) {
				tripsCount++;
			} else if (c == 2) {
				dubsCount++;
			}
		}
		if (tripsCount == 1 && dubsCount == 1) {
			if (jCount > 0) {
				return FIVE;
			} else {
				return FULL;
			}
		} else if (tripsCount == 1) {
			if (jCount == 3) {
				return FOUR;
			} else if (jCount == 1) {
				return FOUR;
			} else if (jCount == 0) {
				return THREE;
			} else {
				throw new RuntimeException("WTF checking for THREE of a kind");
			}
		} else if (dubsCount == 2) {

			if (jCount == 2) {
				return FOUR;
			} else if (jCount == 1) {
				return FULL;
			} else if (jCount == 0) {
				return TWOPAIR;
			} else {
				throw new RuntimeException("WTF check for two pair");
			}
		} else if (dubsCount == 1) {
			if (jCount == 2) {
				return THREE;
			} else if (jCount == 1) {
				return THREE;
			} else if (jCount == 0) {
				return PAIR;
			} else {
				throw new RuntimeException("WTF check for one pair");
			}
		} else {
			if (jCount == 1) {
				return PAIR;
			} else if (jCount == 0) {

				return HIGHCARD;
			} else {
				throw new RuntimeException("WTF check for highcard");
			}

		}

	}

	public int compareTo(Hand h) {
		if (h.handType != handType) {
			return handType - h.handType;
		} else {
			for (int i = 0; i < cards.length; i++) {
				if (h.cards[i] != cards[i]) {
					return toNum(cards[i]) - toNum(h.cards[i]);
				}
			}
		}
		throw new RuntimeException("Unable to compare two Hands for some reason");
	}

	private int toNum(char card) {
		if (Day7.part1) {
			return toNumPart1(card);

		} else {
			return toNumPart2(card);
		}
	}

	private int toNumPart1(char card) {
		if (card == 'A') {
			return 12;
		} else if (card == 'K') {
			return 11;
		} else if (card == 'Q') {
			return 10;
		} else if (card == 'J') {
			return 9;
		} else if (card == 'T') {
			return 8;
		} else {
			return Integer.parseInt(card + "") - 2;
		}
	}

	private int toNumPart2(char card) {
		if (card == 'A') {
			return 12;
		} else if (card == 'K') {
			return 11;
		} else if (card == 'Q') {
			return 10;
		} else if (card == 'J') {
			return -1;
		} else if (card == 'T') {
			return 8;
		} else {
			return Integer.parseInt(card + "") - 2;
		}
	}

	public boolean equals(Hand h) {
		return compareTo(h) == 0;
	}
}
