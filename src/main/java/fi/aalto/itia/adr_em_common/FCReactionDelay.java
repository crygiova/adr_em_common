package fi.aalto.itia.adr_em_common;

import org.apache.commons.math3.distribution.BetaDistribution;

public class FCReactionDelay {

    private static final int CONST_DELAY = ADR_EM_Common.ONE_MIN_IN_SEC;
    private static final int VARIABLE_DELAY = 2 * ADR_EM_Common.ONE_MIN_IN_SEC;

    // http://commons.apache.org/proper/commons-math/userguide/distribution.html
    private BetaDistribution betaD = new BetaDistribution(3, 7);

    private int counterRestore = 0;
    private int counterReact = 0;
    private int restoreDelay;
    // XXX important since is the react delay
    private int reactDelay;

    public FCReactionDelay() {
	this.restoreDelay = (int) (CONST_DELAY + Math.round(VARIABLE_DELAY * betaD.sample()));
	this.reactDelay = (int) (CONST_DELAY + Math.round(VARIABLE_DELAY * betaD.sample()));
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
