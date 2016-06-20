package fi.aalto.itia.adr_em_common;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;

public class UpdateMessageContent implements Serializable, Comparable<UpdateMessageContent> {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8089216898281578784L;
    private static final int THRESHOLD_MIN = 5;

    private Double currentConsumption;// in W
    private Double possibleCut;// in W
    // time in which the consumer can react to a freq control event by giving
    // some flexibility
    private Double reactionTimeCut;// in sec
    // time in which the consumer can participate by giving some flexibility
    private Double timeCut;
    private Double possibleIncrease;// in W
    // time in which the consumer can react to a freq control event by giving
    // some flexibility
    private Double reactionTimeIncrease;
    // time in which the consumer can participate by giving some flexibility
    private Double timeIncrease;// in sed
    private final Timestamp timeOfMessage;
    private final String consumerSender;

    private AgingADRConsumer aging;

    // Empty
    public UpdateMessageContent(String sender) {
	super();
	this.currentConsumption = 0d;
	this.possibleCut = 0d;
	this.timeCut = 0d;
	this.possibleIncrease = 0d;
	this.timeIncrease = 0d;
	this.consumerSender = sender;
	// NOW
	this.timeOfMessage = new Timestamp(System.currentTimeMillis());
    }

    /**
     * @param currentConsumption
     * @param possibleCut
     * @param reactionTimeCut
     * @param timeCut
     * @param possibleIncrease
     * @param reactionTimeIncrease
     * @param timeIncrease
     * @param consumerSender
     */
    public UpdateMessageContent(Double currentConsumption, Double possibleCut,
	    Double reactionTimeCut, Double timeCut, Double possibleIncrease,
	    Double reactionTimeIncrease, Double timeIncrease, String consumerSender,
	    AgingADRConsumer aging) {
	super();
	this.currentConsumption = currentConsumption;
	this.possibleCut = possibleCut;
	this.reactionTimeCut = reactionTimeCut;
	this.timeCut = timeCut;
	this.possibleIncrease = possibleIncrease;
	this.reactionTimeIncrease = reactionTimeIncrease;
	this.timeIncrease = timeIncrease;
	this.consumerSender = consumerSender;
	this.aging = aging;
	// NOW
	this.timeOfMessage = new Timestamp(System.currentTimeMillis());
    }

    public Double getCurrentConsumption() {
	return currentConsumption;
    }

    public void setCurrentConsumption(Double currentConsumption) {
	this.currentConsumption = currentConsumption;
    }

    public Double getPossibleCut() {
	return possibleCut;
    }

    public void setPossibleCut(Double possibleCut) {
	this.possibleCut = possibleCut;
    }

    public Double getTimeCut() {
	return timeCut;
    }

    public void setTimeCut(Double timeCut) {
	this.timeCut = timeCut;
    }

    public Double getPossibleIncrease() {
	return possibleIncrease;
    }

    public void setPossibleIncrease(Double possibleIncrease) {
	this.possibleIncrease = possibleIncrease;
    }

    public Double getTimeIncrease() {
	return timeIncrease;
    }

    public void setTimeIncrease(Double timeIncrease) {
	this.timeIncrease = timeIncrease;
    }

    public Timestamp getTimeOfMessage() {
	return timeOfMessage;
    }

    public String getConsumerSender() {
	return consumerSender;
    }

    public int compareTo(UpdateMessageContent o) {
	// TODO Auto-generated method stub
	return 0;
    }

    public Double getReactionTimeCut() {
	return reactionTimeCut;
    }

    public void setReactionTimeCut(Double reactionTimeCut) {
	this.reactionTimeCut = reactionTimeCut;
    }

    public Double getReactionTimeIncrease() {
	return reactionTimeIncrease;
    }

    public void setReactionTimeIncrease(Double reactionTimeIncrease) {
	this.reactionTimeIncrease = reactionTimeIncrease;
    }

    public AgingADRConsumer getAging() {
	return aging;
    }

    public void setAging(AgingADRConsumer aging) {
	this.aging = aging;
    }

    /** Comparators **/
    public static Comparator<UpdateMessageContent> AscSortByTimeCutComparator = new Comparator<UpdateMessageContent>() {
	// if same time cut => goes to reaction time, goes to power cut
	public int compare(UpdateMessageContent o1, UpdateMessageContent o2) {
	    if (o1.getTimeCut() > o2.getTimeCut())
		return 1;
	    if (o1.getTimeCut() < o2.getTimeCut())
		return -1;
	    if (o1.getReactionTimeCut() > o2.getReactionTimeCut())
		return 1;
	    if (o1.getReactionTimeCut() < o2.getReactionTimeCut())
		return -1;
	    if (o1.getPossibleCut() > o2.getPossibleCut())
		return 1;
	    if (o1.getPossibleCut() < o2.getPossibleCut())
		return -1;
	    return 0;
	}
    };

    public static Comparator<UpdateMessageContent> DescSortByTimeCutComparator = new Comparator<UpdateMessageContent>() {
	// if same time cut =>goes to reaction time => goes to power cut
	public int compare(UpdateMessageContent o1, UpdateMessageContent o2) {
	    return 0 - AscSortByTimeCutComparator.compare(o1, o2);
	}
    };

    public static Comparator<UpdateMessageContent> DescSortByTimeIncreaseComparator = new Comparator<UpdateMessageContent>() {
	// order time increase first and then possible increase if the time is
	// the same
	public int compare(UpdateMessageContent o1, UpdateMessageContent o2) {
	    if (o1.getTimeIncrease() > o2.getTimeIncrease())
		return -1;
	    if (o1.getTimeIncrease() < o2.getTimeIncrease())
		return 1;
	    if (o1.getReactionTimeIncrease() > o2.getReactionTimeIncrease())
		return -1;
	    if (o1.getReactionTimeIncrease() < o2.getReactionTimeIncrease())
		return 1;
	    if (o1.getPossibleIncrease() > o2.getPossibleIncrease())
		return -1;
	    if (o1.getPossibleIncrease() < o2.getPossibleIncrease())
		return 1;
	    return 0;
	}
    };

    public static Comparator<UpdateMessageContent> AscSortByTimeIncreaseComparator = new Comparator<UpdateMessageContent>() {
	// order time increase first and then possible increase if the time is
	// the same
	public int compare(UpdateMessageContent o1, UpdateMessageContent o2) {
	    return 0 - DescSortByTimeIncreaseComparator.compare(o1, o2);
	}
    };

    public static Comparator<UpdateMessageContent> SortByConsumerSenderComparator = new Comparator<UpdateMessageContent>() {
	// compare only by consumer's name
	public int compare(UpdateMessageContent o1, UpdateMessageContent o2) {
	    return o1.getConsumerSender().compareTo(o2.getConsumerSender());
	}
    };

    public static Comparator<UpdateMessageContent> DescSortByAgingTotTimeCutComparator = new Comparator<UpdateMessageContent>() {
	// order time increase first and then possible increase if the time is
	// the same
	public int compare(UpdateMessageContent o1, UpdateMessageContent o2) {
	    int result = AgingADRConsumer.DescSortByTotal.compare(o1.getAging(), o2.getAging());
	    if (result == 0) {
		result = AscSortByTimeCutComparator.compare(o1, o2);
	    }
	    return result;
	}
    };

    public static Comparator<UpdateMessageContent> AscSortByAgingTotTimeCutComparator = new Comparator<UpdateMessageContent>() {
	// order time increase first and then possible increase if the time is
	// the same
	public int compare(UpdateMessageContent o1, UpdateMessageContent o2) {
	    return -DescSortByAgingTotTimeCutComparator.compare(o1, o2);
	}
    };

    public static Comparator<UpdateMessageContent> DescSortByAgingTotTimeIncreaseComparator = new Comparator<UpdateMessageContent>() {
	// order time increase first and then possible increase if the time is
	// the same
	public int compare(UpdateMessageContent o1, UpdateMessageContent o2) {
	    int result = AgingADRConsumer.DescSortByTotal.compare(o1.getAging(), o2.getAging());
	    if (result == 0) {
		result = AscSortByTimeIncreaseComparator.compare(o1, o2);
	    }
	    return result;
	}
    };

    public static Comparator<UpdateMessageContent> AscSortByAgingTotTimeIncreaseComparator = new Comparator<UpdateMessageContent>() {
	// order time increase first and then possible increase if the time is
	// the same
	public int compare(UpdateMessageContent o1, UpdateMessageContent o2) {
	    return -DescSortByAgingTotTimeIncreaseComparator.compare(o1, o2);
	}
    };

    public static Comparator<UpdateMessageContent> SortByAgingTotTimeIncreaseV2Comparator = new Comparator<UpdateMessageContent>() {
	// order time increase first and then possible increase if the time is
	// the same
	public int compare(UpdateMessageContent o1, UpdateMessageContent o2) {
	    // TODO put a threshold instead of 0
	    double timeThreshold = 0d;
	    if (o1.getAging().getTotal() > o2.getAging().getTotal()) {
		if (o2.getPossibleIncrease() > timeThreshold) {
		    return -1;
		} else {
		    return 1;
		}
	    } else if (o1.getAging().getTotal() < o2.getAging().getTotal()) {
		if (o1.getPossibleIncrease() > timeThreshold) {
		    return 1;
		} else {
		    return -1;
		}
	    } else// (o1.getAging().getTotal() == o2.getAging().getTotal())
	    {
		return AscSortByTimeIncreaseComparator.compare(o1, o2);
	    }
	}
    };

    public static Comparator<UpdateMessageContent> OrderForDwElabComparator = new Comparator<UpdateMessageContent>() {
	// order time increase first and then possible increase if the time is
	// the same
	public int compare(UpdateMessageContent o1, UpdateMessageContent o2) {
	    double threshold = THRESHOLD_MIN * ADR_EM_Common.ONE_MIN;// 5 min

	    double minO1 = Math.min(o1.getReactionTimeCut(), o1.getTimeCut());
	    double minO2 = Math.min(o2.getReactionTimeCut(), o2.getTimeCut());
	    // if aging is smaller o1 is bigger
	    if (minO1 < threshold || minO2 < threshold) {
		// compare only the times
		return -Double.compare(minO1, minO2);
	    }
	    // if both values are bigger than the threshold
	    if (o1.getAging().getReactDw() < o2.getAging().getReactDw()) {
		// the smaller aging the bigger is
		return -1;
	    }
	    // aging comparison
	    else if (o1.getAging().getReactDw() > o2.getAging().getReactDw()) {
		return 1;
	    }
	    // same aging
	    else {
		// compare the times
		return -Double.compare(minO1, minO2);
	    }

	}
    };

    public static Comparator<UpdateMessageContent> OrderForUpElabComparator = new Comparator<UpdateMessageContent>() {
	// order time increase first and then possible increase if the time is
	// the same
	public int compare(UpdateMessageContent o1, UpdateMessageContent o2) {
	    double threshold = THRESHOLD_MIN * ADR_EM_Common.ONE_MIN;// 5 min

	    double minO1 = Math.min(o1.getReactionTimeIncrease(), o1.getTimeIncrease());
	    double minO2 = Math.min(o2.getReactionTimeIncrease(), o2.getTimeIncrease());
	    // if aging is smaller o1 is bigger
	    if (minO1 < threshold || minO2 < threshold) {
		// compare only the times
		return -Double.compare(minO1, minO2);
	    }
	    // if both values are bigger than the threshold
	    if (o1.getAging().getReactUp() < o2.getAging().getReactUp()) {
		// the smaller aging the bigger is
		return -1;
	    }
	    // aging comparison
	    else if (o1.getAging().getReactUp() > o2.getAging().getReactUp()) {
		return 1;
	    }
	    // same aging
	    else {
		// compare the times
		return -Double.compare(minO1, minO2);
	    }

	}
    };

    @Override
    public String toString() {
	return "UpdateMessageContent [currentConsumption=" + currentConsumption + ", possibleCut="
		+ possibleCut + ", timeCut=" + timeCut + ", possibleIncrease=" + possibleIncrease
		+ ", timeIncrease=" + timeIncrease + ", timeOfMessage=" + timeOfMessage
		+ ", consumerSender=" + consumerSender + "]";
    }
}
