package fi.aalto.itia.util;

import org.junit.Test;

public class MaxSizedArrayTest {

    @Test
    public void testMaxSizedArray() {
	MaxSizedArray msa = new MaxSizedArray(10);
	for (int i = 1; i < 5; i++) {
	    msa.addElement(i);
	}
	System.out.println(msa.getMean());
	for (int i = 1; i < 5; i++) {
	    msa.addElement(i + 5d);
	}
	System.out.println(msa.getMean());
    }

}
