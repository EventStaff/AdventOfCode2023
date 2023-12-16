package day15;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;

//Determine the ASCII code for the current character of the string.
//Increase the current value by the ASCII code you just determined.
//Set the current value to itself multiplied by 17.
//Set the current value to the remainder of dividing itself by 256.

//rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7
//52

public class Day15 {

	public static void main(String[] args) throws IOException {
		// part1();
		part2();
	}

	public static void part1() throws IOException {

		FileInputStream in = new FileInputStream(new File("src/day15/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		long answer = 0;
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, ", ");
			while (st.hasMoreTokens()) {
				Lense l = new Lense(st.nextToken());
				long next = l.hash;
				answer += next;
			}
		}
		System.out.println("Total:" + answer);

	}

	public static void part2() throws IOException {

		FileInputStream in = new FileInputStream(new File("src/day15/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		long answer = 0;
		Box boxes[] = new Box[256];
		for (int i = 0; i < boxes.length; i++) {
			boxes[i] = new Box();
		}
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, ", ");
			while (st.hasMoreTokens()) {
				Lense l = new Lense(st.nextToken());
				if (l.focalLength >= 0) {
					boxes[l.hash].add(l);
				} else {
					boxes[l.hash].remove(l);
				}
			}
		}
		for (int i = 0; i < boxes.length; i++) {
			answer += boxes[i].getFocusingPower(i + 1);
		}
		System.out.println("Total:" + answer);

	}

}

class Box {
	Vector<Lense> lenses;

	public Box() {
		lenses = new Vector<Lense>();
	}

	public void add(Lense l) {
		for (int i = 0; i < lenses.size(); i++) {
			if (lenses.get(i).name.equals(l.name)) {
				lenses.remove(i);
				lenses.insertElementAt(l, i);
				return;
			}
		}
		lenses.add(l);
	}

	public void remove(Lense l) {
		for (int i = 0; i < lenses.size(); i++) {
			if (lenses.get(i).name.equals(l.name)) {
				lenses.remove(i);
				return;
			}
		}
	}

	public long getFocusingPower(int boxNum) {
		long totalPower = 0;
		for (int i = 0; i < lenses.size(); i++) {
			long lensePower = boxNum * (i + 1) * lenses.get(i).focalLength;
			totalPower += lensePower;
		}
		return totalPower;
	}
}

class Lense {
	String name;
	int focalLength;
	int hash;

	public Lense(String value) {
		StringTokenizer st = new StringTokenizer(value, "=-");
		name = st.nextToken();
		if (st.hasMoreTokens()) {
			focalLength = Integer.parseInt(st.nextToken());
		} else {
			focalLength = -1;
		}
		hash = 0;
		for (char c : name.toCharArray()) {
			hash = ((hash + (int) c) * 17) % 256;
		}

	}

	public String toString() {
		return "[" + name + " " + focalLength + "]";
	}
}
