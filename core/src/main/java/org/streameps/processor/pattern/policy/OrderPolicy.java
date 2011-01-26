package org.streameps.processor.pattern.policy;

import org.streameps.core.ParticipantEventSet;

public class OrderPolicy implements PatternPolicy {

    private OrderPolicyType orderType;
    private ParticipantEventSet participantEventSet;

    public OrderPolicy(OrderPolicyType orderType, ParticipantEventSet participantEventSet) {
        this.orderType = orderType;
        this.participantEventSet = participantEventSet;
    }

    public boolean checkPolicy() {
        switch (orderType) {
            case DETECTION_TIME:
                break;
            case OCCURENCE_TIME:
                break;
            case STREAM_POSITION:
                break;
            case USER_DEFINED_ATTRIBUTE:
                break;
        }
        return false;
    }

    public ParticipantEventSet getParticipantEventSet() {
        return participantEventSet;
    }
    
}
