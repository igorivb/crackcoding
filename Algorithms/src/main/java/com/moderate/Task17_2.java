package com.moderate;

/**
 * Design an algorithm to figure out if someone has won a game of tic-tac-toe
 */
public class Task17_2 {
	
	enum Status { CROSS, ZERO, NONE }
	
	public static Status hasWinner(Status[][] mas) {
		final int len = mas.length;
		Status first;
		boolean win;
		
		//horizontal		
		for (int i = 0; i < len; i ++) {			
			if ((first = mas[i][0]) != Status.NONE) {
				win = true;
				for (int j = 1; j < len; j ++) {
					if (mas[i][j] != first) {
						win = false;
						break;
					}
				}
				if (win) {
					return first;
				}
			}			
		}
		
		//vertical
		for (int j = 0; j < len; j ++) {			
			if ((first = mas[0][j]) != Status.NONE) {
				win = true;
				for (int i = 1; i < len; i ++) {
					if (mas[i][j] != first) {
						win = false;
						break;
					}
				}
				if (win) {
					return first;
				}
				
			}
		}
		
		//diagonal 1
		if ((first = mas[0][0]) != Status.NONE) {
			win = true;
			for (int i = 1; i < len; i ++) {
				if (mas[i][i] != first) {
					win = false;
					break;
				}
			}
			if (win) {
				return first;
			}
		}
		
		
		//diagonal 2
		if ((first = mas[len - 1][0]) != Status.NONE) {
			win = true;
			for (int i = 1; i < len; i ++) {
				if (mas[len - i - 1][i] != first) {
					win = false;
					break;
				}
			}
			if (win) {
				return first;
			}
		}
		
		return Status.NONE;
	}
	
	public static void main(String[] args) {
		String[] str = {
			"_xx", 
		    "xx_",
			"x_0"
		};
		
		Status[][] mas = new Status[str.length][str.length];
		for (int i = 0; i < str.length; i ++) {
			for (int j = 0; j < str.length; j ++) {
				char c = str[i].charAt(j);
				mas[i][j] = c == 'x' ? Status.CROSS : (c == '0' ? Status.ZERO : Status.NONE);
			}
		} 

		System.out.println("Winner: " + hasWinner(mas));
	}

}
