package fi.aalto.itia.util;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

public class MaxSizedArray {

    private CircularFifoQueue<Double> buffer;

    public MaxSizedArray(int size) {
	buffer = new CircularFifoQueue<Double>(size);
    }

    public void addElement(double el) {
	buffer.add(el);
    }

    public double getMean() {
	Mean m = new Mean();
	for (Double d : buffer) {
	    m.increment(d);
	}
	return m.getResult();
    }

}
