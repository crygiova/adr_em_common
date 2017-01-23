package fi.aalto.itia.adr_em_common;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;

public class FCReactionDelay {

	private static final double CONST_DELAY = 2;
	private static final double VARIABLE_DELAY = 0.5;

	// http://commons.apache.org/proper/commons-math/userguide/distribution.html
	private BetaDistribution betaD = new BetaDistribution(3, 7);
	private UniformRealDistribution uniformDistrib = new UniformRealDistribution(CONST_DELAY - VARIABLE_DELAY,
			CONST_DELAY + VARIABLE_DELAY);

	private int counterRestore = 0;
	private int counterReact = 0;
	private int restoreDelay;
	// XXX important since is the react delay
	private int reactDelay;

	public FCReactionDelay() {
		this.restoreDelay = (int) uniformDistrib.sample() * ADR_EM_Common.ONE_MIN_IN_SEC;
		this.reactDelay = (int) uniformDistrib.sample() * ADR_EM_Common.ONE_MIN_IN_SEC;
	}

	public int getCounterRestore() {
		return counterRestore;
	}

	public void addCounterRestore() {
		counterRestore++;
	}

	public void initCounterRestore() {
		counterRestore = 0;
	}

	// Reaction delay methods
	public int getReactDelay() {
		return reactDelay;
	}

	public void setReactDelay(int reactDelay) {
		this.reactDelay = reactDelay;
	}

	public int getCounterReact() {
		return counterReact;
	}

	public void addCounterReact() {
		counterReact++;
	}

	public void initCounterReact() {
		counterReact = 0;
	}

	public int getRestoreDelay() {
		return restoreDelay;
	}

	public void setRestoreDelay(int restoreDelay) {
		this.restoreDelay = restoreDelay;
	}

}
