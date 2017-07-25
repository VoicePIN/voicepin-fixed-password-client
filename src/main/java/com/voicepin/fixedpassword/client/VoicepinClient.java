package com.voicepin.fixedpassword.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.voicepin.fixedpassword.client.messages.*;
import static com.voicepin.fixedpassword.client.PathConsts.*;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.InputStream;


public class VoicepinClient {


    private WebTarget baseWebTarget;

    private String apiKey;

    private String passwordGroup;

    public VoicepinClient(String voicepinBaseUrl, String passwordGroup, String apiKey) {
        this.apiKey = apiKey;
        this.passwordGroup = passwordGroup;

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        JacksonJaxbJsonProvider jsonProvider = new JacksonJaxbJsonProvider();
        jsonProvider.setMapper(mapper);

        Client client = ClientBuilder.newClient();
        client.register(MultiPartFeature.class);
        client.register(jsonProvider);

        this.baseWebTarget = client.target(voicepinBaseUrl);
    }

    public AddResponse addVoiceprint(String speakerId) throws VoicepinClientException {
        WebTarget webTarget = baseWebTarget.path(PASSWORD_GROUP_PATH).path(passwordGroup).queryParam(USER_IDENTIFIER, speakerId).queryParam(API_KEY_PATH, apiKey);
        Builder request = createRequest(webTarget);
        Response response = request.post(null);
        AddResponse addResponse = validate(response).readEntity(AddResponse.class);

        return addResponse;
    }

    public GetIdsResponse getVoiceprints() throws VoicepinClientException {
        WebTarget webTarget = baseWebTarget.path(PASSWORD_GROUP_PATH).path(passwordGroup).path(VOICEPRINT_PATH).queryParam(API_KEY_PATH, apiKey);
        Builder request = createRequest(webTarget);
        Response response = request.get();
        GetIdsResponse getIdsResponse = validate(response).readEntity(GetIdsResponse.class);

        return getIdsResponse;
    }

    public IsTrainedResponse isVoiceprintTrained(String voiceprintId) throws VoicepinClientException {
        WebTarget webTarget = baseWebTarget.path(VOICEPRINT_PATH).path(voiceprintId).path(ENROLLMENT_PATH).queryParam(API_KEY_PATH, apiKey);
        Builder request = createRequest(webTarget);
        Response response = request.get();
        IsTrainedResponse isTrainedResponse = validate(response).readEntity(IsTrainedResponse.class);

        return isTrainedResponse;
    }

    public EnrollResponse enrollVoiceprint(String voiceprintId, InputStream audioInputStream) throws VoicepinClientException {
        WebTarget webTarget = baseWebTarget.path(VOICEPRINT_PATH).path(voiceprintId).path(ENROLLMENT_PATH).queryParam(API_KEY_PATH, apiKey);
        Builder request = createRequest(webTarget);
        Entity<FormDataMultiPart> multipartEntity = createStreamMultipartEntity("recording", audioInputStream);
        Response response = request.post(multipartEntity);
        EnrollResponse enrollResponse = validate(response).readEntity(EnrollResponse.class);

        return enrollResponse;
    }

    public VerifyResponse verifyVoiceprint(String voiceprintId, InputStream audioInputStream) throws VoicepinClientException {
        WebTarget webTarget = baseWebTarget.path(VOICEPRINT_PATH).path(voiceprintId).path(VERIFICATION_PATH).queryParam(API_KEY_PATH, apiKey);
        Builder request = createRequest(webTarget);
        Entity<FormDataMultiPart> multipartEntity = createStreamMultipartEntity("recording", audioInputStream);
        Response response = request.post(multipartEntity);
        VerifyResponse verifyResponse = validate(response).readEntity(VerifyResponse.class);

        return verifyResponse;
    }

    public boolean resetVoiceprint(String voiceprintId) throws VoicepinClientException {
        WebTarget webTarget = baseWebTarget.path(VOICEPRINT_PATH).path(voiceprintId).path(ENROLLMENT_PATH).queryParam(API_KEY_PATH, apiKey);
        Builder request = createRequest(webTarget);
        Response response = request.delete();

        return validate(response).getStatus() == Status.OK.getStatusCode();
    }

    public boolean removeVoiceprint(String voiceprintId) throws VoicepinClientException {
        WebTarget webTarget = baseWebTarget.path(VOICEPRINT_PATH).path(voiceprintId).queryParam(API_KEY_PATH, apiKey);
        Builder request = createRequest(webTarget);
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
            throw new VoicepinServerError(response.getStatus(), response.getStatusInfo().getReasonPhrase());
        }
        return response;
    }
}
