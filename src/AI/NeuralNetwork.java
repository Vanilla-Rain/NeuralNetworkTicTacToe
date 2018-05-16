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
		for (int i = 0; i < 2; i++) {
			inputs.add(new InputNeuron(-0.25, 0.25));
		}
		for (int i = 0; i < 7; i++) {
			hiddens1.add(new ValueNeuron(inputs, -0.25, 0.25, 0, 0));
		}
		 for(int i = 0; i < 4; i++) {
		 hiddens2.add(new ValueNeuron(hiddens1,-1,1,-1,1));
		 }
	//	 for(int i = 0; i < 9; i++) {
	//	 hiddens3.add(new ValueNeuron(hiddens2,-1,1,0,0));
	//	 }
		for (int i = 0; i < 3; i++) {
			outputs.add(new ValueNeuron(hiddens1,-0.25, 0.25, 0, 0));
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

	public int getMove(Player p1, Player p2) {
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
		}*/
					((InputNeuron)inputs.get(0)).setInput(p1.ammo);
					((InputNeuron)inputs.get(1)).setInput(p2.ammo);
					double highest = Double.NEGATIVE_INFINITY;
					int highestNum = -1;
					int wanted = 0;
					double wantedAmount = 0;
					for(int i = 0; i < 3; i++) {
						double value = ((ValueNeuron)outputs.get(i)).getValue();
						//System.out.println("VALUE " + i + ": " + value);
						if(value > highest && isPossible(i, p1)) {
							highest = value;
							highestNum = i;
						}
						if(value > wantedAmount) {
							wantedAmount = value;
							wanted = i;
						}
					}
				//	System.out.println("Chose " + highestNum + " with value " + highest + ", wanted " + wanted + " with " + wantedAmount + ".");
					if(highestNum == -1) {
						System.out.println("broke");
					}
					return highestNum + 1;
		
	}

	public boolean isPossible(int i, Player p) {
	
			
		if(i == 1 || i == 2) {
			return true;
		}else if(i == 0 && p.ammo > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void learn(double amount) {
		for (int i = 0; i < 2; i++) {
			inputs.get(i).learn(amount);
		}
		for (int i = 0; i < 7; i++) {
			hiddens1.get(i).learn(amount);
		}
		 for(int i = 0; i < 4; i++) {
		 hiddens2.get(i).learn(amount);
		 }
		// for(int i = 0; i < 9; i++) {
		// hiddens3.get(i).learn(amount);
		// }
		for (int i = 0; i < 3; i++) {
			outputs.get(i).learn(amount);
		}
	}
}
