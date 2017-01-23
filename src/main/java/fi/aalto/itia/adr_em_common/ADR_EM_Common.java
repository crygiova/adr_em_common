package fi.aalto.itia.adr_em_common;

public class ADR_EM_Common {

    public static final String REG_HEADER = "DR_Register";
    public static final String DENY_REG_HEADER = "DR_Register_Deny";
    public static final String ACCEPT_REG_HEADER = "DR_Register_Accept";
    public static final String STATUS_UPDATE_HEADER = "Status_Update";
    public static final String INSTRUCTIONS_HEADER = "Aggregator_Instructions";
    public static final String AGG_TO_STATS_HEADER = "New_Update";
    public static final String STATS_TO_AGG_HEADER = "StatsToAgg";

    //public static final String OUT_FILE_DIR = "C:/Users/giovanc1/workspace_sts/Aggregator_Web/";
    public static final String OUT_FILE_DIR = "C:/Users/Christian/Documents/GitHub/Aggregator_Web/";

    public static final String EMPTY = "";

    public static final String AGG_INPUT_QUEUE = "AGG";
    public static final String STATS_NAME_QUEUE = "Stats";

    public static final int ONE_SECOND = 1000;
    public static final int ONE_MIN_IN_SEC = 60;
    public static final int ONE_MIN = ONE_MIN_IN_SEC * ONE_SECOND;
    
    //CONFIGURATIONS OF THE APPLICATION
    public static final double TARGET_FLEX = 50000d;
    public static final boolean MSG_DELAYS = true;
    public static final int MSG_DELAY_CONSTANT = ONE_SECOND;
    public static final int MSG_DELAY_VARIABLE = 2 * ONE_SECOND;
    //delay for the fridges 1 = 0 delay
    public static final int FRIDGES_DELAY_CONSTANT = 2 *ONE_MIN_IN_SEC;
    public static final int FRIDGES_DELAY_VARIABLE = 2 *ONE_MIN_IN_SEC;
    //configuration of the allocation algorithm
    public static final boolean USE_POLICIES = true;
    

}
