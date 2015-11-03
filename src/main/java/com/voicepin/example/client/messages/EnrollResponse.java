package com.voicepin.example.client.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnrollResponse extends Response {

    @JsonProperty
    protected boolean trained;
    
    @JsonProperty
    protected EnrollStatus enrollStatus;

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
}
