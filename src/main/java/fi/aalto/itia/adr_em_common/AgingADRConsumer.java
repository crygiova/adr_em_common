package fi.aalto.itia.adr_em_common;

import java.io.Serializable;
import java.util.Comparator;

import com.google.gson.annotations.Expose;

public class AgingADRConsumer implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4659693903254801900L;
    private final String consumerName;
    // consumerID
    @Expose
    private final int ID;
    @Expose
    private long total = 0;
    @Expose
    private long reactUp = 0;
    @Expose
    private long reactDw = 0;

    /**
     * @param consumerName
     * @param consumerID
     */
    public AgingADRConsumer(String consumerName, int consumerID) {
	super();
	this.consumerName = consumerName;
	this.ID = consumerID;
    }

    /**
     * @param total
     * @param reactUp
     * @param reactDw
     */
    // TODO XXX for test putposes
    public AgingADRConsumer(long reactUp, long reactDw) {
	this("Cons", 0);
	this.total = reactUp + reactDw;
	this.reactUp = reactUp;
	this.reactDw = reactDw;

    }

    public long getReactUp() {
	return reactUp;
    }

    public long getReactDw() {
	return reactDw;
    }

    public void addReactUp() {
	this.reactUp++;
	this.total++;
    }

    public void addReactDw() {
	this.reactDw++;
	this.total++;
    }

    public long getTotal() {
	return total;
    }

    public int getConsumerID() {
	return ID;
    }

    public String getConsumerName() {
	return consumerName;
    }

    public static Comparator<AgingADRConsumer> DescSortByTotal = new Comparator<AgingADRConsumer>() {

	public int compare(AgingADRConsumer o1, AgingADRConsumer o2) {
	    if (o1.getTotal() > o2.getTotal())
		return -1;
	    if (o1.getTotal() < o2.getTotal())
		return 1;
	    return 0;
	}
    };

    public static Comparator<AgingADRConsumer> AscSortByTotal = new Comparator<AgingADRConsumer>() {

	public int compare(AgingADRConsumer o1, AgingADRConsumer o2) {
	    return -DescSortByTotal.compare(o1, o2);
	}
    };

    @Override
    public String toString() {
	return "AgingADRConsumer [ID=" + ID + ", total=" + total + ", reactUp=" + reactUp
		+ ", reactDw=" + reactDw + "]";
    }
}
