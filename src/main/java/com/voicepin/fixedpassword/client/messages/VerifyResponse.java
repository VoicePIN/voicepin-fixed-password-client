package com.voicepin.fixedpassword.client.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VerifyResponse extends Response {

    @JsonProperty
    protected double score;

    @JsonProperty
    protected VerifyDecision decision;

    @JsonProperty
    protected VerifyDecisionReason decisionReason;

    public double getScore() {
        return score;
    }

    public void setScore(double value) {
        this.score = value;
    }

    public VerifyDecision getDecision() {
        return decision;
    }

    public void setDecision(VerifyDecision value) {
        this.decision = value;
    }

    public VerifyDecisionReason getDecisionReason() {
        return decisionReason;
    }

    public void setDecisionReason(VerifyDecisionReason decisionReason) {
        this.decisionReason = decisionReason;
    }

    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add("score", score)
                .add("decision", decision)
                .add("decisionReason", decisionReason)
                .toString();
    }
}
