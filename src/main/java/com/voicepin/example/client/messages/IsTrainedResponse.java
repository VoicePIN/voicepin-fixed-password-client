package com.voicepin.example.client.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IsTrainedResponse extends Response {

    @JsonProperty("Trained")
    protected boolean trained;

    public boolean isTrained() {
        return trained;
    }

    public void setTrained(boolean value) {
        this.trained = value;
    }
}
