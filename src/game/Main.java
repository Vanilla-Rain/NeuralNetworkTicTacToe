package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import AI.NeuralNetwork;
import AI.Vector;

public class Main {
	static int aiCount = 50;
	static ArrayList<NeuralNetwork> ais = new ArrayList<NeuralNetwork>();

	public static void main(String[] args) {
		for (int i = 0; i < aiCount; i++) {
			ais.add(new NeuralNetwork());
		}
		s = new Scanner(System.in);
		for (int x = 0; x < 5000; x++) {
			if(x % 100 == 0 && x != 0) {
				System.out.println(x);
				ArrayList<NeuralNetwork> newAis = new ArrayList<NeuralNetwork>();
				for (NeuralNetwork n : ais) {
					if(n.wins >= (x / 100.0) * 50 || newAis.size() == 0) {
						newAis.add(n);
					}
				}
				ais = new ArrayList<NeuralNetwork>();
				for(int i = 0; i < aiCount - newAis.size(); i++) {
					 Random rand = new Random();
					ais.add(new NeuralNetwork(newAis.get(rand.nextInt(newAis.size()))));
				}
				ais.addAll(newAis);
			}
			Collections.shuffle(ais);
			for (int i = 0; i < aiCount; i++) {
				initialize();
				NeuralNetwork aiX = ais.get(i);
				NeuralNetwork aiO = ais.get(i + 1);
				aiX.setChar('X');
				aiO.setChar('O');
				i++;
				while (controlTurnsAI(aiX, aiO) == 0) {
					// s.next();
				}
				if (checkWinCondition(XTurn ? 'O' : 'X') == 1) {
				//	System.out.print((XTurn ? 'O' : 'X') + " Wins!\n\n");
					if (XTurn)
						aiO.wins++;
					else
						aiX.wins++;
					aiX.learn(XTurn ? false : true);
					aiO.learn(XTurn ? true : false);
				} else {
				//	System.out.print("Draw. \n\n");
					aiX.learn(true);
					aiO.learn(true);
				}
			}
			// System.out.println("press any key to go again");
			// s.next();
			XTurn = true;
		}
		int mostWins = 0;
		NeuralNetwork nw = null;
		for (NeuralNetwork n : ais) {
			if (n.wins > mostWins) {
				nw = n;
				mostWins = n.wins;
			}
		}
		initialize();
		nw.setChar('O');
		while (controlTurnsPVAiX(nw) == 0)
			;
		if (checkWinCondition(XTurn ? 'O' : 'X') == 1) {
			System.out.print((XTurn ? 'O' : 'X') + " Wins!\n\n");
		} else {
			System.out.print("Draw.\n\n");
		}
		initialize();
		nw.setChar('X');
		while (controlTurnsPVAiO(nw) == 0)
			;
		if (checkWinCondition(XTurn ? 'O' : 'X') == 1) {
			System.out.print((XTurn ? 'O' : 'X') + " Wins!\n\n");
		} else {
			System.out.print("Draw.\n\n");
		}
		// System.out.print("\n");
	}

	static char[][] board = new char[3][3];

	static void initialize() {
		XTurn = true;
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
		System.out.print("\n\n");
	}

	static boolean XTurn = true;

	static void placeVector(Vector v, char myChar) {
		board[v.x][v.y] = myChar;
	}

	static int controlTurns() {
		draw();
		takeTurn((XTurn ? 'X' : 'O'));
		XTurn = !XTurn;
		return checkWinCondition(XTurn ? 'O' : 'X');
	}

	static int controlTurnsAI(NeuralNetwork X, NeuralNetwork O) {

		Vector turn = (XTurn ? X : O).getMove(board);
		placeVector(turn, XTurn ? 'X' : 'O');
		XTurn = !XTurn;
		//draw();
		return checkWinCondition(XTurn ? 'O' : 'X');
	}

	static int controlTurnsPVAiX(NeuralNetwork n) {
		if (XTurn) {
			draw();
			takeTurn((XTurn ? 'X' : 'O'));
			XTurn = !XTurn;
			return checkWinCondition(XTurn ? 'O' : 'X');
		} else {
			Vector turn = n.getMove(board);
			placeVector(turn, XTurn ? 'X' : 'O');
			XTurn = !XTurn;
			//draw();
			return checkWinCondition(XTurn ? 'O' : 'X');
		}
	}

	static int controlTurnsPVAiO(NeuralNetwork n) {
		if (!XTurn) {
			draw();
			takeTurn((XTurn ? 'X' : 'O'));
			XTurn = !XTurn;
			return checkWinCondition(XTurn ? 'O' : 'X');
		} else {
			Vector turn = n.getMove(board);
			placeVector(turn, XTurn ? 'X' : 'O');
			XTurn = !XTurn;
			//draw();
			return checkWinCondition(XTurn ? 'O' : 'X');
		}
	}

	static char get(int x, int y) {
		return board[y][x];
	}

	static int checkWinCondition(char piece) {
		// vertical
		for (int x = 0; x < 3; x++) {
			if (get(x, 0) == piece && get(x, 1) == piece && get(x, 2) == piece) {
				return 1;
			}
		}
		// horizontal
		for (int y = 0; y < 3; y++) {
			if (get(0, y) == piece && get(1, y) == piece && get(2, y) == piece) {
				return 1;
			}
		}
		// diagnol
		if ((get(0, 0) == piece && get(1, 1) == piece && get(2, 2) == piece)
				|| (get(2, 0) == piece && get(1, 1) == piece && get(0, 2) == piece)) {
			return 1;
		}
		if (get(0, 0) != ' ' && get(0, 1) != ' ' && get(0, 2) != ' ' && get(1, 0) != ' ' && get(1, 1) != ' '
				&& get(1, 2) != ' ' && get(2, 0) != ' ' && get(2, 1) != ' ' && get(2, 2) != ' ')
			return -1;
		return 0;
	}

	static Scanner s;

	static void takeTurn(char piece) {
		int x, y;
		do {
			System.out.print("\n" + piece + " turn, Input coordinates (x y)\n");
			x = s.nextInt();
			y = s.nextInt();
		} while (board[y - 1][x - 1] != ' ' || y > 3 || x > 3 || y <= 0 || x <= 0);

		board[y - 1][x - 1] = piece;
	}
}
