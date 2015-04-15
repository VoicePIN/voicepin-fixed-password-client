package com.voicepin.example.client.messages;

public enum Status {

    OK,
    VOICEPRINT_NOT_EXISTS,
    VOICEPRINT_ALREADY_EXISTS,
    VOICEPRINT_NOT_ENROLLED,
    VOICEPRINT_ALREADY_ENROLLED,
    PASSWORD_GROUP_NOT_EXISTS,
    INCORRECT_AUDIO_INPUT,
    INVALID_LICENSE,
    USER_ERROR,
    INTERNAL_ERROR;

    public String value() {
        return name();
    }

    public static Status fromValue(String v) {
        return valueOf(v);
    }
}
