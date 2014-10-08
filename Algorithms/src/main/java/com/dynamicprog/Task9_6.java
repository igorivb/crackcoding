package com.dynamicprog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Task9_6 {

	static final char OPEN_BRACKET = '(';
	static final char CLOSE_BRACKET = ')';
	
	static void print(List<List<Character>> levelPairs) {
		//print
		for (int i = 0; i < levelPairs.size(); i ++) {			
			String str = getPairAsString(levelPairs.get(i));
			System.out.printf("%2s. %s%n", (i + 1), str);			
		}
	}
	
	static String getPairAsString(List<Character> list) {
		StringBuilder str = new StringBuilder();		
		for (Character ch : list) {
			str.append(ch);
		}		
		return str.toString();
	}
	
	static void processList(List<Character> prevList, List<List<Character>> curPairs, int pos) {
		//process '('		
		System.out.printf("%2s. Process prevList: %s%n", (pos  + 1), getPairAsString(prevList));
		int prevLen = prevList.size();
		for (int i = 1; i < prevLen + 1; i ++) {
			if (i == prevLen ||  prevList.get(i) != OPEN_BRACKET) {
				//insert '(' here
				List<Character> openList = new ArrayList<>(prevList);
				openList.add(i, OPEN_BRACKET);
				System.out.printf("  Insert open bracket, pos: %2s, list: %s%n", i, getPairAsString(openList));
				
				//process ')' 
				for (int j = i + 1; j < prevLen + 2; j ++) {					
					if (j == prevLen + 1 || openList.get(j) != CLOSE_BRACKET) {
						//insert ')' here
						List<Character> closeList = new ArrayList<>(openList);
						closeList.add(j, CLOSE_BRACKET);
						curPairs.add(closeList);
						
						System.out.printf("    Insert close bracket, pos: %2s, list: %s%n", j, getPairAsString(closeList));
					}
				}
			}
		}
	}
	
	public static List<List<Character>> getAllCombinations(int numberOfPairs) {
		List<List<Character>> levelPairs = new ArrayList<>();
		levelPairs.add(Arrays.asList(OPEN_BRACKET, CLOSE_BRACKET));
		
		for (int np = 1; np < numberOfPairs; np ++) {
			System.out.println("\n\nPrev Number of pairs: " + np);
			List<List<Character>> curPairs = new ArrayList<>();
			
			for (int i = 0; i < levelPairs.size(); i ++) {
				List<Character> prevLevelPair = levelPairs.get(i);
				processList(prevLevelPair, curPairs, i);								
			}	
			
			levelPairs = curPairs;
		}	
		
		return levelPairs;
	}
	
	//TODO: not valid, because it contains duplicates
	public static void main1(String[] args) {
		int numberOfPairs = 3;
		
		List<List<Character>> levelPairs = getAllCombinations(numberOfPairs);				
		
		System.out.println("Number of pairs: " + numberOfPairs);
		print(levelPairs);
		
		//check duplicates
		Set<List<Character>> set = new HashSet<>();
		for (int i = 0; i < levelPairs.size(); i ++) {
			List<Character> pair = levelPairs.get(i);
			if (!set.add(pair)) {
				System.err.printf("Duplicate: %2s. %s%n", (i + 1), getPairAsString(pair));
			}			
		}
	}
	
	
	//-------------------------------
	
	
	//from book
	public static void addParen(ArrayList<String> list, int leftRem, int rightRem,
			char[] str, int count) {
		if (leftRem < 0 || rightRem < leftRem) {
			System.err.println("invalid state");
			return; // invalid state
		}

		if (leftRem == 0 && rightRem == 0) { /* no more parens left */
			String s = String.copyValueOf(str);
			list.add(s);
			System.out.println("----- Copy value: " + s);
		} else {
			/* Add left paren, if there are any left parens remaining. */
			if (leftRem > 0) {
				System.out.printf("Add Left paren. left: %d, right: %d, count: %d, str: %s%n", leftRem, rightRem, count, new String(str));
				str[count] = '(';
				addParen(list, leftRem - 1, rightRem, str, count + 1);
			}

			/* Add right paren., if expression is valid */
			if (rightRem > leftRem) {
				System.out.printf("Add Right paren. left: %d, right: %d, count: %d, str: %s%n", leftRem, rightRem, count, new String(str));
				str[count] = ')';
				addParen(list, leftRem, rightRem - 1, str, count + 1);
			}
		}
	}

	public static ArrayList<String> generateParens(int count) {
		char[] str = new char[count * 2];
		ArrayList<String> list = new ArrayList<String>();
		addParen(list, count, count, str, 0);
		return list;
	}	
	
	
	
	//-------------------------------- My again
	
	public static void addParanMy(int leftRem, int rightRem, int count, char[] str, List<String> list) {
		if (leftRem == 0 && rightRem == 0) {
			//full string, add to list
			list.add(new String(str)); 
		} else {
			//left
			if (leftRem > 0) {
				str[count] = OPEN_BRACKET;
				addParanMy(leftRem - 1, rightRem, count + 1, str, list);
			}
			
			//right
			if (rightRem > leftRem) {
				str[count] = CLOSE_BRACKET;
				addParanMy(leftRem, rightRem - 1, count + 1, str, list);
			}
		}
	}
	
	public static List<String> generateParensMy(int count) {
		List<String> list = new ArrayList<>();
		char[] str = new char[count << 1];
		addParanMy(count, count, 0, str, list);
		return list;		
	}
	
	
	static boolean verify(String str) {
		int leftCount = 0, rightCount = 0;
		for (int i = 0; i < str.length(); i ++) {
			char c = str.charAt(i);
			if (c == OPEN_BRACKET) {
				leftCount ++;
			} else if (c == CLOSE_BRACKET) {
				rightCount ++;
				if (rightCount > leftCount) {
					System.out.printf("Not valid, index: %2s%n", i);
					return false;
				}
			} 
		}
		return true;
	}
	
	static Random rand = new Random(System.currentTimeMillis());
	
	static String generateRandomStr(int count) {
		char[] str = new char[count << 1];
		int leftRem = count;
		int rightRem = count;
		
		for (int i = 0; i < str.length; i ++) {			
			boolean left = rand.nextInt(2) == 0;				
			left = (leftRem == 0 || rightRem == 0) ? (leftRem > 0) : left;
				
			if (left) {
				str[i] = OPEN_BRACKET;
				leftRem --;
			} else {
				str[i] = CLOSE_BRACKET;
				rightRem --;
			}
		}
		return new String(str);
	}
	
	
	public static void main(String[] args) {
		int count = 3;
		
		List<String> list = generateParens(count);
		for (int i = 0; i < list.size(); i ++) {
			String str = list.get(i);
			System.out.printf("%2s. %s%n", (i + 1), str);
		}
		
		System.out.println("\n");
		for (int i = 0; i < list.size(); i ++) {
			String str = list.get(i);
			System.out.printf("%2s. %s%n", (i + 1), str);
		}
		
		System.out.println("\nValidate strings:\n");
		for (int i = 0; i < 10; i ++) {
			//int i = 0; String str = "())(()";
		
			String str = generateRandomStr(4);
			System.out.printf("%s. string: %s%n", (i + 1), str);
			verify(str); 
		}
	}
	
}
