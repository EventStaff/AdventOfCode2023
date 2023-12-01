package day1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Day1 {

	public static void main(String[] args) throws IOException{

		FileInputStream in = new FileInputStream(new File("src/day1/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);	
		String line = "";
		int answer = 0;
		while ((line = br.readLine()) != null) {
			int lineValue = Integer.parseInt(findFirstInt(line) + "" + findLastInt(line));
			answer += lineValue;
		}
		System.out.println("Total:" + answer);

	}

	private static char findFirstInt(String line) {
		int firstDigitIndex = -1;
		for (int i = 0; i < line.length(); i++) {
			if (Character.isDigit(line.charAt(i))) {
				firstDigitIndex = i;
				break;
			}
		}
		
		char spelledVal = line.charAt(firstDigitIndex);
		int indexOfSpelledVal = firstDigitIndex;
		
		if(line.indexOf("one") >= 0 && line.indexOf("one") < firstDigitIndex) {
			spelledVal = '1';
			indexOfSpelledVal = line.indexOf("one");
		}
		if(line.indexOf("two") >= 0 && line.indexOf("two") < indexOfSpelledVal) {
			spelledVal = '2';
			indexOfSpelledVal = line.indexOf("two");
		}
		if(line.indexOf("three") >= 0 && line.indexOf("three") < indexOfSpelledVal) {
			spelledVal = '3';
			indexOfSpelledVal = line.indexOf("three");
		}
		if(line.indexOf("four") >= 0 && line.indexOf("four") < indexOfSpelledVal) {
			spelledVal = '4';
			indexOfSpelledVal = line.indexOf("four");
		}
		if(line.indexOf("five") >= 0 && line.indexOf("five") < indexOfSpelledVal) {
			spelledVal = '5';
			indexOfSpelledVal = line.indexOf("five");
		}
		if(line.indexOf("six") >= 0 && line.indexOf("six") < indexOfSpelledVal) {
			spelledVal = '6';
			indexOfSpelledVal = line.indexOf("six");
		}
		
		if(line.indexOf("seven") >= 0 && line.indexOf("seven") < indexOfSpelledVal) {
			spelledVal = '7';
			indexOfSpelledVal = line.indexOf("seven");
		}
		if(line.indexOf("eight") >= 0 && line.indexOf("eight") < indexOfSpelledVal) {
			spelledVal = '8';
			indexOfSpelledVal = line.indexOf("eight");
		}
		if(line.indexOf("nine") >= 0 && line.indexOf("nine") < indexOfSpelledVal) {
			spelledVal = '9';
			indexOfSpelledVal = line.indexOf("nine");
		}
		
		return spelledVal;

	}

	private static char findLastInt(String line) {
		int lastDigitIndex = -1;
		for (int i = line.length()-1; i >= 0; i--) {
			if (Character.isDigit(line.charAt(i))) {
				lastDigitIndex = i;
				break;
			}
		}

		char spelledVal = line.charAt(lastDigitIndex);
		int indexOfSpelledVal = lastDigitIndex;
		
		if(line.lastIndexOf("one") > lastDigitIndex) {
			spelledVal = '1';
			indexOfSpelledVal = line.lastIndexOf("one");
		}
		if(line.lastIndexOf("two") > indexOfSpelledVal) {
			spelledVal = '2';
			indexOfSpelledVal = line.lastIndexOf("two");
		}
		if(line.lastIndexOf("three") > indexOfSpelledVal) {
			spelledVal = '3';
			indexOfSpelledVal = line.lastIndexOf("three");
		}
		if(line.lastIndexOf("four") > indexOfSpelledVal) {
			spelledVal = '4';
			indexOfSpelledVal = line.lastIndexOf("four");
		}
		if(line.lastIndexOf("five") > indexOfSpelledVal) {
			spelledVal = '5';
			indexOfSpelledVal = line.lastIndexOf("five");
		}
		if(line.lastIndexOf("six") > indexOfSpelledVal) {
			spelledVal = '6';
			indexOfSpelledVal = line.lastIndexOf("six");
		}
		
		if(line.lastIndexOf("seven") > indexOfSpelledVal) {
			spelledVal = '7';
			indexOfSpelledVal = line.lastIndexOf("seven");
		}
		if(line.lastIndexOf("eight") > indexOfSpelledVal) {
			spelledVal = '8';
			indexOfSpelledVal = line.lastIndexOf("eight");
		}
		if(line.lastIndexOf("nine") > indexOfSpelledVal) {
			spelledVal = '9';
			indexOfSpelledVal = line.lastIndexOf("nine");
		}
		
		return spelledVal;

	}

	
}
