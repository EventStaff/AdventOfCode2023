package day8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Day8 {

	public static void main(String[] args) throws IOException {
		// part1();
		part2();
	}

	public static void part1() throws IOException {
		FileInputStream in = new FileInputStream(new File("src/day8/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);

		Instructions instr = new Instructions(br.readLine());
		br.readLine();

		String line = "";
		HashMap<String, Node> nodes = new HashMap<String, Node>();
		while ((line = br.readLine()) != null) { // DBQ = (RTP, NBX)
			StringTokenizer st = new StringTokenizer(line, " =(,)");
			String name = st.nextToken();
			nodes.put(name, new Node(name, st.nextToken(), st.nextToken()));
		}
		int steps = traverse(nodes, instr, "AAA", "ZZZ");

		System.out.println("Answer: " + steps);
	}

	public static void part2() throws IOException {
		FileInputStream in = new FileInputStream(new File("src/day8/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);

		Instructions instr = new Instructions(br.readLine());
		br.readLine();

		String line = "";
		HashMap<String, Node> nodes = new HashMap<String, Node>();
		ArrayList<Node> startNodes = new ArrayList<Node>();

		while ((line = br.readLine()) != null) { // DBQ = (RTP, NBX)
			StringTokenizer st = new StringTokenizer(line, " =(,)");
			String name = st.nextToken();
			Node n = new Node(name, st.nextToken(), st.nextToken());
			nodes.put(name, n);
			if (name.charAt(2) == 'A') {
				startNodes.add(n);
			}
		}
		for (int i = 0; i < startNodes.size(); i++) {
			instr.reset();
			traverse(nodes, instr, startNodes.get(i), "ZZZ");
		}

		System.out.println("Answer: " + lcm(startNodes));
	}

	public static long lcm(int[] lengths) {
		long lcm = 1;
		int divisor = 2;

		while (true) {
			int factoredLengths = 0;
			boolean divisible = false;

			for (int i = 0; i < lengths.length; i++) {

				if (lengths[i] == 1) {
					factoredLengths++;
				} else if (lengths[i] == divisor) {
					factoredLengths++;
					lengths[i] /= divisor;
					divisible = true;
				} else if (lengths[i] % divisor == 0) {

					lengths[i] /= divisor;
					divisible = true;

				}
			}

			if (divisible) {
				lcm = lcm * divisor;
			} else {
				divisor++;
			}

			if (factoredLengths == lengths.length) {
				return lcm;
			}
		}

	}

	public static long lcm(ArrayList<Node> nodes) {

		HashSet<String> factors = new HashSet<String>();

		for (int i = 0; i < nodes.size(); i++) {
			factors.addAll(nodes.get(i).zSpots);
		}
		ArrayList<Integer> ints = new ArrayList<Integer>();
		for (String factor : factors) {
			ints.add(Integer.valueOf(factor));
		}
		Collections.sort(ints);
		int arr[] = new int[ints.size()];
		int original[] = new int[ints.size()];
		for (int i = 0; i < arr.length; i++) {
			original[i] = arr[i] = ints.get(i).intValue();
		}
		return lcm(arr);
	}

	public static void traverse(HashMap<String, Node> nodes, Instructions instr, Node startNode, String goal) {
		String next = startNode.name;
		while (true) {
			int pos = instr.position;
			char dir = instr.next();
			Node current = nodes.get(next);
			next = (dir == 'L' ? current.l : current.r);
			if (!startNode.addNew(next + pos)) {
				return;
			}
		}

	}

	public static int traverse(HashMap<String, Node> nodes, Instructions instr, String start, String goal) {
		char dir;
		Node current;
		String next = start;
		int count = 0;

		while (true) {
			dir = instr.next();
			current = nodes.get(next);
			next = (dir == 'L' ? current.l : current.r);
			count++;
			// System.out.println(current + " dir: " + dir + " next: " + next + " goal: " +
			// goal);
			if (next.equals(goal)) {
				return count;
			}
		}

	}

	public static int traverse(HashMap<String, Node> nodes, Instructions instr, ArrayList<Node> startNodes,
			String goal) {
		int count = 0;

		while (true) {
			char dir = instr.next();
			count++;
			boolean allAtEnd = true;
			ArrayList<Node> nextNodes = new ArrayList<Node>();
			for (int i = 0; i < startNodes.size(); i++) {
				Node current = startNodes.get(i);
				String next = (dir == 'L' ? current.l : current.r);
				if (allAtEnd && next.charAt(2) != 'Z') {
					allAtEnd = false;
				}
				nextNodes.add(nodes.get(next));
			}
			startNodes = nextNodes;
			if (allAtEnd) {
				return count;
			}
		}

	}
}

class Instructions {
	String instructions;
	int position;

	public Instructions(String list) {
		instructions = list;
		position = instructions.length();
	}

	public char next() {
		if (position < instructions.length()) {
			return instructions.charAt(position++);
		} else {
			position = 1;
			return instructions.charAt(0);
		}
	}

	public void reset() {
		position = instructions.length();
	}
}

class Node {
	public String l, r, name;
	ArrayList<String> nodeposes;
	ArrayList<String> zSpots;

	public Node(String name, String left, String right) {
		this.name = name;
		l = left;
		r = right;
		nodeposes = new ArrayList<String>();
		zSpots = new ArrayList<String>();
	}

	public String toString() {
		return "Node: " + name + " [" + l + ", " + r + "] - length: " + nodeposes.size();
	}

	public boolean addNew(String nodepos) {
		if (nodeposes.contains(nodepos)) {
			return false;
		} else {
			nodeposes.add(nodepos);
			if (nodepos.charAt(2) == 'Z') {
				zSpots.add(nodeposes.size() + "");
			}
			return true;
		}
	}

	public void printNodePoses() {
		for (String s : nodeposes) {
			System.out.println(s);
		}
	}

	public void printZSpots() {
		for (String s : zSpots) {
			System.out.println(s);
		}
	}
}
