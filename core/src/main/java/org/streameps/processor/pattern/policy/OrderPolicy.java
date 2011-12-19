package org.streameps.processor.pattern.policy;

import org.streameps.core.ParticipantEventSet;

public class OrderPolicy implements PatternPolicy {

    private OrderPolicyType orderType;
    private ParticipantEventSet participantEventSet;
    private String udf = null;
    private int recCount = 0;

    public OrderPolicy(OrderPolicyType orderType, ParticipantEventSet participantEventSet) {
        this.orderType = orderType;
        this.participantEventSet = participantEventSet;
        recCount = participantEventSet.size();
    }

    public void setParticipantEventSet(ParticipantEventSet participantEventSet) {
        this.participantEventSet = participantEventSet;
        recCount = participantEventSet.size();
    }

    public boolean checkPolicy(Object... optional) {
        boolean result = false;
        switch (orderType) {
            case DETECTION_TIME:
                break;
            case OCCURENCE_TIME:
                break;
            case STREAM_POSITION:
                result = true;
                break;
            case USER_DEFINED_ATTRIBUTE:
                if (udf == null) {
                    return result;
                }
                break;
        }
        return result;
    }

    public ParticipantEventSet getParticipantEventSet() {
        return participantEventSet;
    }

    public void setUdf(String udf) {
        this.udf = udf;
    }

    public String getUdf() {
        return udf;
    }

    public PolicyType getPolicyType() {
        return PolicyType.ORDER;
    }
}
