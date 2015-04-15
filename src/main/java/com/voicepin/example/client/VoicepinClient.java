package com.voicepin.example.client;

import java.io.InputStream;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import com.voicepin.example.client.messages.AddResponse;
import com.voicepin.example.client.messages.EnrollResponse;
import com.voicepin.example.client.messages.GetIdsResponse;
import com.voicepin.example.client.messages.IsTrainedResponse;
import com.voicepin.example.client.messages.VerifyResponse;


public class VoicepinClient {

    private static final String VERIFICATION_PATH = "verifications";
    private static final String ENROLLMENT_PATH = "enrollments";
    private static final String VOICEPRINT_PATH = "voiceprints";
    private static final String PASSWORD_GROUP_PATH = "passwordGroups";

    private static final String EXTERNAL_ID_QUERY = "extId";
    private static final String API_KEY_QUERY = "apiKey";

    private WebTarget baseWebTarget;
    
    private String apiKey;
    
    private String passwordGroup;

    public VoicepinClient(String voicepinBaseUrl, String passwordGroup, String apiKey) {
        this.apiKey = apiKey;
        this.passwordGroup = passwordGroup;

        // Create client
        Client client = ClientBuilder.newClient();

        // Add Multipart Feature for audio streams
        client.register(MultiPartFeature.class);

        // Get base WebTarget
        this.baseWebTarget = client.target(voicepinBaseUrl);
    }

    public AddResponse addVoiceprint(String externalId) throws VoicepinClientException {

        // Adjust WebTarget
        WebTarget webTarget = baseWebTarget.path(PASSWORD_GROUP_PATH).path(passwordGroup).queryParam(EXTERNAL_ID_QUERY, externalId).queryParam(API_KEY_QUERY, apiKey);

        // Create request
        Builder request = createRequest(webTarget);

        // POST it
        Response response = request.post(null);

        // Validate and read response
        AddResponse addResponse = validate(response).readEntity(AddResponse.class);

        return addResponse;
    }

    public GetIdsResponse getVoiceprints() throws VoicepinClientException {

        // Adjust WebTarget
        WebTarget webTarget = baseWebTarget.path(PASSWORD_GROUP_PATH).path(passwordGroup).path(VOICEPRINT_PATH).queryParam(API_KEY_QUERY, apiKey);

        // Create request
        Builder request = createRequest(webTarget);

        // GET it
        Response response = request.get();

        // Validate and read response
        GetIdsResponse getIdsResponse = validate(response).readEntity(GetIdsResponse.class);

        return getIdsResponse;
    }

    public IsTrainedResponse isVoiceprintTrained(String voiceprintId) throws VoicepinClientException {

        // Adjust WebTarget
        WebTarget webTarget = baseWebTarget.path(VOICEPRINT_PATH).path(voiceprintId).path(ENROLLMENT_PATH).queryParam(API_KEY_QUERY, apiKey);

        // Create request
        Builder request = createRequest(webTarget);

        // GET it
        Response response = request.get();

        // Validate and read response
        IsTrainedResponse isTrainedResponse = validate(response).readEntity(IsTrainedResponse.class);

        return isTrainedResponse;
    }

    public EnrollResponse enrollVoiceprint(String voiceprintId, InputStream audioInputStream) throws VoicepinClientException {

        // Adjust WebTarget
        WebTarget webTarget = baseWebTarget.path(VOICEPRINT_PATH).path(voiceprintId).path(ENROLLMENT_PATH).queryParam(API_KEY_QUERY, apiKey);

        // Create request
        Builder request = createRequest(webTarget);

        // Create audio stream entity:
        Entity<FormDataMultiPart> multipartEntity = createStreamMultipartEntity("file", audioInputStream);

        // POST it
        Response response = request.post(multipartEntity);

        // Validate and read response
        EnrollResponse enrollResponse = validate(response).readEntity(EnrollResponse.class);

        return enrollResponse;
    }

    public VerifyResponse verifyVoiceprint(String voiceprintId, InputStream audioInputStream) throws VoicepinClientException {

        // Adjust WebTarget
        WebTarget webTarget = baseWebTarget.path(VOICEPRINT_PATH).path(voiceprintId).path(VERIFICATION_PATH).queryParam(API_KEY_QUERY, apiKey);

        // Create request
        Builder request = createRequest(webTarget);

        // Create audio stream entity:
        Entity<FormDataMultiPart> multipartEntity = createStreamMultipartEntity("file", audioInputStream);

        // POST it
        Response response = request.post(multipartEntity);

        // Validate and read response
        VerifyResponse verifyResponse = validate(response).readEntity(VerifyResponse.class);

        return verifyResponse;
    }

    public boolean resetVoiceprint(String voiceprintId) throws VoicepinClientException {

        // Adjust WebTarget
        WebTarget webTarget = baseWebTarget.path(VOICEPRINT_PATH).path(voiceprintId).path(ENROLLMENT_PATH).queryParam(API_KEY_QUERY, apiKey);

        // Create request
        Builder request = createRequest(webTarget);

        // DELETE it
        Response response = request.delete();

        return validate(response).getStatus() == Status.OK.getStatusCode();
    }

    public boolean removeVoiceprint(String voiceprintId) throws VoicepinClientException {

        // Adjust WebTarget
        WebTarget webTarget = baseWebTarget.path(VOICEPRINT_PATH).path(voiceprintId).queryParam(API_KEY_QUERY, apiKey);

        // Create request
        Builder request = createRequest(webTarget);

        // DELETE it
        Response response = request.delete();

        return validate(response).getStatus() == Status.OK.getStatusCode();
    }

    private Builder createRequest(WebTarget webTarget) {
        return webTarget.request(MediaType.APPLICATION_JSON);
    }

    private Entity<FormDataMultiPart> createStreamMultipartEntity(String dataName, InputStream in) {
        FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
        FormDataBodyPart fdp = new FormDataBodyPart(dataName, in, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        formDataMultiPart.bodyPart(fdp);
        return Entity.entity(formDataMultiPart, MediaType.MULTIPART_FORM_DATA_TYPE);
    }

    private Response validate(Response response) throws VoicepinClientException {
        if (response.getStatus() != Status.OK.getStatusCode()) {
            String description = response.readEntity(com.voicepin.example.client.messages.Response.class).getDescription();
            throw new VoicepinClientException("VoicePIN returned: " + description);
        }
        return response;
    }
}
