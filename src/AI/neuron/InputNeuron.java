package AI.neuron;

import java.util.Random;

public class InputNeuron extends Neuron {

	public double inputWeight;
	public InputNeuron(double minWeight, double maxWeight) {
		Random r = new Random();
		inputWeight = minWeight + (maxWeight - minWeight) * r.nextDouble();
	}
	double value;
	public void setInput(double input) {
		value = input * inputWeight;
	}
	@Override
	public double getValue() {
		return value;
	}

	public double weightLearnRate = 0.01;
	public void learn(boolean win) {
		Random r = new Random();
		if(win) {
				 inputWeight += -weightLearnRate + (weightLearnRate + weightLearnRate) * r.nextDouble();
		}
		else {
				 inputWeight += 5 * (-weightLearnRate + (weightLearnRate + weightLearnRate) * r.nextDouble());
		}
	}
}
