package com.voicepin.example.client.messages;

public enum EnrollStatus {

    SAMPLE_ACCEPTED,
    SAMPLE_REJECTED,
    WRONG_PHRASE_SPOKEN;

    public String value() {
        return name();
    }

    public static EnrollStatus fromValue(String v) {
        return valueOf(v);
    }
}
