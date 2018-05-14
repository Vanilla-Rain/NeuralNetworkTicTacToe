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

	public double weightLearnRate = 0.1;
	public void learn(boolean win) {
			
		Random r = new Random();
		if(win) {
			if(Math.random() < 0.5) {
				 inputWeight += -weightLearnRate + (weightLearnRate + weightLearnRate) * r.nextDouble();
			}
		}
		else {
			if(Math.random() < 0.5) {
				 inputWeight += 5 * (-weightLearnRate + (weightLearnRate + weightLearnRate) * r.nextDouble());
			}
		}
	}
}
