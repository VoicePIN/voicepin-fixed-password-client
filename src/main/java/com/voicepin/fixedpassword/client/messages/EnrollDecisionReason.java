package com.voicepin.fixedpassword.client.messages;

/**
 * @author mckulpa
 */
public enum EnrollDecisionReason {
    SAMPLE_ACCEPTED,
    WRONG_PHRASE,
    LOW_QUALITY_AUDIO,
    NO_SPEECH,
    SPEECH_TOO_SHORT,
    AUDIO_TOO_LONG,
    REPLAY_FRAUD,
    //LIVENESS_FRAUD,
    //CUT_AND_PASTE_FRAUD,
    //INCOHERENCE_OF_SPEAKER_SAMPLES
    INCOHERENCE_WITH_PASSWORD_GROUP;
}
