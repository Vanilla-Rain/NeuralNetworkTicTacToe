package AI.neuron;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class ValueNeuron extends Neuron {

	public HashMap<Neuron, Double> connected = new HashMap<Neuron, Double>();
	double threshold = 0;
	public ValueNeuron(ArrayList<Neuron> hiddens, double minWeight, double maxWeight, double minThreshold, double maxThreshold) {
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
	public double thresholdLearnRate = 0.1;
	public double weightLearnRate = 0.1;
	public void learn(double amount) {
		Random r = new Random();
			if(Math.random() < 0.1) {
			threshold += amount * (-thresholdLearnRate + (thresholdLearnRate + thresholdLearnRate) * r.nextDouble());
			}
			for(Map.Entry<Neuron, Double> entry : connected.entrySet()) {
				if(Math.random() < 0.1) {
				 Double weight = entry.getValue();
				 weight += amount * (-weightLearnRate + (weightLearnRate + weightLearnRate) * r.nextDouble());
				 connected.put(entry.getKey(), weight);
				}
			}
	}
}
