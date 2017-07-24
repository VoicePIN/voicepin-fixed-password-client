package com.voicepin.fixedpassword.client.messages;

/**
 * @author mckulpa
 */
public enum VerifyDecisionReason {
    MATCH,
    WRONG_PHRASE,
    LOW_QUALITY_AUDIO,
    NO_SPEECH,
    SPEECH_TOO_SHORT,
    AUDIO_TOO_LONG,
    REPLAY_FRAUD,
    INCOHERENCE_WITH_PASSWORD_GROUP,
    BIOMETRIC_MISMATCH;
}
