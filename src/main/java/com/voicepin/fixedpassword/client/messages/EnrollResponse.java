package com.voicepin.fixedpassword.client.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class EnrollResponse extends Response {

    @JsonProperty
    protected boolean trained;
    
    @JsonProperty
    protected EnrollStatus enrollStatus;

    @JsonProperty
    protected EnrollDecisionReason decisionReason;

    public boolean isTrained() {
        return trained;
    }

    public void setTrained(boolean value) {
        this.trained = value;
    }

    public EnrollStatus getEnrollStatus() {
        return enrollStatus;
    }

    public void setEnrollStatus(EnrollStatus value) {
        this.enrollStatus = value;
    }

    public EnrollDecisionReason getDecisionReason() {
        return decisionReason;
    }

    public void setDecisionReason(EnrollDecisionReason decisionReason) {
        this.decisionReason = decisionReason;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("trained", trained)
                .add("enrollStatus", enrollStatus)
                .add("decisionReason", decisionReason)
                .toString();
    }
}
