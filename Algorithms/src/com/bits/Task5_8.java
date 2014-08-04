package com.bits;

import com.Utils;

public class Task5_8 {

	/*
	 * Note: Incorrect
	 */
	public static void drawLineFalse(byte[] screen, int width, int x1, int x2, int y) {
		//find start and end bytes
		int start = y * width + x1;
		int end = y * width + x2;
		
		//set bytes from [start, end] to 1s
		for (int i = start; i <= end; i ++) {
			screen[i] = ~0;
		}
	}
	
	public static void drawLine(byte[] screen, int width, int x1, int x2, int y) {
		//PRE:
		if (x1 > x2) {
			throw new IllegalArgumentException(String.format("x1 > x2. x1=%s, x2=%s", x1, x2));
		}
		
		final int size = 8;
		
		int start = x1 / size + width / size * y;				
		int end =   x2 / size + width / size * y;		
		System.out.println(String.format("start: %s, end: %s", start, end));
		
		//set intermediate bytes
		for (int i = start + 1; i < end; i ++) {
			screen[i] = (byte) 0xFF;
		}
		
		//set start and end bytes
		int from = x1 % size;
		int to =   x2 % size;
		
		//if start and end in different bytes
		if (start != end) {
			int pos = size - from - 1;
			int mask = 1 << (pos + 1);
			screen[start] = (byte) (mask - 1); 
		
			//screen[end] = (byte) (-1 << (size - to - 1));
			pos = size - to - 1;
			mask = (1 << pos) - 1;
			screen[end] = (byte)(~mask);
		} else {
			int count = x2 - x1 + 1;
			int mask = (1 << count) - 1;
			screen[start] = (byte) (mask << (size - to - 1));
		}		
	}
	
	public static void main(String[] args) {
		byte[] screen = new byte[8];
				
		int width = 32;
//		int x1 = 2, x2 = 4;
//		int y = 1;
		
		int x1 = 2, x2 = 18;
		int y = 1;
		
		drawLine(screen, width, x1, x2, y);
		
		for (int i = 0; i < screen.length; i ++) {
			
			StringBuilder str = new StringBuilder();
			
			str.append(String.format("%1s:%s", i, Utils.byteToBinaryString(screen[i])));
			
			if (i < screen.length - 1) {
				if ((i + 1) % (width / 8) == 0) {
					str.append("\n");		
				} else {
					str.append(",");
				}
			}									
			System.out.print(str);
		}
	}
}
