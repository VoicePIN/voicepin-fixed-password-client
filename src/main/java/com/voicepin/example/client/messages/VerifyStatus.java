package com.voicepin.example.client.messages;

public enum VerifyStatus {

    MATCH,
    MISMATCH,
    FRAUD,
    WRONG_PHRASE_SPOKEN;

    public String value() {
        return name();
    }

    public static VerifyStatus fromValue(String v) {
        return valueOf(v);
    }
}
