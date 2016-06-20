package fi.aalto.itia.adr_em_common;

import java.io.Serializable;

public class StatsToAggUpdateContent implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6508650300853829136L;
    private Double currentNominalAggregatedConsumption;

    /**
     * @param currentNominalAggregatedConsumption
     */
    public StatsToAggUpdateContent(Double currentNominalAggregatedConsumption) {
	super();
	this.currentNominalAggregatedConsumption = currentNominalAggregatedConsumption;
    }

    public Double getCurrentNominalAggregatedConsumption() {
        return currentNominalAggregatedConsumption;
    }

    public void setCurrentNominalAggregatedConsumption(Double currentNominalAggregatedConsumption) {
        this.currentNominalAggregatedConsumption = currentNominalAggregatedConsumption;
    }

}
