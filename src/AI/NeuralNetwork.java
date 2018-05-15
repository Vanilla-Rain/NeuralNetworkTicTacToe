package AI;

import java.util.ArrayList;

import AI.neuron.*;

public class NeuralNetwork {
	char myChar = 'X';
	public ArrayList<Neuron> inputs;
	public ArrayList<Neuron> hiddens1;
	 public ArrayList<Neuron> hiddens2;
	// public ArrayList<Neuron> hiddens3;
	public ArrayList<Neuron> outputs;
	public Integer wins = 0;
	public int ties = 0;
	public int losses = 0;

	public NeuralNetwork() {
		inputs = new ArrayList<Neuron>();
		hiddens1 = new ArrayList<Neuron>();
		 hiddens2 = new ArrayList<Neuron>();
		// hiddens3 = new ArrayList<Neuron>();
		outputs = new ArrayList<Neuron>();
		for (int i = 0; i < 27; i++) {
			inputs.add(new InputNeuron(-1, 1));
		}
		for (int i = 0; i < 18; i++) {
			hiddens1.add(new ValueNeuron(inputs, -1, 1, -1, 1));
		}
		 for(int i = 0; i < 13; i++) {
		 hiddens2.add(new ValueNeuron(hiddens1,-1,1,-1,1));
		 }
	//	 for(int i = 0; i < 9; i++) {
	//	 hiddens3.add(new ValueNeuron(hiddens2,-1,1,0,0));
	//	 }
		for (int i = 0; i < 9; i++) {
			outputs.add(new ValueNeuron(hiddens1,-1, 1, -1, 1));
		}
	}

	public NeuralNetwork(NeuralNetwork n) {
		this.inputs = n.inputs;
		this.hiddens1 = n.hiddens1;
		 this.hiddens2 = n.hiddens2;
		// this.hiddens3 = n.hiddens3;
		this.outputs = n.outputs;

	}

	public void setChar(char myChar) {
		this.myChar = myChar;
	}

	public Vector getMove(char[][] board) {
		/*double highest = Double.NEGATIVE_INFINITY;
		int highestX = -1;
		int highestY = -1;
		for(int x = 1; x <= 3; x++) {
			for(int y = 1; y <= 3; y++) {
				char[][] newBoard = new char[3][3];// = Arrays.copyOf(board, board.length);
				for(int t = 0; t <= 2; t++) {
					for(int u = 0; u <=2; u++) {
						newBoard[t][u] = board[t][u];
					}
				}
				if(newBoard[y-1][x-1] == ' ') {
					newBoard[y-1][x-1] = myChar;
					for(int z = 1; z <= 3; z++) {
						for(int q = 1; q <= 3; q++) {
								((InputNeuron)inputs.get(z * q - 1)).setInput(newBoard[q-1][z-1] == myChar ? 1 : 0);
								((InputNeuron)inputs.get(z * q - 1 + 9)).setInput((newBoard[q-1][z-1] != myChar && newBoard[q-1][z-1] != ' ') ? 1 : 0);
								((InputNeuron)inputs.get(z * q - 1 + 18)).setInput(newBoard[q-1][z-1] == ' ' ? 1 : 0);
						}
					}
					double value = ((ValueNeuron)outputs.get(0)).getValue();
					if(value > highest) {
						highest = value;
						highestX = x-1;
						highestY = y-1;
					}
				}
			}
		}
		return new Vector(highestY, highestX);*/
					for(int z = 1; z <= 3; z++) {
						for(int q = 1; q <= 3; q++) {
								((InputNeuron)inputs.get(z * q - 1)).setInput(board[q-1][z-1] == myChar ? 1 : 0);
								((InputNeuron)inputs.get(z * q - 1 + 9)).setInput((board[q-1][z-1] != myChar && board[q-1][z-1] != ' ') ? 1 : 0);
								((InputNeuron)inputs.get(z * q - 1 + 18)).setInput(board[q-1][z-1] == ' ' ? 1 : 0);
						}
					
				}
					double highest = Double.NEGATIVE_INFINITY;
					int highestNum = 0;
					int wanted = 0;
					double wantedAmount = 0;
					for(int i = 0; i < 9; i++) {
						double value = ((ValueNeuron)outputs.get(i)).getValue();
					//	System.out.println("VALUE " + i + ": " + value);
						if(value > highest && isPossible(i, board)) {
							highest = value;
							highestNum = i;
						}
						if(value > wantedAmount) {
							wantedAmount = value;
							wanted = i;
						}
					}
					//System.out.println("Chose " + highestNum + " with value " + highest + ", wanted " + wanted + " with " + wantedAmount + ".");
					switch(highestNum) {
						case 0:
							return new Vector(0,0);
						case 1:
							return new Vector(0,1);
						case 2:
							return new Vector(0,2);
						case 3:
							return new Vector(1,0);
						case 4:
							return new Vector(1,1);
						case 5:
							return new Vector(1,2);
						case 6:
							return new Vector(2,0);
						case 7:
							return new Vector(2,1);
						case 8:
							return new Vector(2,2);
					}
					return null;
		
	}

	public boolean isPossible(int i, char[][] board) {
		switch (i) {
		case 0:
			return (board[0][0] == ' ');
		case 1:
			return (board[0][1] == ' ');
		case 2:
			return (board[0][2] == ' ');
		case 3:
			return (board[1][0] == ' ');
		case 4:
			return (board[1][1] == ' ');
		case 5:
			return (board[1][2] == ' ');
		case 6:
			return (board[2][0] == ' ');
		case 7:
			return (board[2][1] == ' ');
		case 8:
			return (board[2][2] == ' ');
		}
		return false;
	}

	public void learn(double amount) {
		for (int i = 0; i < 27; i++) {
			inputs.get(i).learn(amount);
		}
		for (int i = 0; i < 18; i++) {
			hiddens1.get(i).learn(amount);
		}
		 for(int i = 0; i < 13; i++) {
		 hiddens2.get(i).learn(amount);
		 }
		// for(int i = 0; i < 9; i++) {
		// hiddens3.get(i).learn(amount);
		// }
		for (int i = 0; i < 9; i++) {
			outputs.get(i).learn(amount);
		}
	}
}
