package com.voicepin.fixedpassword.client.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

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
        final StringBuilder sb = new StringBuilder("EnrollResponse{");
        sb.append("trained=").append(trained);
        sb.append(", enrollStatus=").append(enrollStatus);
        sb.append(", decisionReason=").append(decisionReason);
        sb.append('}');
        return sb.toString();
    }
}
