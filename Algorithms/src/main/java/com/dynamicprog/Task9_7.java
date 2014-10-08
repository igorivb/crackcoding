package com.dynamicprog;


public class Task9_7 {

	public enum Color {
		Black, White, Red, Yellow, Green
	}
	
	public static String PrintColor(Color c) {
		switch(c) {
		case Black:
			return "1";
		case White:
			return "0";
		case Red:
			return "R";
		case Yellow:
			return "Y";
		case Green:
			return "G";
		}
		return "X";
	}
	
	public static void PrintScreen(Color[][] screen) {
		for (int i = 0; i < screen.length; i++) {
			for (int j = 0; j < screen[0].length; j++) {
				System.out.print(PrintColor(screen[i][j]));
			}
			System.out.println();
		}
	}
	
	public static int randomInt(int n) {
		return (int) (Math.random() * n);
	}
	
	public static boolean PaintFill(Color[][] screen, int x, int y, Color ocolor, Color ncolor) {
		if (x < 0 || x >= screen[0].length || y < 0 || y >= screen.length) {
			return false;
		}
		if (screen[y][x] == ocolor) {
			screen[y][x] = ncolor;
			PaintFill(screen, x - 1, y, ocolor, ncolor); // left
			PaintFill(screen, x + 1, y, ocolor, ncolor); // right
			PaintFill(screen, x, y - 1, ocolor, ncolor); // top
			PaintFill(screen, x, y + 1, ocolor, ncolor); // bottom						
			
//			for (int i = y - 1; i < y - 1 + 3; i ++) {
//				for (int j = x - 1; j < x - 1 + 3; j ++) {
//					PaintFill(screen, j, i, ocolor, ncolor);
//				}
//			}
			
		}
		return true;
	}
	
	public static boolean PaintFill(Color[][] screen, int x, int y, Color ncolor) {
		if (screen[y][x] == ncolor) return false;
		return PaintFill(screen, x, y, screen[y][x], ncolor);
	}
	
	public static void main(String[] args) {
//		int N = 10;
//		Color[][] screen = new Color[N][N];
//		for (int i = 0; i < N; i++) {
//			for (int j = 0; j < N; j++) {
//				screen[i][j] = Color.Black;
//			}			
//		}
//		for (int i = 0; i < 100; i++) {
//			screen[randomInt(N)][randomInt(N)] = Color.Green;
//		}
		
		Color[][] screen = new Color[][] {
			{Color.White,Color.White,Color.White,Color.White,Color.Black,Color.White},
			{Color.White,Color.Black,Color.White,Color.Black,Color.White,Color.White},
			{Color.White,Color.Black,Color.Black,Color.White,Color.White,Color.White},
			{Color.White,Color.White, Color.White/*Color.Black*/, Color.Black,Color.Black,Color.White}
		};
		
		
		PrintScreen(screen);
		PaintFill(screen, 2, 2, Color.White);
		System.out.println();
		PrintScreen(screen);
	}

}
