package AI;

import java.util.Comparator;

public class NeuralComparator implements Comparator<NeuralNetwork> {

	@Override
	public int compare(NeuralNetwork o1, NeuralNetwork o2) {
		return o1.wins.compareTo(o2.wins);
	}

}
