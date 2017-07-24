package com.voicepin.fixedpassword.client;

public class VoicepinClientException extends Exception {
    private static final long serialVersionUID = 8008436773054247597L;
    private final int statusCode;
    private final String statusDescription;

    VoicepinClientException(int statusCode, String statusDescription) {
        super("VoicePIN Server returned ERROR: " + statusCode + " " + statusDescription);
        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }
}
