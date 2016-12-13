package fi.aalto.itia.util;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

public class MaxSizedArray {

    private CircularFifoQueue<Double> buffer;
    private double last = 0d;

    public MaxSizedArray(int size) {
	buffer = new CircularFifoQueue<Double>(size);
    }

    public void addElement(double el) {
	last = el;
	buffer.add(last);
    }

    public double getMean() {
	Mean m = new Mean();
	for (Double d : buffer) {
	    m.increment(d);
	}
	return m.getResult();
    }

    public double getLast() {
	return last;
    }

}
