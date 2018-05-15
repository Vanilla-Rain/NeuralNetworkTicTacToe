package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import AI.NeuralComparator;
import AI.NeuralNetwork;
import AI.Vector;

public class Main {
	static int aiCount = 100;
	static ArrayList<NeuralNetwork> ais = new ArrayList<NeuralNetwork>();

	public static void main(String[] args) {
		File f = new File("output.txt");
		FileWriter fileWriter = null;
		try {
			
			System.out.println(f.getAbsolutePath());
			if(!f.exists())
				f.createNewFile();
			else {
				f.delete();
				f.createNewFile();
			}
			 fileWriter = new FileWriter(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < aiCount; i++) {
			ais.add(new NeuralNetwork());
		}
		s = new Scanner(System.in);
		for (int x = 0; x < 1000; x++) {
				System.out.println(x);
				if(x != 0) {
					ArrayList<NeuralNetwork> newAis = new ArrayList<NeuralNetwork>();
					Collections.sort(ais,new NeuralComparator());
					int highRatio = 0;
					for (int g = ais.size()-1; g > ais.size() - 2; g--) {
						if(g == ais.size() - 1) {
							//System.out.println(x + " " + ais.get(g).wins);
							System.out.println("HIGH " + ais.get(g).wins + "/" + ais.get(g).losses + "/" + ais.get(g).ties);
								if(x != 0) {
									highRatio = ais.get(g).wins - ais.get(g).losses;
									
								}
								
							
								
						}
						//ais.get(g).learn(ais.get(g).wins - ais.get(g).losses);
						ais.get(g).wins = 0;
						ais.get(g).ties = 0;
						ais.get(g).losses = 0;
						
							newAis.add(ais.get(g));
					}
					int lowRatio = ais.get(0).wins - ais.get(0).losses;
					for (int g = 0; g < 1; g++) {
						System.out.println("LOW " + ais.get(g).wins + "/" + ais.get(g).losses + "/" + ais.get(g).ties);
					}try {
					fileWriter.write(((highRatio + lowRatio) / 2) + "\r\n");
					fileWriter.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
					ais = new ArrayList<NeuralNetwork>();
					for(int i = 0; i < aiCount - newAis.size(); i++) {
						 Random rand = new Random();
						 int randd = rand.nextInt(newAis.size());
						 NeuralNetwork n = new NeuralNetwork(newAis.get(randd));
						 n.wins = 0;
						// n.learn(1);
						ais.add(n);
					}
					ais.addAll(newAis);
				}
				
			
			Collections.shuffle(ais);
			for (int i = 0; i < aiCount; i++) {
				for(int j = 0; j < 50; j++) {
					initialize();
					NeuralNetwork aiX = ais.get(i);
					aiX.setChar('X');
					Random rand = new Random();
					 int randd = rand.nextInt(ais.size());
					NeuralNetwork aiO = ais.get(randd);
					aiO.setChar('O');
					while (controlTurnsAIVRandomX(aiX) == 0) {
					//	 s.next();
					}
					if (checkWinCondition(XTurn ? 'O' : 'X') == 1) {
						//System.out.print((XTurn ? 'O' : 'X') + " Wins!\n\n");
						if (!XTurn) {
							aiX.wins++;
							//aiX.learn(true);
						}
						else {
						//	aiX.learn(true);
							aiX.losses++;
						//	aiX.learn(0.001);
							//aiX.learn(false);
						}
					} else {
						aiX.ties++;
					//	aiX.wins += 0.5;
						//aiX.learn(true);
					}
					initialize();
					aiX.setChar('O');
					aiO.setChar('X');
					while (controlTurnsAIVRandomO(aiX) == 0) {
						// s.next();
					}
					if (checkWinCondition(XTurn ? 'O' : 'X') == 1) {
						//System.out.print((XTurn ? 'O' : 'X') + " Wins!\n\n");
						if (XTurn) {
							aiX.wins++;
							//aiX.learn(true);
						}
						else {
						//	aiX.learn(true);
							aiX.losses++;
							aiX.learn(1);
							//aiX.learn(false);
						}
					} else {
						aiX.ties++;
					//	aiX.wins += 0.5;
						//aiX.learn(true);
					}
				}
			}
			// System.out.println("press any key to go again");
			// s.next();
			XTurn = true;
		}
		Integer mostWins = Integer.MIN_VALUE;
		NeuralNetwork nw = null;
		for (NeuralNetwork n : ais) {
			if (n.wins > mostWins) {
				nw = n;
				mostWins = n.wins;
			}
		}
		try {
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true) {
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
	static int controlTurnsAIVRandomX(NeuralNetwork X) {
		if(XTurn) {
			Vector turn = X.getMove(board);
			placeVector(turn, XTurn ? 'X' : 'O');
			//draw();
			XTurn = !XTurn;
		} else {
			Vector v;
			do {
				Random r = new Random();
				v = new Vector(r.nextInt(3),r.nextInt(3));
			} while(board[v.x][v.y] != ' ');
				placeVector(v, XTurn ? 'X' : 'O');
			//	draw();
			XTurn = !XTurn;
		}
		return checkWinCondition(XTurn ? 'O' : 'X');
	}
	static int controlTurnsAIVRandomO(NeuralNetwork O) {
		if(!XTurn) {
			Vector turn = O.getMove(board);
			placeVector(turn, XTurn ? 'X' : 'O');
			XTurn = !XTurn;
			//draw();
		} else {
			Vector v;
			do {
				Random r = new Random();
				v = new Vector(r.nextInt(3),r.nextInt(3));
			} while(board[v.x][v.y] != ' ');
				placeVector(v, XTurn ? 'X' : 'O');
				//draw();
			XTurn = !XTurn;
		}
		return checkWinCondition(XTurn ? 'O' : 'X');
	}
	static int controlTurnsAI(NeuralNetwork X, NeuralNetwork O) {

		Vector turn = (XTurn ? X : O).getMove(board);
		placeVector(turn, XTurn ? 'X' : 'O');
		XTurn = !XTurn;
		//draw();
		//s.next();
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
