package AI.neuron;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class ValueNeuron extends Neuron {

	public HashMap<Neuron, Double> connected = new HashMap<Neuron, Double>();
	double threshold = 0;
	public ValueNeuron(ArrayList<Neuron> hiddens, double minWeight, double maxWeight) {
		Random r = new Random();
		for (Neuron n : hiddens) {

			double rWeight = minWeight + (maxWeight - minWeight) * r.nextDouble();
			connected.put(n, rWeight);
		}
	}

	@Override
	public double getValue() {
		double sum = 0;
		for(Entry<Neuron, Double> entry : connected.entrySet()) {
		    Neuron neuron = entry.getKey();
		    Double weight = entry.getValue();
		    sum += (neuron.getValue() * weight);
		}
		sum -= threshold;
		double output = 1 / (1 + Math.exp(-sum));
		return output;
	}
	public double thresholdLearnRate = 0.01;
	public double weightLearnRate = 0.01;
	public void learn(boolean win) {
		Random r = new Random();
		if(win) {
			threshold += -thresholdLearnRate + (thresholdLearnRate + thresholdLearnRate) * r.nextDouble();
			for(Map.Entry<Neuron, Double> entry : connected.entrySet()) {
				 Double weight = entry.getValue();
				 weight += -weightLearnRate + (weightLearnRate + weightLearnRate) * r.nextDouble();
			}
		}
		else {
			threshold += 5 * (-thresholdLearnRate + (thresholdLearnRate + thresholdLearnRate) * r.nextDouble());
			for(Map.Entry<Neuron, Double> entry : connected.entrySet()) {
				 Double weight = entry.getValue();
				 weight += 5 * (-weightLearnRate + (weightLearnRate + weightLearnRate) * r.nextDouble());
			}
		}
	}
}
