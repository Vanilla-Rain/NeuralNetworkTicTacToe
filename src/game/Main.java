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
import java.util.concurrent.ThreadLocalRandom;

import AI.NeuralComparator;
import AI.NeuralNetwork;
import AI.Player;
import AI.Vector;

public class Main {
	static int aiCount = 100;
	static ArrayList<NeuralNetwork> ais = new ArrayList<NeuralNetwork>();

	public static void main(String[] args) {
		s = new Scanner(System.in);
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
	
		for (int x = 0; x < 500; x++) {
				System.out.println(x);
				if(x != 0) {
					ArrayList<NeuralNetwork> newAis = new ArrayList<NeuralNetwork>();
					Collections.sort(ais,new NeuralComparator());
					int highRatio = 0;
					for (int g = ais.size()-1; g > ais.size() - 5; g--) {
						if(g == ais.size() - 1) {
							//System.out.println(x + " " + ais.get(g).wins);
							System.out.println("HIGH " + ais.get(g).wins + "/" + ais.get(g).losses + "/" + ais.get(g).ties);
								if(x != 0) {
									highRatio = ais.get(g).wins - ais.get(g).losses;
									
								}
								
							
								
						}
						//ais.get(g).learn(ais.get(g).wins - ais.get(g).losses);
						if(ais.get(g).wins == 0) {
							 ais.get(g).learn(20);
						}
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
						 n.learn(0.1);
						ais.add(n);
					}
					ais.addAll(newAis);
				}
				
			
			Collections.shuffle(ais);
			for (int i = 0; i < aiCount; i++) {
				for(int j = 0; j < 25; j++) {
					NeuralNetwork ai = ais.get(i);
					int q = controlTurnsAIVRandom(ai);
					//System.out.println(q + " wins!");
					//s.next();
					if(q == 1) {
						ai.wins++;
					}
					else if(q == 2) {
						ai.losses++;
					}
				/*	NeuralNetwork ai1 = ais.get(i);
					 Random rand = new Random();
					 int randd = rand.nextInt(ais.size());
					NeuralNetwork ai2 = ais.get(randd);
					int q = controlTurnsAI(ai1,ai2);
					if(q == 1) {
						ai1.wins++;
					//	ai2.losses++;
					}
					else if(q == 2) {
					//	ai2.wins++;
						ai1.losses++;
					}
					else {
						ai1.ties++;
					//	ai2.losses++;
					}*/
				}
			}
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
			int i = controlTurnsPVAi(nw);
			System.out.println(i + " wins, play again?\n");
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
		Player p1 = new Player();
		Player p2 = new Player();
		while(true) {
			int one = takeTurn(1,p1);
			int two = takeTurn(2,p2);
			System.out.println("Player 1 plays " + one + ", Player 2 plays " + two);
			if(one == 1 && two == 2) {
				return 1;
			}
			else if(two == 1 && one == 2) {
				return 2;
			}
			if(one == 2) {
				p1.ammo++;
			}
			if(two == 2) {
				p2.ammo++;
			}
		}
	}
	static int controlTurnsAI(NeuralNetwork ai1, NeuralNetwork ai2) {
		Player p1 = new Player();
		Player p2 = new Player();
		int turns = 0;
		while(true) {
			int one = ai1.getMove(p1,p2);
			int two = ai2.getMove(p2, p1);
			//System.out.println("Player 1 plays " + one + ", Player 2 plays " + two);
			if(one == 1 && two == 2) {
				return 1;
			}
			else if(two == 1 && one == 2) {
				return 2;
			}
			if(one == 2) {
				p1.ammo++;
			}
			if(two == 2) {
				p2.ammo++;
			}
			if(one == 1) {
				p1.ammo--;
			}
			if(two == 1) {
				p2.ammo--;
			}
			turns++;
			if(turns > 20) {
				return 3;
			}
		}
	}
	static int controlTurnsAIVRandom(NeuralNetwork ai) {
		Player p1 = new Player();
		Player p2 = new Player();
		int turns = 0;
		while(true) {
			int one = ai.getMove(p1,p2);
			int two = takeTurnRandom(2,p2);
			//System.out.println("Player 1 plays " + one + ", Player 2 plays " + two);
			if(one == 1 && two == 2) {
				return 1;
			}
			else if(two == 1 && one == 2) {
				return 2;
			}
			if(one == 2) {
				p1.ammo++;
			}
			if(two == 2) {
				p2.ammo++;
			}
			if(one == 1) {
				p1.ammo--;
			}
			if(two == 1) {
				p2.ammo--;
			}
			turns++;
			if(turns > 20) {
				return 2;
			}
		}
	}

	static int controlTurnsPVAi(NeuralNetwork n) {
		Player p1 = new Player();
		Player p2 = new Player();
		while(true) {
			int one = takeTurn(1,p1);
			int two = n.getMove(p2, p1);
			System.out.println("Player 1 plays " + one + ", Player 2 plays " + two);
			if(one == 1 && two == 2) {
				return 1;
			}
			else if(two == 1 && one == 2) {
				return 2;
			}
			if(one == 2) {
				p1.ammo++;
			}
			if(two == 2) {
				p2.ammo++;
			}
			if(one == 1) {
				p1.ammo--;
			}
			if(two == 1) {
				System.out.println("YOU LOST AMMO");
				p2.ammo--;
			}
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

	static int takeTurn(int player,Player playerP) {
		int x;
		do {
			System.out.print("Player " + player + ": Its ur turn (1 shoot, 2 reload, 3 block)");
			x = s.nextInt();
		} while ((x == 1 && playerP.ammo == 0) || x < 1 || x > 3);
		return x;
	}
	static int takeTurnRandom(int player, Player playerP) {
		int x;
		do {
		//	System.out.print("Player " + player + ": Its ur turn (1 shoot, 2 reload, 3 block)");
			x = ThreadLocalRandom.current().nextInt(1, 3 + 1);
		} while ((x == 1 && playerP.ammo == 0) || x < 1 || x > 3);
		return x;
	}
}
