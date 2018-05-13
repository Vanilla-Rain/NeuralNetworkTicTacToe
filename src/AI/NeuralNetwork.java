package AI;

import java.util.ArrayList;

import AI.neuron.*;

public class NeuralNetwork {
	char myChar = 'X';
	public ArrayList<Neuron> inputs;
	public ArrayList<Neuron> hiddens; // 1 layer for now
	public ArrayList<Neuron> outputs;
	public int wins = 0;
	public NeuralNetwork() {
		inputs = new ArrayList<Neuron>();
		hiddens = new ArrayList<Neuron>();
		outputs = new ArrayList<Neuron>();
		for(int i = 0; i < 9; i++) {
			inputs.add(new InputNeuron(-1, 1));
		}
		for(int i = 0; i < 9; i++) {
			hiddens.add(new ValueNeuron(inputs,-1,1));
		}
		for(int i = 0; i < 9; i++) {
			outputs.add(new ValueNeuron(hiddens,-1,1));
		}
	}
	public NeuralNetwork(NeuralNetwork n) {
		this.inputs = n.inputs;
		this.hiddens = n.hiddens;
		this.outputs = n.outputs;
		learn(false);
	}
	public void setChar(char myChar) {
		this.myChar = myChar;
	}
	public Vector getMove(char[][] board) {
		for(int x = 1; x <= 3; x++) {
			for(int y = 1; y <= 3; y++) {
				((InputNeuron)inputs.get(x * y - 1)).setInput(board[y-1][x-1] == myChar ? 1 : (board[y-1][x-1] == ' ' ? 0 : -1));
			}
		}
		double highest = Double.NEGATIVE_INFINITY;
		int highestNum = -1;
		for(int i = 0; i < 9; i++) {
			double value = ((ValueNeuron)outputs.get(i)).getValue();
			//System.out.println(value);
			switch(i) {
			case 0:
				if(board[0][0] != ' ') {
					continue;
				}
				break;
			case 1:
				if(board[0][1] != ' ') {
					continue;
				}
				break;
			case 2:
				if(board[0][2] != ' ') {
					continue;
				}
				break;
			case 3:
				if(board[1][0] != ' ') {
					continue;
				}
				break;
			case 4:
				if(board[1][1] != ' ') {
					continue;
				}
				break;
			case 5:
				if(board[1][2] != ' ') {
					continue;
				}
				break;
			case 6:
				if(board[2][0] != ' ') {
					continue;
				}
				break;
			case 7:
				if(board[2][1] != ' ') {
					continue;
				}
				break;
			case 8:
				if(board[2][2] != ' ') {
					continue;
				}
				break;
		}
			if(value > highest) {
				highest = value;
				highestNum = i;
			}
		}
		if(highestNum == -1) {
			System.err.println("MACHINE BROKE");
		}
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
	public void learn(boolean win) {
		for(int i = 0; i < 9; i++) {
			inputs.get(i).learn(win);
		}
		for(int i = 0; i < 9; i++) {
			hiddens.get(i).learn(win);
		}
		for(int i = 0; i < 9; i++) {
			outputs.get(i).learn(win);
		}
	}
}
