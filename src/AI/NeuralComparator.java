package AI;

import java.util.Comparator;

public class NeuralComparator implements Comparator<NeuralNetwork> {

	@Override
	public int compare(NeuralNetwork o1, NeuralNetwork o2) {
		if(o1.wins - o1.losses > o2.wins -o2.losses) {
			return 1;
		}
		else
			return -1;
	}

}
