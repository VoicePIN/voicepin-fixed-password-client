package com.voicepin.example.client.messages;

import java.util.ArrayList;
import java.util.List;

public class GetIdsResponse extends Response {

    protected List<String> voiceprintIds;
    protected long totalCount;

    public List<String> getVoiceprintIds() {
        if (voiceprintIds == null) {
            voiceprintIds = new ArrayList<String>();
        }
        return this.voiceprintIds;
    }

    public long getTotalCount() {
        return totalCount;
    }
}
