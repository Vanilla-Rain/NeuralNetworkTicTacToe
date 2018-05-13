package game;

import java.util.Scanner;

public class Main {
public static void main(String[] args)
{
	s = new Scanner(System.in);
	while (true) {
		initialize();
		while (!controlTurns());
		System.out.print((XTurn ? 'O' : 'X') + " Wins!\n\n");
		XTurn = true;
	}
	
	//System.out.print("\n");
}
static char[][] board = new char[3][3];
static void initialize() {
	for (int y = 0; y < 3; y++) {
		for (int x = 0; x < 3; x++) {
			board[y][x] = ' ';
		}
	}
}
static void draw() {
	for (int y = 0; y < 3; y++) {
		for (int x = 0; x < 3; x++) {
			System.out.print(board[y][x]);
			if (x != 2) {
				System.out.print('|');
			}
		}
		if (y != 2) {
			System.out.print("\n- - -\n");
		}
	}
}
static boolean XTurn = true;
static boolean controlTurns() {
	draw();
	takeTurn((XTurn ? 'X' : 'O'));
	XTurn = !XTurn;
	return checkWinCondition(XTurn ? 'O' : 'X');
}
static char get(int x, int y) {
	return board[y][x];
}
static boolean checkWinCondition(char piece) {
	// vertical
	for (int x = 0; x < 2; x++) {
		if (get(x, 0) == piece && get(x, 1) == piece && get(x, 2) == piece) {
			return true;
		}
	}
	// horizontal
	for (int y = 0; y < 2; y++) {
		if (get(0, y) == piece && get(1, y) == piece && get(2, y) == piece) {
			return true;
		}
	}
	// diagnol
	if ((get(0, 0) == piece && get(1, 1) == piece && get(2, 2) == piece) || (get(2, 0) == piece && get(1, 1) == piece && get(0, 2) == piece)) {
		return true;
	}
	return false;
}
static Scanner s;
static void takeTurn(char piece) {
	int x, y;
	do {
		System.out.print("\n" + piece + " turn, Input coordinates (x y)\n");
		x = s.nextInt();
		y = s.nextInt();
	} while (board[y-1][x-1] != ' ' || y > 3 || x > 3 || y <= 0 || x <= 0);
	
	
	board[y-1][x-1] = piece;
}
}
