package com.voicepin.example;

import java.io.InputStream;

import com.voicepin.example.client.VoicepinClient;
import com.voicepin.example.client.VoicepinClientException;
import com.voicepin.example.client.messages.AddResponse;
import com.voicepin.example.client.messages.EnrollResponse;
import com.voicepin.example.client.messages.IsTrainedResponse;
import com.voicepin.example.client.messages.VerifyResponse;


public class RestClientApp {

    public static void main(String[] args) throws VoicepinClientException {

        /* VoicePIN API url */
        String apiUrl = "http://localhost:8080/voicepin-server/rest/v1/verifier";
        
        /* Data relating to your voice password - you should obtain these parameters from VoicePIN.com */
        String exampleApiKey = "37b1509d-e481-41a1-ae25-f77ec51021e2";
        String examplePasswordGroup = "uzywam-mojego-glosu-jako-klucza-ivr";

        /* Create client */
        VoicepinClient voicepinClient = new VoicepinClient(apiUrl, examplePasswordGroup, exampleApiKey);

        /* Perform some example operations */
        exampleOperations(voicepinClient);
    }

    private static void exampleOperations(VoicepinClient voicepinClient) throws VoicepinClientException {
        
        /* First we add a new voiceprint to VoicePIN and obtain its unique id (voiceprintId)
         * If you already have an id for this particular user, provide this id to this method instead of null
         */
        AddResponse addResponse = voicepinClient.addVoiceprint(null);
        String voiceprintId = addResponse.getVoiceprintId();
        System.out.println("New voiceprintId: " + addResponse.getVoiceprintId());
        /* -- */
        
        
        /* With the obtained voicepintId we can enroll a user (register voice and create voiceprint) 
         * For creating a voiceprint model we need three samples so we are going to enroll the voiceprint three times
         */
        EnrollResponse enrollResponse = voicepinClient.enrollVoiceprint(voiceprintId, loadAudio("/example/record_1.wav"));
        System.out.println("First sample enrollment result: " + enrollResponse.getEnrollStatus());
        
        enrollResponse = voicepinClient.enrollVoiceprint(voiceprintId, loadAudio("/example/record_2.wav"));
        System.out.println("Second sample enrollment result: " + enrollResponse.getEnrollStatus());
        
        enrollResponse = voicepinClient.enrollVoiceprint(voiceprintId, loadAudio("/example/record_3.wav"));
        System.out.println("Third sample enrollment result: " + enrollResponse.getEnrollStatus());
        /* -- */
                
        
        /* Now we can ensure that the voiceprint is trained properly */
        IsTrainedResponse isTrainResponse = voicepinClient.isVoiceprintTrained(voiceprintId);
        System.out.println("Voiceprint successfully trained?: " + isTrainResponse.isTrained());
        /* -- */
        
        
        /* Finally we can verify the user with another audio streams */
        VerifyResponse verifyResponse = voicepinClient.verifyVoiceprint(voiceprintId, loadAudio("/example/record_4.wav"));
        System.out.println("First sample verification decision: " + verifyResponse.getDecision());

        verifyResponse = voicepinClient.verifyVoiceprint(voiceprintId, loadAudio("/example/record_5.wav"));
        System.out.println("Second sample verification decision: " + verifyResponse.getDecision());
        /* -- */
        
        
        /* At the end it is possible to reset or remove the voiceprint
         * - Reset is the inverse operation to the registration by three enrollments
         * - Remove is the inverse operation to the adding a voiceprint 
         */
        boolean reset = voicepinClient.resetVoiceprint(voiceprintId);
        System.out.println("Voiceprint successfully reset?: " + reset);

        boolean deleted = voicepinClient.removeVoiceprint(voiceprintId);
        System.out.println("Voiceprint successfully removed?: " + deleted);
        /* -- */
        
    }

    public static InputStream loadAudio(String wavePath) {
        return RestClientApp.class.getResourceAsStream(wavePath);
    }
}
