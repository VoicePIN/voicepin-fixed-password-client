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
        final StringBuilder sb = new StringBuilder("VerifyResponse{");
        sb.append("score=").append(score);
        sb.append(", decision=").append(decision);
        sb.append(", decisionReason=").append(decisionReason);
        sb.append('}');
        return sb.toString();
    }
}
