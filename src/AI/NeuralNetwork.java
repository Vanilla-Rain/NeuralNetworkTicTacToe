package AI;

import java.util.ArrayList;

import AI.neuron.*;

public class NeuralNetwork {
	char myChar;
	ArrayList<Neuron> inputs;
	ArrayList<Neuron> hiddens; // 1 layer for now
	ArrayList<Neuron> outputs;
	
	public NeuralNetwork(char myChar) {
		this.myChar = myChar;
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
	public Vector getMove(int[][] board) {
		for(int x = 1; x <= 3; x++) {
			for(int y = 1; y <= 3; y++) {
				((InputNeuron)inputs.get(x * y - 1)).setInput(board[y-1][x-1] == myChar ? 1 : (board[y-1][x-1] == ' ' ? 0 : -1));
			}
		}
		double highest = Double.NEGATIVE_INFINITY;
		int highestNum = 0;
		for(int i = 0; i < 9; i++) {
			double value = ((ValueNeuron)outputs.get(i)).getValue();
			if(value > highest) {
				highest = value;
				highestNum = i;
			}
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
