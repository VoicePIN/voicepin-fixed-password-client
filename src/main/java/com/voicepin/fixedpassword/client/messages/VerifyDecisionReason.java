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
    //LIVENESS_FRAUD,
    //CUT_AND_PASTE_FRAUD,
    INCOHERENCE_WITH_PASSWORD_GROUP,
    BIOMETRIC_MISMATCH,
    SNR_LEVEL_TOO_LOW
}
