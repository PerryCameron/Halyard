package com.ecsail.jotform;

import com.ecsail.structures.jotform.JotFormSubmissionListDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JotFormECSC {

    public JotFormECSC() {
    }

    public JotFormSubmissionListDTO addSubmissionAnswersIntoDTO(JSONObject formSubmission) {
        JotFormSubmissionListDTO jotFormSubmissionDTO = new JotFormSubmissionListDTO(false,false,"",
                String.valueOf(formSubmission.get("created_at")),
                String.valueOf(formSubmission.get("ip")),
                Long.parseLong((String) formSubmission.get("form_id")),
                String.valueOf(formSubmission.get("created_at")),
                Long.parseLong((String) formSubmission.get("id")),
                String.valueOf(formSubmission.get("status")),
                "","","","","","","","");
        JSONObject answerObject = (JSONObject) formSubmission.get("answers");
        boolean hasAnswer = false;
        for (Object key : answerObject.keySet()) {
            JSONObject x = (JSONObject) answerObject.get((String) key);
            for (Object xkey : x.keySet()) {
                if (xkey.equals("answer"))
                    hasAnswer = true;
            }
            if (hasAnswer) {
                if(x.get("name").equals("membershipType")) {
                    jotFormSubmissionDTO.setMemType(String.valueOf(x.get("answer")));
                } else if(x.get("name").equals("primaryMember")) {
                    JSONObject name = (JSONObject) x.get("answer");
                    jotFormSubmissionDTO.setPrimaryFirstName(String.valueOf(name.get("first")));
                    jotFormSubmissionDTO.setPrimaryLastName(String.valueOf(name.get("last")));
                } else if (x.get("name").equals("address")) {
                    JSONObject address = (JSONObject) x.get("answer");
                    jotFormSubmissionDTO.setAddressLine1(String.valueOf(address.get("addr_line1")));
                    jotFormSubmissionDTO.setCity(String.valueOf(address.get("city")));
                    jotFormSubmissionDTO.setState(String.valueOf(address.get("state")));
                    jotFormSubmissionDTO.setPostal(String.valueOf(address.get("postal")));
                }
                hasAnswer = false;
            }
        }
        return jotFormSubmissionDTO;
    }

    public ArrayList<JSONObject> addFormSubmissionsIntoArray(JSONObject submissions) {
        ArrayList<JSONObject> formSubmissions = new ArrayList<>();
        JSONArray content = (JSONArray) submissions.get("content");
        for(int i =0; i < content.length(); i++) {
            formSubmissions.add((JSONObject) content.get(i));
        }
        return formSubmissions;
    }
}
