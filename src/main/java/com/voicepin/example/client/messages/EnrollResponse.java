package com.voicepin.example.client.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnrollResponse extends Response {

    @JsonProperty("IsTrained")
    protected boolean isTrained;
    
    @JsonProperty("EnrollStatus")
    protected EnrollStatus enrollStatus;

    public boolean isIsTrained() {
        return isTrained;
    }

    public void setIsTrained(boolean value) {
        this.isTrained = value;
    }

    public EnrollStatus getEnrollStatus() {
        return enrollStatus;
    }

    public void setEnrollStatus(EnrollStatus value) {
        this.enrollStatus = value;
    }
}
